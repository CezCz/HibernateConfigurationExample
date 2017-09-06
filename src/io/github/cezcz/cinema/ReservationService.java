package io.github.cezcz.cinema;

import io.github.cezcz.hibernate.MovieDateEntity;
import io.github.cezcz.hibernate.MovieDateReservationEntity;
import io.github.cezcz.soap.*;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Cezary on 19.04.2017.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
//@HandlerChain(file="handler-chain.xml")
public interface ReservationService {
    @WebMethod
    List<MovieDateSoap> getAllMovies();

    @WebMethod
    List<MovieDateSoap> getMovies(Date date);

    @WebMethod
    Image getImage(String image);

    @WebMethod
    Message reserveMovie(ReservationInfo reservationInfo);

    @WebMethod
    Message batchMovieOperations(List<ReservationInfo> reservationInfo);

    @WebMethod
    List<MovieDateReservationSoap> getReservations(User user);

    Message getReservationsForDate(ReservationInfo reservationInfo);
}
