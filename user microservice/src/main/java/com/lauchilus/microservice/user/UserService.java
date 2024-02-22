package com.lauchilus.microservice.user;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lauchilus.microservice.cloudinary.CloudinaryComs;
import com.lauchilus.microservice.cloudinary.Images;
import com.lauchilus.microservice.congif.FirebaseConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class UserService {


    private final FirebaseAuth firebaseAuth;

    private final CloudinaryComs cloudinaryComs;

    public String createUser(UserDto dto) throws FirebaseAuthException {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(dto.email())
                .setDisplayName("")
                .setPhotoUrl("https://e7.pngegg.com/pngimages/84/165/png-clipart-united-states-avatar-organization-information-user-avatar-service-computer-wallpaper-thumbnail.png");

        UserRecord userRecord = firebaseAuth.createUser(request);
        String token = firebaseAuth.createCustomToken(userRecord.getUid());
        return token;
    }

    public UserRecord getUseInfo(String uid) throws FirebaseAuthException {
        UserRecord request = firebaseAuth.getUser(uid);
        return request;
    }

    public UserRecord updateUserInfo(MultipartFile multipartFile,String uid,String username) throws FirebaseAuthException, IOException {
        switch (getCaseType(multipartFile, username)) {
            case 1:
                return updateDisplayNameAndPhoto(uid, username, multipartFile);
            case 2:
                return updateDisplayName(uid, username);
            case 3:
                return updatePhotoUrl(uid, multipartFile);
            default:
                throw new RuntimeException();
    }
    }
    private int getCaseType(MultipartFile multipartFile, String username) {
        if (multipartFile != null && username != null) {
            return 1;
        } else if (multipartFile == null && username != null) {
            return 2;
        } else if (multipartFile != null && username == null) {
            return 3;
        } else {
            return 0;
        }
    }

    private UserRecord updateDisplayName(String uid,String display){
        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid)
                .setDisplayName(display);

        try {
            UserRecord r = firebaseAuth.updateUser(request);
            return r;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private UserRecord updatePhotoUrl(String uid,MultipartFile file) throws IOException {
        String photourl = getImageUrl(file);
        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid)
                .setPhotoUrl(photourl);
        try {
            UserRecord r = firebaseAuth.updateUser(request);
            return r;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getImageUrl(MultipartFile file) throws IOException {
        String photourl = cloudinaryComs.upload(file).imageUrl();
        return photourl;
    }

    private UserRecord updateDisplayNameAndPhoto(String uid,String display,MultipartFile file) throws IOException {
        String photourl = getImageUrl(file);
        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid)
                .setDisplayName(display)
                .setPhotoUrl(photourl);

        try {
            UserRecord r = firebaseAuth.updateUser(request);
            return r;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
