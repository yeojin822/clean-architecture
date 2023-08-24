package com.example.points.persistence.adapter;

import com.example.points.persistence.entity.PointHistoryEntity;
import com.example.points.persistence.repository.PointHistoryRepository;
import com.example.points.usecases.dto.PointHistoryDto;
import com.example.points.usecases.port.out.PointHistoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class PointHistoryAdapter implements PointHistoryPort {
    private final PointHistoryRepository pointHistoryRepository;


    @Override
    public void save(PointHistoryDto pointHistoryDto) {
        PointHistoryEntity entity = PointHistoryEntity.of(pointHistoryDto);
        pointHistoryRepository.save(entity);
    }

    @Override
    public List<PointHistoryDto> findAllByUserId(Long userId, Pageable pageable) {
        List<PointHistoryEntity> histories = pointHistoryRepository.findAllByUserId(userId, pageable);
        return histories.stream().map(PointHistoryDto::of).collect(Collectors.toList());
    }

}
