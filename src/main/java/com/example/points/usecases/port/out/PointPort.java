package com.example.points.usecases.port.out;

import com.example.points.usecases.business.domain.Point;
import com.example.points.usecases.business.domain.Points;

import java.time.LocalDateTime;

public interface PointPort {
    Points findAllByUserId(Long userId);

    void save(Point point);

    void saveAll(Points points);

    Points findAllByAutoExpireAt(LocalDateTime currentDateTime);
}
