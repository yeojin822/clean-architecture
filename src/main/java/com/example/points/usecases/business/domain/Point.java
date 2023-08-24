package com.example.points.usecases.business.domain;

import com.example.points.common.util.SelfValidating;
import com.example.points.persistence.entity.PointEntity;
import com.example.points.usecases.business.constant.PointStatusEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(callSuper = false)
public class Point implements SelfValidating<Point> {
    private final Long id;
    @Positive
    private final Long userId;
    @Positive
    private final BigDecimal point;
    private BigDecimal balance;
    @NotNull
    private PointStatusEnum pointStatus;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    @NotNull
    private final LocalDateTime autoExpireAt;
    private LocalDateTime expiredAt;

    private Point(Long id, Long userId, BigDecimal point, BigDecimal balance, PointStatusEnum pointStatus, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime autoExpireAt, LocalDateTime expiredAt) {
        this.id = id;
        this.userId = userId;
        this.point = point;
        this.balance = balance;
        this.pointStatus = pointStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.autoExpireAt = autoExpireAt;
        this.expiredAt = expiredAt;

        this.validateSelf();
    }

    public static Point collect(Long userId, BigDecimal point, PointStatusEnum pointStatus, LocalDateTime autoExpireAt, LocalDateTime expiredAt) {
        return new Point(null,
                userId,
                point,
                point,
                pointStatus,
                null,
                null,
                autoExpireAt,
                expiredAt);
    }

    public static Point of(PointEntity entity) {
        return new Point(entity.getId(),
                entity.getUserId(),
                entity.getPoint(),
                entity.getBalance(),
                entity.getPointStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getAutoExpireAt(),
                entity.getExpiredAt());
    }

    public BigDecimal use(BigDecimal usage) {
        if (isUse()) {
            return usage;
        }

        if (isEqualsBalance(usage)) {
            this.pointStatus = PointStatusEnum.USE;
            this.balance = this.balance.subtract(usage);
            return BigDecimal.ZERO;
        }
        if (isLessThanBalance(usage)) {
            this.balance = this.balance.subtract(usage);
            return BigDecimal.ZERO;
        }
        this.pointStatus = PointStatusEnum.USE;
        BigDecimal changes = usage.subtract(this.balance);
        this.balance = BigDecimal.ZERO;
        return changes;
    }

    private boolean isLessThanBalance(BigDecimal usage) {
        return this.balance.compareTo(usage) > 0;
    }

    private boolean isEqualsBalance(BigDecimal usage) {
        return this.balance.compareTo(usage) == 0;
    }

    public boolean isUse() {
        return this.pointStatus == PointStatusEnum.USE;
    }

    public void delete() {
        this.expiredAt = LocalDateTime.now();
    }
}
