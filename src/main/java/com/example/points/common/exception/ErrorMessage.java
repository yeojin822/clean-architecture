package com.example.points.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorMessage {
    NOT_FOUND_USER("회원을 찾을 수 없습니다."),
    POINT_POSITIVE("적립금은 0 보다 커야합니다."),
    POINT_OVER_TOTAL_POINTS("사용 가능한 적립금을 초과하였습니다.");

    private final String message;
}
