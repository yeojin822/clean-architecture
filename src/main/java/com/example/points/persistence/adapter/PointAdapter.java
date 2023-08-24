package com.example.points.persistence.adapter;

import com.example.points.persistence.entity.PointEntity;
import com.example.points.persistence.repository.PointRepository;
import com.example.points.usecases.business.domain.Point;
import com.example.points.usecases.business.domain.Points;
import com.example.points.usecases.port.out.PointPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PointAdapter implements PointPort {
    private final PointRepository pointRepository;

    @Override
    public Points findAllByUserId(Long userId) {
        List<PointEntity> pointEntities = pointRepository.findAllByUserId(userId);
        return new Points(pointEntities.stream().map(Point::of).collect(Collectors.toList()));
    }

    @Override
    public void save(Point point) {
        pointRepository.save(PointEntity.of(point));
    }

    @Override
    public void saveAll(Points points) {
        pointRepository.saveAll(points.toEntity());
    }

    @Override
    public Points findAllByAutoExpireAt(LocalDateTime currentDateTime) {
        List<PointEntity> pointEntities = pointRepository.findAllByAutoExpireAt(currentDateTime);
        return new Points(pointEntities.stream().map(Point::of).collect(Collectors.toList()));
    }
}
