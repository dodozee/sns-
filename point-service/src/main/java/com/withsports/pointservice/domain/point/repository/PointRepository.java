package com.withsports.pointservice.domain.point.repository;

import com.withsports.pointservice.domain.point.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

    Point findByUserId(Long userId);
}
