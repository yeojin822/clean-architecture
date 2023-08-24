package com.example.points.usecases.port.in;

import com.example.points.presentation.payload.request.UserCreateReq;
import com.example.points.usecases.dto.UserDto;

public interface UserUseCase {
    UserDto create(UserCreateReq request);

    UserDto find(Long userId);

    void delete(Long userId);
}
