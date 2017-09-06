package io.github.cezcz.soap;

import io.github.cezcz.hibernate.ActorsEntity;
import io.github.cezcz.hibernate.GenresEntity;

import java.io.Serializable;

/**
 * Created by Cezary on 23.04.2017.
 */
public class GenresSoap implements Serializable {
    public Integer genreId;

    public static GenresSoap fromGenresEntity(GenresEntity genresEntity) {
        GenresSoap genresSoap = new GenresSoap();

        genresSoap.genreId = genresEntity.getId();

        return genresSoap;
    }
}
