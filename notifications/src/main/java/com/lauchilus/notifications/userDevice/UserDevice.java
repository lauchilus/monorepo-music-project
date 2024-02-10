package com.lauchilus.notifications.userDevice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lauchilus.notifications.notification.Notification;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "user_devices")
@Table(name = "user_devices")
public class UserDevice {

    @Id
    @UuidGenerator
    private String id;
    private String userId;
    private String userDeviceToken;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(fetch = FetchType.LAZY)
    private List<Notification> notifications = new ArrayList<>();
}
