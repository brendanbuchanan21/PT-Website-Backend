package com.brendanbuchanan.pt_website_backend.config;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.util.Base64;


// essentially just a connection to firebase
public class FirebaseConfig {

    @PostConstruct
    public void init() throws IOException {
        GoogleCredentials credentials;

        String base64Creds = System.getenv("FIREBASE_CREDS_B64");

        if (base64Creds != null && !base64Creds.isEmpty()) {
            // Production: decode base64 from env
            byte[] decodedBytes = Base64.getDecoder().decode(base64Creds);
            try (ByteArrayInputStream serviceAccount = new ByteArrayInputStream(decodedBytes)) {
                credentials = GoogleCredentials.fromStream(serviceAccount);
            }
        } else {
            // Development: load from local file
            try (InputStream serviceAccount = getClass().getClassLoader()
                    .getResourceAsStream("firebase-service-account.json")) {
                if (serviceAccount == null) {
                    throw new IOException("firebase-service-account.json not found in classpath");
                }
                credentials = GoogleCredentials.fromStream(serviceAccount);
            }
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }
}