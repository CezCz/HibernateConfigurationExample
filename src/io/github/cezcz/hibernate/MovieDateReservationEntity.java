package io.github.cezcz.hibernate;

import javax.annotation.Generated;
import javax.persistence.*;

/**
 * Created by Cezary on 19.04.2017.
 */
@Entity
@Table(name = "movie_date_reservation", schema = "public")
public class MovieDateReservationEntity {
    private Integer id;
    private Integer reservedSeatNumber;
    private UserEntity user_entity;
    private MovieDateEntity seance;

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
    @Column(name = "reserved_seat_number")
    public Integer getReservedSeatNumber() {
        return reservedSeatNumber;
    }

    public void setReservedSeatNumber(Integer reservedSeatNumber) {
        this.reservedSeatNumber = reservedSeatNumber;
    }

    @ManyToOne
    @JoinColumn(name = "movie_date", referencedColumnName = "id")
    public MovieDateEntity getSeance() {
        return seance;
    }

    public void setSeance(MovieDateEntity seance) {
        this.seance = seance;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public UserEntity getUser_entity() {
        return user_entity;
    }

    public void setUser_entity(UserEntity user_entity) {
        this.user_entity = user_entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieDateReservationEntity that = (MovieDateReservationEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (reservedSeatNumber != null ? !reservedSeatNumber.equals(that.reservedSeatNumber) : that.reservedSeatNumber != null)
            return false;
        if (getSeance().getDate() != null ? !getSeance().getDate().equals(that.getSeance().getDate()) : that.getSeance().getDate() != null)
            return false;
        if (user_entity.getId() != null ? !user_entity.getId().equals(that.user_entity.getId()) : that.user_entity.getId() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (reservedSeatNumber != null ? reservedSeatNumber.hashCode() : 0);
        result = 31 * result + (getSeance().getDate() != null ? getSeance().getDate().hashCode() : 0);
        result = 31 * result + (user_entity.getId() != null ? user_entity.getId().hashCode() : 0);
        return result;
    }

}
