package com.brendanbuchanan.pt_website_backend.gcs;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.io.ByteArrayInputStream;

public class GCSClientProvider {

    public static Storage getStorage() throws IOException {
        String base64Creds = System.getenv("GCS_CREDS_B64");

        if (base64Creds != null && !base64Creds.isEmpty()) {
            // ✅ Production mode using env var
            byte[] decoded = Base64.getDecoder().decode(base64Creds);
            return StorageOptions.newBuilder()
                    .setCredentials(ServiceAccountCredentials.fromStream(new ByteArrayInputStream(decoded)))
                    .build()
                    .getService();
        } else {
            // ✅ Development mode using local file
            InputStream serviceAccountStream = GCSClientProvider.class
                    .getClassLoader()
                    .getResourceAsStream("gcp-service-account.json");

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