package com.example.points.usecases.dto;

import com.example.points.usecases.business.constant.PointStatusEnum;
import com.example.points.usecases.business.domain.Point;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PointDto {
    private final Long id;
    private final Long userId;
    private final BigDecimal point;
    private final BigDecimal remainingPoint;
    private final PointStatusEnum pointStatus;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime autoExpireAt;
    private final LocalDateTime expiredAt;

    public static PointDto of(Point point) {
        return new PointDto(point.getId(),
                point.getUserId(),
                point.getPoint(),
                point.getBalance(),
                point.getPointStatus(),
                point.getCreatedAt(),
                point.getUpdatedAt(),
                point.getAutoExpireAt(),
                point.getExpiredAt());
    }
}
