package edu.upload.domain.entity;

import edu.upload.domain.embed.FileMetadata;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ProductImage {

    @Id @GeneratedValue
    private Integer id;

    @ManyToOne
    private Product product;

    @Embedded
    private FileMetadata fileMetadata;

}
