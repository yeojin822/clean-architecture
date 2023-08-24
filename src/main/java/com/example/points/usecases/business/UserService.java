package com.example.points.usecases.business;

import com.example.points.presentation.payload.request.UserCreateReq;
import com.example.points.usecases.business.domain.User;
import com.example.points.usecases.dto.UserDto;
import com.example.points.usecases.port.in.UserUseCase;
import com.example.points.usecases.port.out.UserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {
    private final UserPort userPort;

    @Override
    @Transactional
    public UserDto create(UserCreateReq request) {
        User user = userPort.save(new User(null, request.getName(), null));
        return UserDto.of(user);
    }

    @Override
    public UserDto find(Long userId) {
        User user = userPort.findById(userId);
        return UserDto.of(user);
    }

    @Override
    public void delete(Long userId) {
        userPort.deleteById(userId);
    }
}
