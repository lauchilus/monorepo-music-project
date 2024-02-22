package com.lauchilus.microservice.user;

public record LoginDto(
        String email,
        String password
) {
}
