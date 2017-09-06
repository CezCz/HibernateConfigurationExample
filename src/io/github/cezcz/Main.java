package io.github.cezcz;

import io.github.cezcz.cinema.ReservationService;
import io.github.cezcz.cinema.ReservationServiceImpl;
import io.github.cezcz.soap.ReservationInfo;
import io.github.cezcz.soap.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.xml.ws.Endpoint;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Cezary on 19.04.2017.
 */
public class Main {
    public static void main(String[] args) {
//        Endpoint.publish("http://localhost:8080/cinema/reservations", new ReservationServiceImpl());
        System.out.println("Webservice running");
    }

    private static final SessionFactory ourSessionFactory;

    static {
        try {
            ourSessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }
}
