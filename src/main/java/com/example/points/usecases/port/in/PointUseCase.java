package com.example.points.usecases.port.in;

import com.example.points.presentation.payload.request.UserPointCreateReq;
import com.example.points.presentation.payload.request.UserPointUpdateReq;
import com.example.points.usecases.dto.TotalPointDto;

public interface PointUseCase {
    TotalPointDto getTotalPointByUserId(Long userId);

    void collectPoint(Long userId, UserPointCreateReq request);

    void usePoint(Long id, UserPointUpdateReq request);
}
