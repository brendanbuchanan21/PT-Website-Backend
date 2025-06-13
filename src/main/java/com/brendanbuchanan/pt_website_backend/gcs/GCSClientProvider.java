package com.brendanbuchanan.pt_website_backend.gcs;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.FileInputStream;
import java.io.IOException;

public class GCSClientProvider {


    public static Storage getStorage() throws IOException {
        return StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(
                        new FileInputStream("src/main/resources/gcp-service-account.json")
                ))
                .build()
                .getService();
    }

}