package edu.upload.domain.entity;

import edu.upload.domain.embed.Region;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @Embedded
    private Region region;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private RegistrationCertificate registrationCertificate;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> productImages;


}
