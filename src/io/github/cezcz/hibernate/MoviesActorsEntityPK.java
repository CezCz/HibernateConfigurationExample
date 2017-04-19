package io.github.cezcz.hibernate;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Cezary on 19.04.2017.
 */
public class MoviesActorsEntityPK implements Serializable {
    private Integer movieId;
    private Integer actorId;

    @Column(name = "movie_id")
    @Id
    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    @Column(name = "actor_id")
    @Id
    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoviesActorsEntityPK that = (MoviesActorsEntityPK) o;

        if (movieId != null ? !movieId.equals(that.movieId) : that.movieId != null) return false;
        if (actorId != null ? !actorId.equals(that.actorId) : that.actorId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = movieId != null ? movieId.hashCode() : 0;
        result = 31 * result + (actorId != null ? actorId.hashCode() : 0);
        return result;
    }
}
