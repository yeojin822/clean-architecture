package com.example.points.presentation.payload.response;

import com.example.points.usecases.dto.TotalPointDto;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UserTotalPointRes {
    private final Long userId;
    private final BigDecimal totalPoint;

    public UserTotalPointRes(TotalPointDto totalPointDto) {
        userId = totalPointDto.getUserId();
        totalPoint = totalPointDto.getTotalPoint();
    }
}
