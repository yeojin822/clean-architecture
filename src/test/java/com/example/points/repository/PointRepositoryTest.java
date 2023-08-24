package com.example.points.repository;

import com.example.points.persistence.entity.PointEntity;
import com.example.points.persistence.repository.PointRepository;
import com.example.points.usecases.business.constant.PointStatusEnum;
import com.example.points.usecases.business.domain.Point;
import com.example.points.usecases.business.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class PointRepositoryTest {
    @Autowired
    PointRepository pointRepository;

    User 회원;
    PointEntity 적립금1;
    PointEntity 적립금2;
    PointEntity 적립금3;

    @BeforeEach
    void init() {
        회원 = new User(1L, "홍길동", LocalDateTime.now());
        적립금1 = PointEntity.of(Point.collect(회원.getId(), BigDecimal.valueOf(1000), PointStatusEnum.COLLECT, LocalDateTime.now().plusDays(365), null));
        적립금2 = PointEntity.of(Point.collect(회원.getId(), BigDecimal.valueOf(2000), PointStatusEnum.COLLECT, LocalDateTime.now().plusDays(365), null));
        적립금3 = PointEntity.of(Point.collect(회원.getId(), BigDecimal.valueOf(3000), PointStatusEnum.COLLECT, LocalDateTime.now().plusDays(365), null));

        pointRepository.save(적립금1);
        pointRepository.save(적립금2);
        pointRepository.save(적립금3);

    }

    @Test
    void 적립금_조회() {
        List<PointEntity> points = pointRepository.findAllByUserId(회원.getId());
        assertThat(points).hasSize(3);
    }

    @Test
    void 만료적립금_제외_조회() {
        적립금1.setExpiredAt(LocalDateTime.now());
        pointRepository.save(적립금1);

        List<PointEntity> points = pointRepository.findAllByUserId(회원.getId());
        assertThat(points).hasSize(2);
    }
}
