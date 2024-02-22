package com.lauchilus.microservice.user;

import org.springframework.web.multipart.MultipartFile;

public record updateDto(
        MultipartFile multipartFile,
        String uid,
        String username
) {
}
