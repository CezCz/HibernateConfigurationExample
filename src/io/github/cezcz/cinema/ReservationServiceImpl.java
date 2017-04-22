package io.github.cezcz.cinema;

import io.github.cezcz.hibernate.MovieDateEntity;
import io.github.cezcz.soap.Message;
import io.github.cezcz.soap.MovieInfo;
import io.github.cezcz.soap.ReservationInfo;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.service.ServiceRegistry;

import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Cezary on 19.04.2017.
 */

@WebService(endpointInterface = "io.github.cezcz.cinema.ReservationService")
public class ReservationServiceImpl implements ReservationService {

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

    @Override
    public List<MovieDateEntity> getMovies() {
        Session session = getSession();
        EntityManagerFactory emf = session.getEntityManagerFactory();
        Metamodel metamodel = emf.getMetamodel();
        EntityType<MovieDateEntity> movieDateEntity = metamodel.entity(MovieDateEntity.class);
        EntityManager em = emf.createEntityManager();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<MovieDateEntity> criteria = criteriaBuilder.createQuery(MovieDateEntity.class);
        Root<MovieDateEntity> movieDateEntityRoot = criteria.from(MovieDateEntity.class);

        Timestamp today = new Timestamp(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_WEEK, 14);
        Timestamp todayPlus14 =  new Timestamp(cal.getTime().getTime());

        Path<Date> dateEntryPath = movieDateEntityRoot.get("date");
        criteria.where(criteriaBuilder.between(dateEntryPath, today, todayPlus14));

        List<MovieDateEntity> movieDateEntityList = em.createQuery(criteria).getResultList();

        return null;
    }

    @Override
    public List<MovieDateEntity> getMovies(Date date) {
        return null;
    }

    @Override
    public Message reserveMovie(ReservationInfo reservationInfo) {
        return null;
    }

    @Override
    public Message batchMovieOperations(ReservationInfo reservationInfo) {
        return null;
    }

    @Override
    public Message getReservations() {
        return null;
    }
}
