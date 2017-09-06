package io.github.cezcz.hibernate;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Cezary on 19.04.2017.
 */
@Entity
@Table(name = "genres", schema = "public" )
public class GenresEntity {
    private Integer id;
    private String name;
    private List<MoviesEntity> movies;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenresEntity that = (GenresEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @ManyToMany(mappedBy = "genres")
    public List<MoviesEntity> getMovies() {
        return movies;
    }

    public void setMovies(List<MoviesEntity> movies) {
        this.movies = movies;
    }
}
