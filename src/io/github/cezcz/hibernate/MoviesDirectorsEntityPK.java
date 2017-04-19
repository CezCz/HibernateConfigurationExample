package io.github.cezcz.hibernate;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Cezary on 19.04.2017.
 */
public class MoviesDirectorsEntityPK implements Serializable {
    private Integer directorId;
    private Integer movieId;

    @Column(name = "director_id")
    @Id
    public Integer getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Integer directorId) {
        this.directorId = directorId;
    }

    @Column(name = "movie_id")
    @Id
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

        MoviesDirectorsEntityPK that = (MoviesDirectorsEntityPK) o;

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
