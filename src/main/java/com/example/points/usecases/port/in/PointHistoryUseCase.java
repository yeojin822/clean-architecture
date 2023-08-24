package com.example.points.usecases.port.in;

import com.example.points.usecases.dto.PointHistoryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PointHistoryUseCase {
    List<PointHistoryDto> getPointHistories(Long userId, Pageable pageable);
}
