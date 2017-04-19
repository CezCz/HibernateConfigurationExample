package io.github.cezcz;

import io.github.cezcz.cinema.ReservationService;
import io.github.cezcz.cinema.ReservationServiceImpl;

/**
 * Created by Cezary on 19.04.2017.
 */
public class Main {
    public static void main(String[] args) {
        ReservationService rsvp = new ReservationServiceImpl();

        rsvp.getMovies();
    }
}
