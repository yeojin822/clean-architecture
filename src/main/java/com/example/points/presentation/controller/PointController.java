package com.example.points.presentation.controller;

import com.example.points.presentation.payload.request.UserPointCreateReq;
import com.example.points.presentation.payload.request.UserPointUpdateReq;
import com.example.points.presentation.payload.response.UserPointHistoryRes;
import com.example.points.presentation.payload.response.UserTotalPointRes;
import com.example.points.usecases.dto.PointHistoryDto;
import com.example.points.usecases.dto.TotalPointDto;
import com.example.points.usecases.port.in.PointHistoryUseCase;
import com.example.points.usecases.port.in.PointUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/point")
@RestController
public class PointController {
    private final PointUseCase pointUseCase;
    private final PointHistoryUseCase pointHistoryUseCase;

    @GetMapping("/total")
    public ResponseEntity<UserTotalPointRes> getTotalReserveByMember(@PathVariable Long userId) {
        TotalPointDto totalPoint = pointUseCase.getTotalPointByUserId(userId);
        return ResponseEntity.ok().body(new UserTotalPointRes(totalPoint));
    }

    @PostMapping
    public ResponseEntity<?> collectPoints(@PathVariable Long userId, @RequestBody @Valid UserPointCreateReq request) {
        pointUseCase.collectPoint(userId, request);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/history")
    public ResponseEntity<List<UserPointHistoryRes>> getPointHistories(@PathVariable Long userId, Pageable pageable) {
        List<PointHistoryDto> pointHistories = pointHistoryUseCase.getPointHistories(userId, pageable);
        return ResponseEntity.ok().body(pointHistories.stream().map(UserPointHistoryRes::new).collect(Collectors.toList()));
    }

    @PutMapping
    public ResponseEntity<?> usePoint(@PathVariable Long userId, @RequestBody @Valid UserPointUpdateReq request) {
        pointUseCase.usePoint(userId, request);
        return ResponseEntity.ok().build();
    }
}
