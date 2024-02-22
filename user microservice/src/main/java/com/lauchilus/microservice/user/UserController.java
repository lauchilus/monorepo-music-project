package com.lauchilus.microservice.user;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> createUserResponseToken(@RequestBody UserDto dto) throws FirebaseAuthException {
        return new ResponseEntity<>(userService.createUser(dto), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<UserRecord> asdasd(@RequestPart(value = "multipartfile",required = false) MultipartFile multipartfile,@RequestPart("uid") String uid,@RequestPart(value = "username",required = false) String username) throws FirebaseAuthException, IOException {
        return new ResponseEntity<>(userService.updateUserInfo(multipartfile,uid, username), HttpStatus.CREATED);
    }

    @GetMapping("/{uid}")
    public ResponseEntity<UserRecord> getUSerProfileInfo(@PathVariable String uid) throws FirebaseAuthException {
        return new ResponseEntity<UserRecord>(userService.getUseInfo(uid),HttpStatus.OK);
    }

}
