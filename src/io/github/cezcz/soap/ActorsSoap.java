package io.github.cezcz.soap;

import io.github.cezcz.hibernate.ActorsEntity;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Cezary on 23.04.2017.
 */
public class ActorsSoap implements Serializable {
    public Integer id;
    public String name;
    public String surname;
    public Long birthDay;

    public static ActorsSoap fromActorEntity(ActorsEntity actorsEntity) {
        ActorsSoap actorsSoap = new ActorsSoap();

        actorsSoap.id = actorsEntity.getId();
        actorsSoap.name = actorsEntity.getName();
        actorsSoap.surname = actorsEntity.getSurname();
        actorsSoap.birthDay = actorsEntity.getBirthDay().getTime();

        return actorsSoap;
    }
}
