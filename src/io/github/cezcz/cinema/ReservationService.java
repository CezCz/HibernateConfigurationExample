package io.github.cezcz.cinema;

import io.github.cezcz.hibernate.MovieDateEntity;
import io.github.cezcz.soap.Message;
import io.github.cezcz.soap.MovieInfo;
import io.github.cezcz.soap.ReservationInfo;
import io.github.cezcz.soap.User;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.Date;
import java.util.List;

/**
 * Created by Cezary on 19.04.2017.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface ReservationService {
    @WebMethod
    List<MovieDateEntity> getMovies();

    @WebMethod
    List<MovieDateEntity> getMovies(Date date);

    @WebMethod
    Message reserveMovie(ReservationInfo reservationInfo);

    @WebMethod
    Message batchMovieOperations(ReservationInfo reservationInfo);

    @WebMethod
    Message getReservations();
}
