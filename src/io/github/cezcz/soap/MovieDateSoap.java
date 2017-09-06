package io.github.cezcz.soap;

import io.github.cezcz.hibernate.CinemaHallEntity;
import io.github.cezcz.hibernate.MovieDateEntity;
import io.github.cezcz.hibernate.MovieDateReservationEntity;
import io.github.cezcz.hibernate.MoviesEntity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Cezary on 23.04.2017.
 */
public class MovieDateSoap implements Serializable {
    public Integer id;
    public Long date;
    public CinemaHallSoap cinemaHall;
    public List<MovieDateReservationSoap> reservations;
    public MoviesSoap movie;

    public static MovieDateSoap fromMovieDateEntity(MovieDateEntity movieDateEntity) {
        MovieDateSoap mds = new MovieDateSoap();

        mds.id = movieDateEntity.getId();
        mds.date = movieDateEntity.getDate().getTime();
        mds.cinemaHall = CinemaHallSoap.fromCinemaHallEntity(movieDateEntity.getCinemaHall());
        mds.movie = MoviesSoap.fromMoviesEntity(movieDateEntity.getMovie());
        mds.reservations = movieDateEntity.getReservations().stream().map(movieDateReservationEntity -> MovieDateReservationSoap.fromMoviesDateReservationEntity(movieDateReservationEntity)).collect(Collectors.toList());

        return mds;
    }
}
