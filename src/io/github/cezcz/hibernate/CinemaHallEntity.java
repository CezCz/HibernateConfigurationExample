package io.github.cezcz.hibernate;

import javax.persistence.*;

/**
 * Created by Cezary on 19.04.2017.
 */
@Entity
@Table(name = "cinema_hall", schema = "public", catalog = "TicketRes")
public class CinemaHallEntity {
    private Integer id;
    private Integer seats;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "seats")
    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CinemaHallEntity that = (CinemaHallEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (seats != null ? !seats.equals(that.seats) : that.seats != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (seats != null ? seats.hashCode() : 0);
        return result;
    }
}
