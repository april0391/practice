package edu.upload.web;

import edu.upload.domain.embed.FileMetadata;
import edu.upload.domain.entity.Product;
import edu.upload.domain.dto.ProductForm;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.MalformedURLException;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final ProductService productService;
    private final StoreService storeService;

    @GetMapping("/upload")
    public String uploadForm(Model model) {
        ProductForm productForm = new ProductForm();
        model.addAttribute("productForm", productForm);
        return "upload";
    }

    @PostMapping("/upload")
    public String upload(@ModelAttribute("productForm") ProductForm productForm, RedirectAttributes redirectAttributes) {
        // 파일 저장
        FileMetadata registrationCertifiacteFileMetadata = storeService.storeFile(productForm.getRegistrationCertificate());
        List<FileMetadata> productImagesFileMetadata = storeService.storeFiles(productForm.getProductImages());

        // DB에 저장
        Product saved = productService.save(productForm, registrationCertifiacteFileMetadata, productImagesFileMetadata);

        // 리다이렉트
        redirectAttributes.addAttribute("productId", saved.getId());
        return "redirect:/view/{productId}";
    }

    @GetMapping("/view/{productId}")
    public String view(@PathVariable Integer productId, Model model) {
        Product product = productService.findById(productId);
        model.addAttribute("product", product);
        return "view";
    }

    @GetMapping("/attach/{id}")
    public ResponseEntity<Resource> fileServe(@PathVariable("id") Integer productId) throws MalformedURLException {
        String storedName = productService.findById(productId)
                .getRegistrationCertificate()
                .getFileMetadata()
                .getStoredName();
        UrlResource urlResource = new UrlResource("file:" + storeService.getFullPath(storedName));
        String contentDisposition = "attachment; filename=\"" + storedName + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(urlResource);
    }

    @ResponseBody
    @GetMapping("/images/{fileName}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String fileName) throws MalformedURLException {
        UrlResource resource = new UrlResource("file:" + storeService.getFullPath(fileName));
        return ResponseEntity.ok()
                .body(resource);
    }

}
