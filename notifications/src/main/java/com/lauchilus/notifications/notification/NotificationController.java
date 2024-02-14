package com.lauchilus.notifications.notification;

import com.lauchilus.notifications.config.FirebaseMessagingService;
import com.lauchilus.notifications.userDevice.UserDevicePost;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final FirebaseMessagingService firebaseMessagingService;
    private final NotificationService notificationService;

    @PostMapping
    public String firebaseNotification(@RequestBody NotificationMessage notificationMessage){
        return  firebaseMessagingService.sendNotificationByToken(notificationMessage);
    }

    @PostMapping("/register-device")
    public ResponseEntity<String> saveUserDevice(@RequestBody UserDevicePost device){
        notificationService.saveUserDevice(device);
        return new ResponseEntity<String>("UserDevice Registered", HttpStatus.CREATED);
    }

    @GetMapping("/notifications/{user}")
    public ResponseEntity<List<NotificationResponse>> getAllNotificationsFromUser(@PathVariable String user){
        return new ResponseEntity<List<NotificationResponse>>(notificationService.getAllNotificationFromUser(user),HttpStatus.OK);
    }

}
