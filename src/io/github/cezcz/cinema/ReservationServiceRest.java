package io.github.cezcz.cinema;

import io.github.cezcz.hibernate.MovieDateEntity;
import io.github.cezcz.hibernate.MovieDateReservationEntity;
import io.github.cezcz.soap.*;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Cezary on 19.04.2017.
 */

public interface ReservationServiceRest {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/seances")
    List<MovieDateSoap> getMovies(@QueryParam("date") Long date);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/images/{image_path}")
    String getImage(@PathParam("image_path") String image);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/seances/info")
    Message reserveMovie(ReservationInfo reservationInfo);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/seances")
    Message batchMovieOperations(List<ReservationInfo> reservationInfo);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/reservations")
    List<MovieDateReservationSoap> getReservations(User user);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/reservations/detail")
    Message getReservationsForDate(ReservationInfo reservationInfo);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/test")
    ReservationInfo test();
}
