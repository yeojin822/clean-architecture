package com.example.points.service;

import com.example.points.common.exception.ApiException;
import com.example.points.presentation.payload.request.UserPointCreateReq;
import com.example.points.presentation.payload.request.UserPointUpdateReq;
import com.example.points.usecases.business.PointService;
import com.example.points.usecases.business.constant.PointStatusEnum;
import com.example.points.usecases.business.domain.Point;
import com.example.points.usecases.business.domain.Points;
import com.example.points.usecases.business.domain.User;
import com.example.points.usecases.dto.TotalPointDto;
import com.example.points.usecases.port.out.PointPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.points.common.exception.ErrorMessage.POINT_OVER_TOTAL_POINTS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {
    @Mock
    private PointPort pointPort;
    @InjectMocks
    private PointService pointService;
    private final User 유저 = new User(1L, "홍길동", LocalDateTime.now());
    private final Point 적립금1 = Point.collect(유저.getId(), BigDecimal.valueOf(1000), PointStatusEnum.COLLECT, LocalDateTime.now().plusDays(365), null);
    private final Point 적립금2 = Point.collect(유저.getId(), BigDecimal.valueOf(2000), PointStatusEnum.COLLECT, LocalDateTime.now().plusDays(365), null);
    private final Point 적립금3 = Point.collect(유저.getId(), BigDecimal.valueOf(3000), PointStatusEnum.COLLECT, LocalDateTime.now().plusDays(365), null);

    private final Points 유저적립금목록 = new Points(List.of(적립금1, 적립금2, 적립금3));

    @Test
    void 유저_적립금_적립() {
        given(pointPort.findAllByUserId(anyLong())).willReturn(new Points(List.of(적립금1)));
        UserPointCreateReq userPointCreateReq = UserPointCreateReq.builder().point(BigDecimal.valueOf(1000)).autoExpiredDay(365).build();

        pointService.collectPoint(유저.getId(), userPointCreateReq);

        assertThat(pointPort.findAllByUserId(anyLong()).getPoints().size()).isEqualTo(1);
    }

    @Test
    void 유저_음수_적립_에러발생() {
        UserPointCreateReq userPointCreateReq = UserPointCreateReq.builder().point(BigDecimal.valueOf(-100)).autoExpiredDay(365).build();

        assertThatThrownBy(
                () -> pointService.collectPoint(유저.getId(), userPointCreateReq)
        )
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("0보다 커야 합니다");
    }

    @Test
    void 유저_적립금_합계_조회() {

        given(pointPort.findAllByUserId(anyLong())).willReturn(유저적립금목록);

        TotalPointDto totalPoint = pointService.getTotalPointByUserId(유저.getId());
        assertAll(
                () -> assertThat(totalPoint.getUserId()).isEqualTo(유저.getId()),
                () -> assertThat(totalPoint.getTotalPoint()).isEqualTo(BigDecimal.valueOf(6000))
        );
    }


//    @Test
//    void 회원별_적립금_적립_사용_내역_조회() {
//        given(memberRepository.findById(anyLong())).willReturn(Optional.of(유저));
//        given(reserveRepository.findAllByMemberId(anyLong(), any())).willReturn(Arrays.asList(적립금1, 적립금2, 적립금3));
//
//        List<MemberReserveHistoryResponse> response =
//                memberService.getReserveHistoryByMember(유저.getId(), PageRequest.of(0, 5));
//
//        assertAll(
//                () -> assertThat(response).hasSize(3),
//                () -> assertThat(response.get(0).getMemberId()).isEqualTo(유저.getId()),
//                () -> assertThat(response.get(0).getAmount()).isEqualTo(10),
//                () -> assertThat(response.get(0).getBalance()).isEqualTo(10),
//                () -> assertThat(response.get(0).getStatus()).isEqualTo(ReserveStatus.SAVE),
//                () -> assertThat(response.get(1).getMemberId()).isEqualTo(유저.getId()),
//                () -> assertThat(response.get(1).getAmount()).isEqualTo(5),
//                () -> assertThat(response.get(1).getBalance()).isEqualTo(5),
//                () -> assertThat(response.get(1).getStatus()).isEqualTo(ReserveStatus.SAVE),
//                () -> assertThat(response.get(2).getMemberId()).isEqualTo(유저.getId()),
//                () -> assertThat(response.get(2).getAmount()).isEqualTo(1),
//                () -> assertThat(response.get(2).getBalance()).isEqualTo(1),
//                () -> assertThat(response.get(2).getStatus()).isEqualTo(ReserveStatus.SAVE)
//        );
//    }

    @Test
    void 회원별_적립금_사용() {
        given(pointPort.findAllByUserId(anyLong())).willReturn(유저적립금목록);
        UserPointUpdateReq userPointUpdateReq = UserPointUpdateReq.builder().point(BigDecimal.valueOf(1000)).build();
        pointService.usePoint(유저.getId(), userPointUpdateReq);
        pointService.usePoint(유저.getId(), userPointUpdateReq);
        pointService.usePoint(유저.getId(), userPointUpdateReq);

        assertThat(유저적립금목록.getTotal()).isEqualTo(BigDecimal.valueOf(3000));
    }

    @Test
    void 적립금_보다_큰_금액_사용() {
        given(pointPort.findAllByUserId(anyLong())).willReturn(유저적립금목록);
        UserPointUpdateReq userPointUpdateReq = UserPointUpdateReq.builder().point(BigDecimal.valueOf(8000)).build();

        assertThatThrownBy(
                () -> pointService.usePoint(유저.getId(), userPointUpdateReq)
        )
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(POINT_OVER_TOTAL_POINTS.getMessage());
    }

}
