package com.example.points.domain;

import com.example.points.common.exception.ApiException;
import com.example.points.usecases.business.constant.PointStatusEnum;
import com.example.points.usecases.business.domain.Point;
import com.example.points.usecases.business.domain.Points;
import com.example.points.usecases.business.domain.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.points.common.exception.ErrorMessage.POINT_OVER_TOTAL_POINTS;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PointsTest {
    private final User 회원 = new User(1L, "홍길동", LocalDateTime.now());
    private final Point 적립금1 = Point.collect(회원.getId(), BigDecimal.valueOf(1000), PointStatusEnum.COLLECT, LocalDateTime.now().plusDays(365), null);
    private final Point 적립금2 = Point.collect(회원.getId(), BigDecimal.valueOf(2000), PointStatusEnum.COLLECT, LocalDateTime.now().plusDays(365), null);
    private final Point 적립금3 = Point.collect(회원.getId(), BigDecimal.valueOf(3000), PointStatusEnum.COLLECT, LocalDateTime.now().plusDays(365), null);

    private Points 회원_포인트_리스트() {
        return new Points(List.of(적립금1, 적립금2, 적립금3));
    }

    @Test
    void 적립금_총_합계() {
        Points points = 회원_포인트_리스트();
        assertThat(points.getTotal()).isEqualTo(BigDecimal.valueOf(6000));
    }

    @Test
    void 보유적립금과_사용적립금이_같은_경우_사용() {
        Points points = 회원_포인트_리스트();

        points.use(BigDecimal.valueOf(1000));

        assertThat(points.getTotal()).isEqualTo(BigDecimal.valueOf(5000));
        assertThat(points.getPoints().get(0).getPointStatus()).isEqualTo(PointStatusEnum.USE);
    }

    @Test
    void 개별적립금이_사용적립금_보다_큰_경우_사용() {
        Points points = 회원_포인트_리스트();

        points.use(BigDecimal.valueOf(500));

        assertThat(points.getTotal()).isEqualTo(BigDecimal.valueOf(5500));
        assertThat(points.getPoints().get(0).getPointStatus()).isEqualTo(PointStatusEnum.COLLECT);
    }

    @Test
    void 개별적립금이_보다_사용_금액이_큰_경우_사용() {
        Points points = 회원_포인트_리스트();

        points.use(BigDecimal.valueOf(2000));

        assertThat(points.getTotal()).isEqualTo(BigDecimal.valueOf(4000));
        assertThat(points.getPoints().get(0).getPointStatus()).isEqualTo(PointStatusEnum.USE);
        assertThat(points.getPoints().get(1).getPointStatus()).isEqualTo(PointStatusEnum.COLLECT);
        assertThat(points.getPoints().get(1).getBalance()).isEqualTo(BigDecimal.valueOf(1000));
    }

    @Test
    void 총_적립금액_보다_사용_금액이_큰_경우() {
        Points points = 회원_포인트_리스트();

        assertThatThrownBy(
                () -> points.use(BigDecimal.valueOf(7000)))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(POINT_OVER_TOTAL_POINTS.getMessage());
    }
}
