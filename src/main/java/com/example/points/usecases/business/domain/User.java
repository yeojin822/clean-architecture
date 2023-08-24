package com.example.points.usecases.business.domain;

import com.example.points.persistence.entity.UserEntity;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class User {
    Long id;
    String name;
    LocalDateTime createdAt;

    public static User of(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getName(), userEntity.getCreatedAt());
    }
}
