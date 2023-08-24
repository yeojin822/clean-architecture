package com.example.points.presentation.payload.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class UserPointUpdateReq {
    @Min(1)
    private BigDecimal point;

    @Builder
    private UserPointUpdateReq(BigDecimal point) {
        this.point = point;
    }
}
