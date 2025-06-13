package com.brendanbuchanan.pt_website_backend.gcs;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class GCSFileService {

    private final Storage storage;
    private final String bucket = "pt-website-storage";

    public GCSFileService() throws IOException {
        this.storage = GCSClientProvider.getStorage();
    }

    public String uploadFile(MultipartFile file) throws IOException {

        // this stores the original file name into this variable e.g. myphoto.jpg
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }

        BlobInfo blobInfo = BlobInfo.newBuilder(bucket, fileName).build();
        storage.create(blobInfo, file.getBytes());

        return "https://storage.googleapis.com/" + bucket + "/" + fileName;
    }
}
