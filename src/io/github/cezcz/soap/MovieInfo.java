package io.github.cezcz.soap;

import io.github.cezcz.hibernate.CinemaHallEntity;
import io.github.cezcz.hibernate.MovieDateEntity;
import io.github.cezcz.hibernate.MovieDateReservationEntity;
import io.github.cezcz.hibernate.MoviesEntity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;

/**
 * Created by Cezary on 19.04.2017.
 */
public class MovieInfo implements Serializable {

    public Integer id;
    public Timestamp date;
    public CinemaHallEntity cinemaHall;
    public List<MovieDateReservationSoap> reservations;
    public MoviesEntity movie;

}
