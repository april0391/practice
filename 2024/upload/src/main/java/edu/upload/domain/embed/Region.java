package edu.upload.domain.embed;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Region {

    private String country;
    private String city;
    private int zipcode;
}
