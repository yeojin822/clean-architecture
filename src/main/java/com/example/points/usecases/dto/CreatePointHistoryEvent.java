package com.example.points.usecases.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class CreatePointHistoryEvent {
    private final PointHistoryDto pointHistoryDto;
}
