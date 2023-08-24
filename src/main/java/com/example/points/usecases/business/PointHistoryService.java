package com.example.points.usecases.business;

import com.example.points.usecases.dto.PointHistoryDto;
import com.example.points.usecases.port.in.PointHistoryUseCase;
import com.example.points.usecases.port.out.PointHistoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointHistoryService implements PointHistoryUseCase {
    private final PointHistoryPort pointHistoryPort;

    @Override
    public List<PointHistoryDto> getPointHistories(Long userId, Pageable pageable) {
        return pointHistoryPort.findAllByUserId(userId, pageable);
    }
}
