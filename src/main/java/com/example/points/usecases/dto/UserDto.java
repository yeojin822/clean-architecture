package com.example.points.usecases.dto;

import com.example.points.usecases.business.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDto {
    private final Long userId;
    private final String name;
    private final LocalDateTime createdAt;

    public static UserDto of(User user) {
        return new UserDto(user.getId(), user.getName(), user.getCreatedAt());
    }
}
