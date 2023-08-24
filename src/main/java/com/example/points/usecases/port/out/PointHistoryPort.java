package com.example.points.usecases.port.out;

import com.example.points.usecases.dto.PointHistoryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PointHistoryPort {

    void save(PointHistoryDto pointHistoryDto);

    List<PointHistoryDto> findAllByUserId(Long userId, Pageable pageable);
}
