package com.example.points.persistence.entity;

import com.example.points.usecases.business.constant.PointStatusEnum;
import com.example.points.usecases.dto.PointHistoryDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;


@Getter
@Setter
@ToString
@Entity(name = "point_history")
public class PointHistoryEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private BigDecimal point;
    private BigDecimal totalBalance;
    @Enumerated(value = EnumType.STRING)
    private PointStatusEnum pointStatus;

    public static PointHistoryEntity of(PointHistoryDto pointHistoryDto) {
        PointHistoryEntity pointHistoryEntity = new PointHistoryEntity();
        pointHistoryEntity.setPoint(pointHistoryDto.getPoint());
        pointHistoryEntity.setUserId(pointHistoryDto.getUserId());
        pointHistoryEntity.setPointStatus(pointHistoryDto.getPointStatus());
        pointHistoryEntity.setTotalBalance(pointHistoryDto.getTotalBalance());
        return pointHistoryEntity;
    }
}
