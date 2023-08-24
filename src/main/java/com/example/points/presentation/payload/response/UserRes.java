package com.example.points.presentation.payload.response;

import com.example.points.usecases.dto.UserDto;
import lombok.Getter;

@Getter
public class UserRes {
    private final Long userId;
    private final String name;
    private final String createdAt;

    public UserRes(UserDto user) {
        userId = user.getUserId();
        name = user.getName();
        createdAt = user.getCreatedAt().toString();
    }
}
