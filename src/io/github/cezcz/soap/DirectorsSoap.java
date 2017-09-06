package io.github.cezcz.soap;

import io.github.cezcz.hibernate.ActorsEntity;
import io.github.cezcz.hibernate.DirectorsEntity;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Cezary on 23.04.2017.
 */
public class DirectorsSoap implements Serializable {
    public Integer id;
    public String name;
    public String surname;
    public Long birthDay;

    public static DirectorsSoap fromDirectorsEntity(DirectorsEntity directorsEntity) {
        DirectorsSoap directorsSoap = new DirectorsSoap();

        directorsSoap.id = directorsEntity.getId();
        directorsSoap.name = directorsEntity.getName();
        directorsSoap.surname = directorsEntity.getSurname();
        directorsSoap.birthDay = directorsEntity.getBirthDay().getTime();

        return directorsSoap;
    }
}
