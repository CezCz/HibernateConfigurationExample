package io.github.cezcz.hibernate;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Cezary on 19.04.2017.
 */
@Entity
@Table(name = "movie_date", schema = "public")
public class MovieDateEntity {
    private Integer id;
    private Timestamp date;
    private CinemaHallEntity cinemaHall;
    private List<MovieDateReservationEntity> reservations;
    private MoviesEntity movie;

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    public MoviesEntity getMovie() {
        return movie;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @ManyToOne
    @JoinColumn(name = "cinema_hall", referencedColumnName = "id")
    public CinemaHallEntity getCinemaHall() {
        return cinemaHall;
    }

    public void setCinemaHall(CinemaHallEntity cinemaHall) {
        this.cinemaHall = cinemaHall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieDateEntity that = (MovieDateEntity) o;

        if (getMovie().getId() != null ? !getMovie().getId().equals(that.getMovie().getId()) : that.getMovie().getId() != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (cinemaHall != null ? !cinemaHall.equals(that.cinemaHall) : that.cinemaHall != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getMovie().getId() != null ? getMovie().getId().hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (cinemaHall != null ? cinemaHall.hashCode() : 0);
        return result;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seance")
    public List<MovieDateReservationEntity> getReservations() {
        return reservations;
    }

    public void setReservations(List<MovieDateReservationEntity> reservations) {
        this.reservations = reservations;
    }

    public void setMovie(MoviesEntity movie) {
        this.movie = movie;
    }
}
