package com.example.points.usecases.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TotalPointDto {
    private final Long userId;
    private final BigDecimal totalPoint;

    public static TotalPointDto of(Long userId, BigDecimal totalPoint) {
        return new TotalPointDto(userId, totalPoint);
    }
}
