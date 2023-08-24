package com.example.points.domain;

import com.example.points.usecases.business.constant.PointStatusEnum;
import com.example.points.usecases.business.domain.Point;
import com.example.points.usecases.business.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class PointTest {
    private final User 회원 = new User(1L, "홍길동", LocalDateTime.now());

    private Point 적립금_생성(BigDecimal point, Integer autoExpireDay){
        return Point.collect(회원.getId(), point, PointStatusEnum.COLLECT, LocalDateTime.now().plusDays(autoExpireDay), null);
    }

    @Test
    void 적립금_생성_확인() {
        Point 적립금 = 적립금_생성(BigDecimal.valueOf(1000), 365);

        assertAll(
                () -> assertThat(적립금.getPoint()).isEqualTo(BigDecimal.valueOf(1000)),
                () -> assertThat(적립금.getBalance()).isEqualTo(BigDecimal.valueOf(1000)),
                () -> assertThat(적립금.getExpiredAt()).isNull()
        );
    }

    @Test
    void 적립금_만료() {
        Point 적립금 = 적립금_생성(BigDecimal.valueOf(1000), 365);

        적립금.delete();

        assertThat(적립금.getExpiredAt()).isNotNull();
    }

    @Test
    void 개별적립금이_사용적립금_같은_경우() {
        Point 적립금 = 적립금_생성(BigDecimal.valueOf(1000), 365);
        BigDecimal 사용부족포인트 = 적립금.use(BigDecimal.valueOf(1000));

        assertAll(
                () -> assertThat(사용부족포인트).isEqualTo(BigDecimal.ZERO),
                () -> assertThat(적립금.getPoint()).isEqualTo(BigDecimal.valueOf(1000)),
                () -> assertThat(적립금.getBalance()).isEqualTo(BigDecimal.ZERO),
                () -> assertThat(적립금.getPointStatus()).isEqualTo(PointStatusEnum.USE)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -10, -100, -1000})
    void 사용할_적립금이_0_또는_음수인_경우(int usage) {
        assertThatThrownBy(() -> 적립금_생성(new BigDecimal(usage), 365))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("0보다 커야 합니다");
    }

    @Test
    void 사용적립금이_개별적립금보다_작은_경우() {
        Point 적립금 = 적립금_생성(BigDecimal.valueOf(1000), 365);
        BigDecimal 사용부족포인트 = 적립금.use(BigDecimal.valueOf(500));

        assertAll(
                () -> assertThat(사용부족포인트).isEqualTo(BigDecimal.ZERO),
                () -> assertThat(적립금.getPoint()).isEqualTo(BigDecimal.valueOf(1000)),
                () -> assertThat(적립금.getBalance()).isEqualTo(BigDecimal.valueOf(500)),
                () -> assertThat(적립금.getPointStatus()).isEqualTo(PointStatusEnum.COLLECT)
        );
    }

    @Test
    void 사용적립금이_개별적립금보다_큰_경우() {
        Point 적립금 = 적립금_생성(BigDecimal.valueOf(1000), 365);
        BigDecimal 사용부족포인트 = 적립금.use(BigDecimal.valueOf(1500));

        assertAll(
                () -> assertThat(사용부족포인트).isEqualTo(BigDecimal.valueOf(500)),
                () -> assertThat(적립금.getPoint()).isEqualTo(BigDecimal.valueOf(1000)),
                () -> assertThat(적립금.getBalance()).isEqualTo(BigDecimal.ZERO),
                () -> assertThat(적립금.getPointStatus()).isEqualTo(PointStatusEnum.USE)
        );
    }


}
