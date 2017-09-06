package io.github.cezcz.soap;

import io.github.cezcz.hibernate.CinemaHallEntity;

import java.io.Serializable;

/**
 * Created by Cezary on 23.04.2017.
 */
public class CinemaHallSoap implements Serializable {
    public Integer id;
    public Integer seats;

    public static CinemaHallSoap fromCinemaHallEntity(CinemaHallEntity cinemaHall) {
        CinemaHallSoap chs = new CinemaHallSoap();

        chs.id = cinemaHall.getId();
        chs.seats = cinemaHall.getSeats();

        return chs;
    }
}
