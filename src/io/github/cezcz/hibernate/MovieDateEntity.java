package io.github.cezcz.hibernate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Cezary on 19.04.2017.
 */
@Entity
@Table(name = "movie_date", schema = "public", catalog = "TicketRes")
public class MovieDateEntity {
    private Integer movieId;
    private Integer id;
    private Timestamp date;
    private Integer cinemaHall;
    private List<MovieDateReservationEntity> reservations;
    private MoviesEntity movie;

    @Basic
    @Column(name = "movie_id")
    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    @Id
    @Column(name = "id")
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

    @Basic
    @Column(name = "cinema_hall")
    public Integer getCinemaHall() {
        return cinemaHall;
    }

    public void setCinemaHall(Integer cinemaHall) {
        this.cinemaHall = cinemaHall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieDateEntity that = (MovieDateEntity) o;

        if (movieId != null ? !movieId.equals(that.movieId) : that.movieId != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (cinemaHall != null ? !cinemaHall.equals(that.cinemaHall) : that.cinemaHall != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = movieId != null ? movieId.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (cinemaHall != null ? cinemaHall.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "seance")
    public List<MovieDateReservationEntity> getReservations() {
        return reservations;
    }

    public void setReservations(List<MovieDateReservationEntity> reservations) {
        this.reservations = reservations;
    }

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    public MoviesEntity getMovie() {
        return movie;
    }

    public void setMovie(MoviesEntity movie) {
        this.movie = movie;
    }
}
