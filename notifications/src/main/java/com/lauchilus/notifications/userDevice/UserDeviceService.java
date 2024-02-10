package com.lauchilus.notifications.userDevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDeviceService {

    private final UserDeviceRepository userDeviceRepository;

    public void saveUserDevice(String user,String token){
        UserDevice device = UserDevice.builder()
                .userDeviceToken(token)
                .userId(user)
                .build();
        userDeviceRepository.save(device);
    }

    public UserDevice getReferenceByUserDeviceToken(String recipientToken) {
        return userDeviceRepository.getReferenceByUserDeviceToken(recipientToken);
    }
    public UserDevice getUserDeviceByUser(String user) {
        return userDeviceRepository.getReferenceByUserId(user);
    }

    public String getTokenByUser(String user) {
        System.out.println(user);
        UserDevice device = userDeviceRepository.getReferenceByUserId(user);
        return device.getUserDeviceToken();
    }
}
