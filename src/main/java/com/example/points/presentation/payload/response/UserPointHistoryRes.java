package com.example.points.presentation.payload.response;

import com.example.points.usecases.business.constant.PointStatusEnum;
import com.example.points.usecases.dto.PointHistoryDto;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UserPointHistoryRes {
    private final Long id;
    private final BigDecimal point;
    private final BigDecimal totalBalance;
    private final PointStatusEnum pointStatus;
    private final String createdAt;

    public UserPointHistoryRes(PointHistoryDto pointHistoryDto) {
        this.id = pointHistoryDto.getId();
        this.point = pointHistoryDto.getPoint();
        this.totalBalance = pointHistoryDto.getTotalBalance();
        this.pointStatus = pointHistoryDto.getPointStatus();
        this.createdAt = pointHistoryDto.getCreatedAt().toString();
    }
}
