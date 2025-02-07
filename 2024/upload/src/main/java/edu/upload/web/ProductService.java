package edu.upload.web;

import edu.upload.domain.dto.ProductForm;
import edu.upload.domain.embed.FileMetadata;
import edu.upload.domain.entity.Product;
import edu.upload.domain.entity.ProductImage;
import edu.upload.domain.entity.RegistrationCertificate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Product save(ProductForm productForm, FileMetadata registrationCertificateFileMetadata, List<FileMetadata> productImagesFileMetadata) {
        Product product = new Product();
        product.setName(productForm.getName());
        product.setRegion(productForm.getRegion());
        product.setRegistrationCertificate(createRegistrationCertificate(product, registrationCertificateFileMetadata));
        product.setProductImages(createProductImages(product, productImagesFileMetadata));
        return productRepository.save(product);
    }

    private RegistrationCertificate createRegistrationCertificate(Product product, FileMetadata registrationCertificateFileMetadata) {
        RegistrationCertificate registrationCertificate = new RegistrationCertificate();
        registrationCertificate.setProduct(product);
        registrationCertificate.setFileMetadata(registrationCertificateFileMetadata);
        return registrationCertificate;
    }

    private List<ProductImage> createProductImages(Product product, List<FileMetadata> productImagesFileMetadata) {
        List<ProductImage> productImages = new ArrayList<>();
        for (FileMetadata productImageFileMetadata : productImagesFileMetadata) {
            ProductImage productImage = createProductImage(product, productImageFileMetadata);
            productImages.add(productImage);
        }
        return productImages;
    }

    private ProductImage createProductImage(Product product, FileMetadata productImageFileMetadata) {
        ProductImage productImage = new ProductImage();
        productImage.setProduct(product);
        productImage.setFileMetadata(productImageFileMetadata);
        return productImage;
    }

    public Product findById(Integer productId) {
        return productRepository.findById(productId).orElseThrow();
    }

}
