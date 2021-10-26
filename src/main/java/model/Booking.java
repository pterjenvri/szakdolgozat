package model;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
public class Booking implements Serializable {
    @NonNull
    private String olvasojegySzam;
    @NonNull
    private String ISBN;
}
