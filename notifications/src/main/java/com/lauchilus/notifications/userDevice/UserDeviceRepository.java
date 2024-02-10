package com.lauchilus.notifications.userDevice;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, String> {
    UserDevice getReferenceByUserDeviceToken(String recipientToken);

    UserDevice getReferenceByUserId(String user);
}
