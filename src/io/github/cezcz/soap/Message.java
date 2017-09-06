package io.github.cezcz.soap;

import io.github.cezcz.hibernate.MovieDateReservationEntity;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Cezary on 19.04.2017.
 */
public class Message implements Serializable {
    public static class Codes {
        public static final int SUCCESS = 1;
        public static final int ERROR = 2;
        public static final int INFO = 3;
    }

    public int code;
    public String message;
    public byte[] pdf;
}
