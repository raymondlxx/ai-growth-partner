package com.aigrowth.path.repository;

import com.aigrowth.path.entity.UserPathProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserPathProgressRepository extends JpaRepository<UserPathProgress, Long> {
    Optional<UserPathProgress> findByUserIdAndPathId(Long userId, Long pathId);
}
