package io.github.cezcz.hibernate;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Cezary on 19.04.2017.
 */
public class MoviesGenresEntityPK implements Serializable {
    private Integer genreId;
    private Integer movieId;

    @Column(name = "genre_id")
    @Id
    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
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

        MoviesGenresEntityPK that = (MoviesGenresEntityPK) o;

        if (genreId != null ? !genreId.equals(that.genreId) : that.genreId != null) return false;
        if (movieId != null ? !movieId.equals(that.movieId) : that.movieId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = genreId != null ? genreId.hashCode() : 0;
        result = 31 * result + (movieId != null ? movieId.hashCode() : 0);
        return result;
    }
}
