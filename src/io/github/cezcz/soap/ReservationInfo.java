package io.github.cezcz.soap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Cezary on 19.04.2017.
 */
public class ReservationInfo implements Serializable {
    public List<Integer> seats;
    public Integer seanceId;
    public OperationType operation;
    public User user;

    public enum OperationType {
        RESERVATION,
        UNRESERVATION
    }
}
