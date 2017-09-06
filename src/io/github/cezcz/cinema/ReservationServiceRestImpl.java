package io.github.cezcz.cinema;

import io.github.cezcz.MyApplication;
import io.github.cezcz.hibernate.CinemaHallEntity;
import io.github.cezcz.hibernate.MovieDateEntity;
import io.github.cezcz.hibernate.MovieDateReservationEntity;
import io.github.cezcz.hibernate.UserEntity;
import io.github.cezcz.soap.*;
import io.github.cezcz.util.UserUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.imageio.ImageIO;
import javax.jws.WebService;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.soap.MTOM;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Cezary on 19.04.2017.
 */
@javax.ws.rs.Path("/reservation_service")

public class ReservationServiceRestImpl implements ReservationServiceRest {

    public ReservationServiceRestImpl() {

    }

    public static Session getSession() throws HibernateException {
        return MyApplication.getSession();
    }

    private List<MovieDateSoap> getAllMovies() {
        Session session = getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<MovieDateEntity> criteria = criteriaBuilder.createQuery(MovieDateEntity.class);
        Root<MovieDateEntity> movieDateEntityRoot = criteria.from(MovieDateEntity.class);

        Timestamp today = new Timestamp(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_WEEK, 24);
        Timestamp todayPlus14 = new Timestamp(cal.getTime().getTime());

        Path<Date> dateEntryPath = movieDateEntityRoot.get("date");
        criteria.where(criteriaBuilder.between(dateEntryPath, today, todayPlus14));

        List<MovieDateEntity> resultList = session.createQuery(criteria)
                .getResultList();
        List<MovieDateSoap> movieDateSoapList = resultList.stream().map(MovieDateSoap::fromMovieDateEntity).collect(Collectors.toList());
        session.close();

        return movieDateSoapList;
    }

    @Override
    public String getImage(String imageName) {
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        File image = new File("images/" + imageName);
        System.out.println(image.getAbsolutePath());

        try {
            return new String(Base64.getEncoder().encode(Files.readAllBytes(Paths.get("images/" + imageName))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<MovieDateSoap> getMovies(Long longDate) {
        if (longDate == null) {
            return getAllMovies();
        }
        Date date = new Date(longDate);
        Session session = getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<MovieDateEntity> criteria = criteriaBuilder.createQuery(MovieDateEntity.class);
        Root<MovieDateEntity> movieDateEntityRoot = criteria.from(MovieDateEntity.class);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date today = new Timestamp(cal.getTimeInMillis());
        cal.set(Calendar.HOUR_OF_DAY, 24);
        Date todayEvening = new Timestamp(cal.getTimeInMillis());

        Path<Date> dateEntryPath = movieDateEntityRoot.get("date");
        criteria.where(criteriaBuilder.between(dateEntryPath, today, todayEvening));

        List<MovieDateEntity> resultList = session.createQuery(criteria).getResultList();

        List<MovieDateSoap> movieDateSoapList = resultList.stream().map(MovieDateSoap::fromMovieDateEntity).collect(Collectors.toList());
        session.close();

        return movieDateSoapList;
    }

    @Override
    public Message reserveMovie(ReservationInfo reservationInfo) {
        Session session = getSession();
        UserEntity user = UserUtil.validateUserCredentials(reservationInfo.user, session);
        if (user != null) {
            Message operationInfo = new Message();
            MovieDateEntity reservationMovie = session.get(MovieDateEntity.class, reservationInfo.seanceId);
            List<MovieDateReservationEntity> movieDateReservationList = reservationMovie.getReservations();
            List<Integer> seats = reservationInfo.seats;

            if (reservationInfo.operation.equals(ReservationInfo.OperationType.RESERVATION)) {
                Transaction transaction = session.beginTransaction();
                try {
                    List<MovieDateReservationEntity> newReservations = reserveSeats(session, user, reservationMovie, movieDateReservationList, seats);

                    operationInfo.code = Message.Codes.SUCCESS;
                    operationInfo.message = "Tranzakcja przeszla pomyslnie";
                    operationInfo.pdf = generatePdf(newReservations);

                    transaction.commit();
                } catch (Exception e) {
                    if (transaction != null) transaction.rollback();
                    e.printStackTrace();
                    operationInfo.code = Message.Codes.ERROR;
                    operationInfo.message = e.getMessage();
                } finally {
                    session.close();
                }
            } else if (reservationInfo.operation.equals(ReservationInfo.OperationType.UNRESERVATION)) {
                Transaction transaction = session.beginTransaction();
                try {
                    unreserveSeats(session, user, movieDateReservationList, seats);

                    operationInfo.code = Message.Codes.SUCCESS;
                    operationInfo.message = "Tranzakcja przeszla pomyslnie";
                    operationInfo.pdf = null;

                    transaction.commit();
                } catch (RuntimeException e) {
                    if (transaction != null) transaction.rollback();
                    operationInfo.code = Message.Codes.ERROR;
                    operationInfo.message = e.getMessage();
                } finally {
                    session.close();
                }
            } else {
                session.close();
                throw new RuntimeException("Nie ma takiej operacji");
            }

            return operationInfo;
        } else {
            session.close();
            throw new RuntimeException("Nie ma takiego uzytkownika");
        }
    }

    private byte[] generatePdf(List<MovieDateReservationEntity> newReservations) throws IOException {
        PDDocument pdf = new PDDocument();
        PDPage page = new PDPage();
        pdf.addPage(page);
        PDDocumentInformation docInfo = pdf.getDocumentInformation();
        docInfo.setAuthor("Kino CINEMA");
        docInfo.setTitle("Bilet na seans");

        PDPageContentStream contentStream = new PDPageContentStream(pdf, page);
        PDFont font = PDType1Font.HELVETICA_BOLD;
        int fontSize = 16;
        float titleWidth = font.getStringWidth("Kino CINEMA") / 1000 * fontSize;
        float titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;

        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.setLeading(14.5f);
        contentStream.newLineAtOffset(Math.round((page.getMediaBox().getWidth() - titleWidth) / 2), Math.round(page.getMediaBox().getHeight() - titleHeight));

        contentStream.showText("Kino CINEMA");
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(-Math.round((page.getMediaBox().getWidth() - titleWidth) / 2) + 50, -25);
        contentStream.newLine();
        contentStream.showText("Tytul----------------------------------------------------------------------Data--------------------------------Miejsce");
        for (MovieDateReservationEntity movieReservation : newReservations) {
            contentStream.newLine();
            String title = movieReservation.getSeance().getMovie().getTitle();
            String date = movieReservation.getSeance().getDate().toString();
            String seat_number = movieReservation.getReservedSeatNumber().toString();

            for (int i = title.length(); i < 76; i++) {
                title += " ";
            }

            for (int i = date.length(); i < 32; i++) {
                date += " ";
            }

            contentStream.showText(title + date + seat_number);
        }

        contentStream.endText();
        contentStream.close();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        pdf.save(byteArrayOutputStream);
        pdf.close();
        return byteArrayOutputStream.toByteArray();
    }

    private synchronized void unreserveSeats(Session session, UserEntity user, List<MovieDateReservationEntity> movieDateReservationList, List<Integer> seats) {
        List<MovieDateReservationEntity> usersReservation = movieDateReservationList
                .stream().filter(movieDateReservationEntity -> seats
                        .stream().anyMatch(
                                seat -> seat.equals(movieDateReservationEntity.getReservedSeatNumber())) &&
                        movieDateReservationEntity.getUser_entity().equals(user)).collect(Collectors.toList());

        boolean notAllCanBeUnreserved = usersReservation.size() != seats.size();
        if (!notAllCanBeUnreserved) {
            usersReservation.stream().forEach(res -> {
                res.getUser_entity().getReservations().remove(res.getUser_entity().getReservations().indexOf(res));
                res.getSeance().getReservations().remove(res.getSeance().getReservations().indexOf(res));
            });
            for (MovieDateReservationEntity movieDateReservationEntity : usersReservation) {
                session.delete(movieDateReservationEntity);
            }
        } else {
            throw new RuntimeException("Nie mozna zrezygnowac z nieswoich rezerwacji!");
        }
    }

    private synchronized List<MovieDateReservationEntity> reserveSeats(Session session, UserEntity user, MovieDateEntity reservationMovie, List<MovieDateReservationEntity> movieDateReservationList, List<Integer> seats) {
        boolean anySeatTaken = movieDateReservationList.stream().anyMatch(movieDateReservation -> seats.stream().anyMatch(seat -> seat.equals(movieDateReservation.getReservedSeatNumber())));
        boolean noSuchSeat = false;
        if (!movieDateReservationList.isEmpty()) {
            CinemaHallEntity cinemaHall = movieDateReservationList.get(0).getSeance().getCinemaHall();
            noSuchSeat = seats.stream().anyMatch(seat -> seat > cinemaHall.getSeats());
        }

        if (anySeatTaken || noSuchSeat) {
            throw new RuntimeException("Seats are taken");
        } else {
            List<MovieDateReservationEntity> newReservationsList = seats.stream().map(seat -> {
                MovieDateReservationEntity newReservation = new MovieDateReservationEntity();
                newReservation.setReservedSeatNumber(seat);
                newReservation.setSeance(reservationMovie);
                newReservation.setUser_entity(user);

                return newReservation;
            }).collect(Collectors.toList());

            for (MovieDateReservationEntity newReservation : newReservationsList) {
                session.save(newReservation);
            }

            return newReservationsList;
        }
    }

    @Override
    public synchronized Message batchMovieOperations(List<ReservationInfo> reservationInfoList) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();

        Message operationInfo = new Message();
        List<MovieDateReservationEntity> movieDateReservationList = null;
        try {
            for (ReservationInfo reservationInfo : reservationInfoList) {
                UserEntity user = UserUtil.validateUserCredentials(reservationInfo.user, session);
                MovieDateEntity reservationMovie = session.get(MovieDateEntity.class, reservationInfo.seanceId);
                session.refresh(reservationMovie);
                movieDateReservationList = reservationMovie.getReservations();
                List<Integer> seats = reservationInfo.seats;
                if (user != null) {
                    if (reservationInfo.operation.equals(ReservationInfo.OperationType.RESERVATION)) {
                        reserveSeats(session, user, reservationMovie, movieDateReservationList, seats);
                    } else if (reservationInfo.operation.equals(ReservationInfo.OperationType.UNRESERVATION)) {
                        unreserveSeats(session, user, movieDateReservationList, seats);
                    }
                } else {
                    throw new RuntimeException("Nie ma takiego uzytkownika");
                }

                session.flush();
            }

            UserEntity user = UserUtil.validateUserCredentials(reservationInfoList.get(0).user, session);
            session.refresh(user);
            session.flush();
            List<MovieDateReservationEntity> reservations = user.getReservations().stream().filter(reservation -> reservationInfoList.stream().anyMatch(reservationInfo -> reservation.getSeance().getId().equals(reservationInfo.seanceId))).collect(Collectors.toList());

            if (reservations != null && !reservations.isEmpty()) {
                operationInfo.pdf = generatePdf(reservations);
            }

            transaction.commit();

            operationInfo.code = Message.Codes.SUCCESS;
            operationInfo.message = "Rezerwacja pomyslna.";
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            operationInfo.code = Message.Codes.ERROR;
            operationInfo.message = e.getMessage();
        } finally {
            session.close();
        }

        return operationInfo;
    }

    @Override
    public List<MovieDateReservationSoap> getReservations(User user) {
        Session session = getSession();
        UserEntity userEntity = UserUtil.validateUserCredentials(user, session);

        return userEntity.getReservations().stream().filter(reservation -> reservation.getSeance().getDate().after(new Timestamp(System.currentTimeMillis()))).map(MovieDateReservationSoap::fromMoviesDateReservationEntity).collect(Collectors.toList());
    }

    @Override
    public Message getReservationsForDate(ReservationInfo reservationInfo) {
        Session session = getSession();
        UserEntity userEntity = UserUtil.validateUserCredentials(reservationInfo.user, session);
        Message msg = new Message();

        try {
            msg.message = "Reservation information";
            msg.code = Message.Codes.SUCCESS;
            List<MovieDateReservationEntity> reservations = userEntity.getReservations().stream().filter(reservation -> reservation.getSeance().getId().equals(reservationInfo.seanceId)).collect(Collectors.toList());
            msg.pdf = generatePdf(reservations);
        } catch (IOException e) {
            e.printStackTrace();
            msg.message = "Reservaton information failed";
            msg.code = Message.Codes.ERROR;
        }

        return msg;
    }

    @Override
    public ReservationInfo test() {
        ReservationInfo reservationInfo = new ReservationInfo();
        reservationInfo.operation = ReservationInfo.OperationType.RESERVATION;
        reservationInfo.seanceId = 1;
        reservationInfo.user = new User();
        reservationInfo.user.login = "cez";
        reservationInfo.user.password = "cez";
        reservationInfo.seats = new ArrayList<>();
        reservationInfo.seats.add(1);

        return reservationInfo;
    }
}
