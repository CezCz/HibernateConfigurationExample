package io.github.cezcz.hibernate;

import javax.persistence.*;

/**
 * Created by Cezary on 19.04.2017.
 */
@Entity
@Table(name = "movie_date_reservation", schema = "public", catalog = "TicketRes")
public class MovieDateReservationEntity {
    private Integer id;
    private Integer reservedSeatNumber;
    private Integer movieDate;
    private Integer user;
    private UserEntity user_entity;
    private MovieDateEntity seance;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "reserved_seat_number")
    public Integer getReservedSeatNumber() {
        return reservedSeatNumber;
    }

    public void setReservedSeatNumber(Integer reservedSeatNumber) {
        this.reservedSeatNumber = reservedSeatNumber;
    }

    @Basic
    @Column(name = "movie_date")
    public Integer getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(Integer movieDate) {
        this.movieDate = movieDate;
    }

    @Basic
    @Column(name = "user")
    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieDateReservationEntity that = (MovieDateReservationEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (reservedSeatNumber != null ? !reservedSeatNumber.equals(that.reservedSeatNumber) : that.reservedSeatNumber != null)
            return false;
        if (movieDate != null ? !movieDate.equals(that.movieDate) : that.movieDate != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (reservedSeatNumber != null ? reservedSeatNumber.hashCode() : 0);
        result = 31 * result + (movieDate != null ? movieDate.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id")
    public UserEntity getUser_entity() {
        return user_entity;
    }

    public void setUser_entity(UserEntity user_entity) {
        this.user_entity = user_entity;
    }

    @ManyToOne
    @JoinColumn(name = "movie_date", referencedColumnName = "id")
    public MovieDateEntity getSeance() {
        return seance;
    }

    public void setSeance(MovieDateEntity seance) {
        this.seance = seance;
    }
}
