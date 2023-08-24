package com.example.points.usecases.dto;

import com.example.points.persistence.entity.PointHistoryEntity;
import com.example.points.usecases.business.constant.PointStatusEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@ToString
public class PointHistoryDto {
    private Long id;
    private final Long userId;
    private final BigDecimal point;
    private final BigDecimal totalBalance;
    private final PointStatusEnum pointStatus;
    private LocalDateTime createdAt;

    public static PointHistoryDto of(PointHistoryEntity entity) {
        return new PointHistoryDto(entity.getId(),
                entity.getUserId(),
                entity.getPoint(),
                entity.getTotalBalance(),
                entity.getPointStatus(),
                entity.getCreatedAt());
    }

}
