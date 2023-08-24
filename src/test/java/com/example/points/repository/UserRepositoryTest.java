package com.example.points.repository;

import com.example.points.persistence.entity.UserEntity;
import com.example.points.persistence.repository.UserRepository;
import com.example.points.usecases.business.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
@DataJpaTest
@ExtendWith(SpringExtension.class)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private UserEntity 회원1;

    @BeforeEach
    void before() {
        회원1 = UserEntity.of(new User(1L, "홍길동", LocalDateTime.now()));
    }

    @Test
    void 유저_저장() {
        userRepository.save(회원1);

        UserEntity userEntity = userRepository.findById(회원1.getId()).get();

        assertAll(
                () -> assertThat(userEntity).isNotNull(),
                () -> assertThat(userEntity.getId()).isEqualTo(1L)
        );
    }
}
