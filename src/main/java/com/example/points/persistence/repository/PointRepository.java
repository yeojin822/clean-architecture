package com.example.points.persistence.repository;

import com.example.points.persistence.entity.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.List;

public interface PointRepository extends JpaRepository<PointEntity, Long> {
    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT p FROM point p WHERE p.userId = ?1 and p.expiredAt is null order by p.createdAt")
    List<PointEntity> findAllByUserId(Long userId);

    @Query("SELECT p FROM point p WHERE p.expiredAt is null and p.autoExpireAt < ?1")
    List<PointEntity> findAllByAutoExpireAt(LocalDateTime currentDateTime);

}
