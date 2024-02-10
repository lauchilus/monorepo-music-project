package com.lauchilus.notifications.notification;

import com.lauchilus.notifications.userDevice.UserDevice;
import com.lauchilus.notifications.userDevice.UserDevicePost;
import com.lauchilus.notifications.userDevice.UserDeviceService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserDeviceService userDeviceService;
    private final NotificationRepository notificationRepository;

    public void saveNotification(NotificationMessage msg){
        UserDevice user = userDeviceService.getUserDeviceByUser(msg.getRecipientToken());
        Notification notification = Notification.builder()
                .body(msg.getBody())
                .title(msg.getTitle())
                .type(msg.getType())
                .recipientId(user)
                .senderId(msg.getUsername())
                .build();

        notificationRepository.save(notification);

    }

    public void saveUserDevice(UserDevicePost device){
        userDeviceService.saveUserDevice(device.userId(), device.token());
    }

    public String getUserDeviceToken(String user){
        return userDeviceService.getTokenByUser(user);
    }

    public List<NotificationResponse> getAllNotificationFromUser(String userId){

        UserDevice device = userDeviceService.getUserDeviceByUser(userId);
        List<NotificationResponse> response = notificationRepository.findAllByRecipientId(device).stream().map(
               notification -> new NotificationResponse(notification.getId(),notification.getType(),notification.getTitle(),notification.getBody())
        ).toList();
        return response;
    }

}
