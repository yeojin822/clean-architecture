package com.example.points.persistence.adapter;

import com.example.points.common.exception.ApiException;
import com.example.points.persistence.entity.UserEntity;
import com.example.points.persistence.repository.UserRepository;
import com.example.points.usecases.business.domain.User;
import com.example.points.usecases.port.out.UserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.points.common.exception.ErrorMessage.NOT_FOUND_USER;

@Component
@RequiredArgsConstructor
public class UserAdapter implements UserPort {
    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        UserEntity userEntity = userRepository.save(UserEntity.of(user));
        return User.of(userEntity);
    }

    @Override
    public User findById(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new ApiException(NOT_FOUND_USER.getMessage()));
        return User.of(userEntity);
    }

    @Override
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }
}
