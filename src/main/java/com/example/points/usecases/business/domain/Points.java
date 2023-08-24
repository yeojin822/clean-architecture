package com.example.points.usecases.business.domain;

import com.example.points.common.exception.ApiException;
import com.example.points.persistence.entity.PointEntity;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.points.common.exception.ErrorMessage.POINT_OVER_TOTAL_POINTS;

@Getter
public class Points {
    private final List<Point> points;

    public Points(List<Point> points) {
        this.points = points;
    }

    public BigDecimal getTotal() {
        return points.stream()
                .map(Point::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void delete() {
        points.forEach(Point::delete);
    }


    public void use(BigDecimal usage) {
        isOverTotalPoints(usage);

        int cursor = 0;
        while (true) {
            if (points.get(cursor).isUse()) {
                cursor++;
                continue;
            }

            BigDecimal changes = points.get(cursor).use(usage);
            if (isZero(changes)) {
                break;
            }
            cursor++;
            usage = changes;
        }
    }

    private boolean isZero(BigDecimal changes) {
        return changes.compareTo(BigDecimal.ZERO) == 0;
    }

    private void isOverTotalPoints(BigDecimal point) {
        if (getTotal().compareTo(point) < 0) {
            throw new ApiException(POINT_OVER_TOTAL_POINTS.getMessage());
        }
    }

    public List<PointEntity> toEntity() {
        return points.stream().map(PointEntity::of).collect(Collectors.toList());
    }
}
