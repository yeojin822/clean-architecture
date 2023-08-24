package com.example.points.usecases.business;

import com.example.points.usecases.dto.CreatePointHistoryEvent;
import com.example.points.usecases.port.out.PointHistoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
@Slf4j
public class PointHistoryEventHandler {
    private final PointHistoryPort pointHistoryPort;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(
            classes = CreatePointHistoryEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(final CreatePointHistoryEvent event) {
        Assert.notNull(event.getPointHistoryDto(), "pointHistoryDto는 null이 아니어야 합니다");

        pointHistoryPort.save(event.getPointHistoryDto());
    }
}