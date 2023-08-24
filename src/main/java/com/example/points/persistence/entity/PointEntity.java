package com.example.points.persistence.entity;

import com.example.points.usecases.business.constant.PointStatusEnum;
import com.example.points.usecases.business.domain.Point;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "point")
@Getter
@Setter
@ToString
public class PointEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;
    private BigDecimal point;
    private Long userId;
    private BigDecimal balance;
    @Enumerated(value = EnumType.STRING)
    private PointStatusEnum pointStatus;
    private LocalDateTime autoExpireAt;
    private LocalDateTime expiredAt;


    public static PointEntity of(Point point) {
        PointEntity pointEntity = new PointEntity();
        pointEntity.setId(point.getId());
        pointEntity.setPoint(point.getPoint());
        pointEntity.setUserId(point.getUserId());
        pointEntity.setBalance(point.getBalance());
        pointEntity.setPointStatus(point.getPointStatus());
        pointEntity.setAutoExpireAt(point.getAutoExpireAt());
        return pointEntity;
    }
}
