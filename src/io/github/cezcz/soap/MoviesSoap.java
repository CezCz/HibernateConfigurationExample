package io.github.cezcz.soap;

import io.github.cezcz.hibernate.*;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Cezary on 23.04.2017.
 */
public class MoviesSoap implements Serializable {
    public String title;
    public Integer id;
    public String description;
    public String imagePath;
    public List<ActorsSoap> actors;
    public List<DirectorsSoap> directors;
    public List<GenresSoap> genres;

    public static MoviesSoap fromMoviesEntity(MoviesEntity movie) {
        MoviesSoap ms = new MoviesSoap();

        ms.id = movie.getId();
        ms.title = movie.getTitle();
        ms.description = movie.getDescription();
        ms.imagePath = movie.getImagePath();
        ms.actors = movie.getActors().stream().map(actorsEntity -> ActorsSoap.fromActorEntity(actorsEntity)).collect(Collectors.toList());
        ms.directors = movie.getDirectors().stream().map(directorsEntity -> DirectorsSoap.fromDirectorsEntity(directorsEntity)).collect(Collectors.toList());
        ms.genres = movie.getGenres().stream().map(genresEntity -> GenresSoap.fromGenresEntity(genresEntity)).collect(Collectors.toList());

        return ms;
    }
}
