package com.lauchilus.notifications.notification;

import com.lauchilus.notifications.userDevice.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
    List<Notification> findAllByRecipientId(UserDevice userId);
}
