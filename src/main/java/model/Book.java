package model;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
public class Book implements Serializable {
    @NonNull
    private String Title;
    @NonNull
    private String ISBN;
}
