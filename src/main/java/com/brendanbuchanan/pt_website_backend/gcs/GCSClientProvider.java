package com.brendanbuchanan.pt_website_backend.gcs;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class GCSClientProvider {


    public static Storage getStorage() throws IOException {
        try (InputStream serviceAccountStream = GCSClientProvider.class
                .getClassLoader()
                .getResourceAsStream("gcp-service-account.json")) {
            if (serviceAccountStream == null) {
                throw new IOException("gcp-service-account.json not found in classpath");
            }
            return StorageOptions.newBuilder()
                    .setCredentials(ServiceAccountCredentials.fromStream(serviceAccountStream))
                    .build()
                    .getService();
        }
    }

}