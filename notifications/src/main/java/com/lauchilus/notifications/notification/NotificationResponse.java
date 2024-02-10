package com.lauchilus.notifications.notification;

public record NotificationResponse(
        String id,
        String type,
        String title,
        String body
) {
}
