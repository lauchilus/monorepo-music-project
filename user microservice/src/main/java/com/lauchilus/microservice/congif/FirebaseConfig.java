package com.lauchilus.microservice.congif;

import com.cloudinary.Cloudinary;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class FirebaseConfig {

    @Value("${cloudinary.cloudName}")
    private String cloudName;
    @Value("${cloudinary.apikey}")
    private String apiKey;
    @Value("${cloudinary.apiSecret}")
    private String apiSecret;

    @Bean
    FirebaseAuth firebaseAuth() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(
                new ClassPathResource("firebase-service-account.json").getInputStream()
        );

        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "hearu-a4fac");

        return FirebaseAuth.getInstance(app);
    }


    @Bean
    Cloudinary CloudinaryService() {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("cloud_name", cloudName);
        valuesMap.put("api_key", apiKey);
        valuesMap.put("api_secret", apiSecret);
        return new Cloudinary(valuesMap);
    }



}





