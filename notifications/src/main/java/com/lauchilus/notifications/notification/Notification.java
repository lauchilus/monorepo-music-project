package com.lauchilus.notifications.notification;

import com.lauchilus.notifications.userDevice.UserDevice;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "notifications")
@Table(name = "notifications")
public class Notification {

    @Id
    @UuidGenerator
    private String id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipient_id")
    private UserDevice recipientId;
    private String senderId;
    private String type;
    private String title;
    private String body;
}
