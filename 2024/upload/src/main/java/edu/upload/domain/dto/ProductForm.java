package edu.upload.domain.dto;

import edu.upload.domain.embed.Region;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductForm {

    private Integer id;
    private String name;
    private Region region = new Region();
    private MultipartFile registrationCertificate;
    private List<MultipartFile> productImages;

    /*public String getCountry() {
        return region.getCountry();
    }

    public String getCity() {
        return region.getCity();
    }

    public int getZipcode() {
        return region.getZipcode();
    }

    public void setCountry(String country) {
        this.region.setCountry(country);
    }

    public void setCity(String city) {
        this.region.setCity(city);
    }

    public void setZipcode(int zipcode) {
        this.region.setZipcode(zipcode);
    }*/

}
