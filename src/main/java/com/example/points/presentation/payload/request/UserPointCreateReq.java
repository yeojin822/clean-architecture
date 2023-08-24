package com.example.points.presentation.payload.request;

import com.example.points.usecases.business.constant.PointStatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.example.points.usecases.business.constant.PointStatusEnum.COLLECT;

@Getter
@Setter
@NoArgsConstructor
public class UserPointCreateReq {
    @Min(1)
    private BigDecimal point;
    @Min(1)
    private Integer autoExpiredDay;
    private final PointStatusEnum pointStatusEnum = COLLECT;

    public LocalDateTime autoExpireDate() {
        return LocalDateTime.now().plusDays(this.autoExpiredDay);
    }

    @Builder
    private UserPointCreateReq(BigDecimal point, Integer autoExpiredDay) {
        this.point = point;
        this.autoExpiredDay = autoExpiredDay;
    }
}
