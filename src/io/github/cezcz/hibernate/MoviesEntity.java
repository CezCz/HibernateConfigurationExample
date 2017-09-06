package io.github.cezcz.hibernate;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Cezary on 19.04.2017.
 */
@Entity
@Table(name = "movies", schema = "public")
public class MoviesEntity {
    private String title;
    private Integer id;
    private String description;
    private String imagePath;
    private List<MovieDateEntity> seances;
    private List<ActorsEntity> actors;
    private List<DirectorsEntity> directors;
    private List<GenresEntity> genres;

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "image_path")
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoviesEntity that = (MoviesEntity) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (imagePath != null ? !imagePath.equals(that.imagePath) : that.imagePath != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (imagePath != null ? imagePath.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "movie")
    public List<MovieDateEntity> getSeances() {
        return seances;
    }

    public void setSeances(List<MovieDateEntity> seances) {
        this.seances = seances;
    }

    @ManyToMany
    @JoinTable(name = "movies_actors", schema = "public", joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "id", nullable = false))
    public List<ActorsEntity> getActors() {
        return actors;
    }

    public void setActors(List<ActorsEntity> actors) {
        this.actors = actors;
    }

    @ManyToMany
    @JoinTable(name = "movies_directors", schema = "public", joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "director_id", referencedColumnName = "id", nullable = false))
    public List<DirectorsEntity> getDirectors() {
        return directors;
    }

    public void setDirectors(List<DirectorsEntity> directors) {
        this.directors = directors;
    }

    @ManyToMany
    @JoinTable(name = "movies_genres", schema = "public", joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id", nullable = false))
    public List<GenresEntity> getGenres() {
        return genres;
    }

    public void setGenres(List<GenresEntity> genres) {
        this.genres = genres;
    }
}
