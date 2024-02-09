package com.lauchilus.notifications.notification;

import lombok.Data;

import java.util.Map;

@Data
public class NotificationMessage {
    private String recipientToken;
    private String title;
    private String body;
    private String username;
    private Map<String, String> data;

}
