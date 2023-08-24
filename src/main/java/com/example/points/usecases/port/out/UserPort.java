package com.example.points.usecases.port.out;

import com.example.points.usecases.business.domain.User;

public interface UserPort {
    User save(User user);

    User findById(Long userId);

    void deleteById(Long userId);
}
