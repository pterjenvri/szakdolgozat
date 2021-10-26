package model;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
public class User implements Serializable {
    @NonNull
    private String vezeteknev;
    @NonNull
    private String keresztnev;
    @NonNull
    private String olvasojegySzam;
    @NonNull
    private String szuletesiIdo;
}
