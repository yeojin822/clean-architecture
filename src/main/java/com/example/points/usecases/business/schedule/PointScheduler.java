package com.example.points.usecases.business.schedule;

import com.example.points.usecases.port.out.PointPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class PointScheduler {
    private final PointPort pointPort;

    @Scheduled(cron = "0 0 4 * * *", zone = "Asia/Seoul")
    public void batchExpiredReserve() {
        log.info("------------- 적립금 만료 처리 시작 -------------");
        LocalDateTime currentDateTime = LocalDateTime.now();
        pointPort.findAllByAutoExpireAt(currentDateTime).delete();
    }
}
