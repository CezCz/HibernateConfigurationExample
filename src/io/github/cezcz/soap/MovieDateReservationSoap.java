package io.github.cezcz.soap;

import io.github.cezcz.hibernate.MovieDateEntity;
import io.github.cezcz.hibernate.MovieDateReservationEntity;
import io.github.cezcz.hibernate.UserEntity;

import java.io.Serializable;

/**
 * Created by Cezary on 23.04.2017.
 */
public class MovieDateReservationSoap implements Serializable {
    public Integer id;
    public Integer seanceId;
    public Integer reservedSeatNumber;
    public String user;
    public String title;
    public Long date;

    public static MovieDateReservationSoap fromMoviesDateReservationEntity(MovieDateReservationEntity movieDateReservationEntity) {
        MovieDateReservationSoap movieDateSoap = new MovieDateReservationSoap();

        movieDateSoap.id = movieDateReservationEntity.getId();

        movieDateSoap.reservedSeatNumber = movieDateReservationEntity.getReservedSeatNumber();
        movieDateSoap.user = movieDateReservationEntity.getUser_entity().getLogin();
        movieDateSoap.title = movieDateReservationEntity.getSeance().getMovie().getTitle();
        movieDateSoap.date = movieDateReservationEntity.getSeance().getDate().getTime();
        movieDateSoap.seanceId = movieDateReservationEntity.getSeance().getId();

        return movieDateSoap;
    }
}
