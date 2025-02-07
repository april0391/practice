package edu.upload.web;

import edu.upload.domain.embed.FileMetadata;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StoreService {

    private final String path = "C:/Users/april/Documents/files/";

    public FileMetadata storeFile(MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return null;
            }
            String originalName = file.getOriginalFilename();
            String storeName = generateStoreName(originalName);
            String fullPath = getFullPath(storeName);
            file.transferTo(new File(fullPath));
            return createFileMetadata(originalName, storeName);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getFullPath(String storeName) {
        return path + storeName;
    }

    private FileMetadata createFileMetadata(String originalName, String storeName) {
        return new FileMetadata(originalName, storeName);
    }

    private String generateStoreName(String originalFileName) {
        int i = originalFileName.lastIndexOf(".");
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        String ext = originalFileName.substring(i);
        return originalFileName.substring(0, i) + "-" + uuid + ext;
    }

    public List<FileMetadata> storeFiles(List<MultipartFile> multipartFiles) {
        List<FileMetadata> fileMetadataList = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            FileMetadata fileMetadata = storeFile(file);
            fileMetadataList.add(fileMetadata);
        }
        return fileMetadataList;
    }

}
