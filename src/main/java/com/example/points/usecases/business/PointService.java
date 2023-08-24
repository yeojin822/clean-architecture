package com.example.points.usecases.business;

import com.example.points.presentation.payload.request.UserPointCreateReq;
import com.example.points.presentation.payload.request.UserPointUpdateReq;
import com.example.points.usecases.business.constant.PointStatusEnum;
import com.example.points.usecases.business.domain.Point;
import com.example.points.usecases.business.domain.Points;
import com.example.points.usecases.dto.CreatePointHistoryEvent;
import com.example.points.usecases.dto.PointHistoryDto;
import com.example.points.usecases.dto.TotalPointDto;
import com.example.points.usecases.port.in.PointUseCase;
import com.example.points.usecases.port.out.PointPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointService implements PointUseCase {
    private final PointPort pointPort;
    private final ApplicationEventPublisher publisher;

    @Override
    public TotalPointDto getTotalPointByUserId(Long userId) {
        Points points = pointPort.findAllByUserId(userId);
        return TotalPointDto.of(userId, points.getTotal());
    }

    @Override
    @Transactional
    public void collectPoint(Long userId, UserPointCreateReq request) {
        Point point = Point.collect(userId, request.getPoint(), request.getPointStatusEnum(), request.autoExpireDate(), null);
        pointPort.save(point);

        Points points = pointPort.findAllByUserId(userId);

        publisher.publishEvent(new CreatePointHistoryEvent(new PointHistoryDto(
                  userId
                , request.getPoint()
                , points.getTotal()
                , request.getPointStatusEnum())));
    }

    @Override
    @Transactional
    public void usePoint(Long userId, UserPointUpdateReq request) {
        Points points = pointPort.findAllByUserId(userId);
        points.use(request.getPoint());
        pointPort.saveAll(points);

        publisher.publishEvent(new CreatePointHistoryEvent(new PointHistoryDto(
                  userId
                , request.getPoint()
                , points.getTotal()
                , PointStatusEnum.USE)));
    }
}
