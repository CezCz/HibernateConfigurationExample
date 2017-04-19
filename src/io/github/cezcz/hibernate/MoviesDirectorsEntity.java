package io.github.cezcz.hibernate;

import javax.persistence.*;

/**
 * Created by Cezary on 19.04.2017.
 */
@Entity
@Table(name = "movies_directors", schema = "public", catalog = "TicketRes")
@IdClass(MoviesDirectorsEntityPK.class)
public class MoviesDirectorsEntity {
    private Integer directorId;
    private Integer movieId;

    @Id
    @Column(name = "director_id")
    public Integer getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Integer directorId) {
        this.directorId = directorId;
    }

    @Id
    @Column(name = "movie_id")
    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoviesDirectorsEntity that = (MoviesDirectorsEntity) o;

        if (directorId != null ? !directorId.equals(that.directorId) : that.directorId != null) return false;
        if (movieId != null ? !movieId.equals(that.movieId) : that.movieId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = directorId != null ? directorId.hashCode() : 0;
        result = 31 * result + (movieId != null ? movieId.hashCode() : 0);
        return result;
    }
}
