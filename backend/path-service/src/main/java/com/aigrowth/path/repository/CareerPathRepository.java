package com.aigrowth.path.repository;

import com.aigrowth.path.entity.CareerPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CareerPathRepository extends JpaRepository<CareerPath, Long> {
    List<CareerPath> findByIsActiveTrue();
}
