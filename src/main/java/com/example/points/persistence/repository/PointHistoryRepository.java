package com.example.points.persistence.repository;

import com.example.points.persistence.entity.PointHistoryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PointHistoryRepository extends JpaRepository<PointHistoryEntity, Long> {

    @Query("SELECT h FROM point_history h WHERE h.userId = ?1")
    List<PointHistoryEntity> findAllByUserId(Long userId, Pageable pageable);
}
