package com.aigrowth.task.repository;

import com.aigrowth.task.entity.UserTaskProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserTaskProgressRepository extends JpaRepository<UserTaskProgress, Long> {
    List<UserTaskProgress> findByUserId(Long userId);
    Optional<UserTaskProgress> findByUserIdAndTaskId(Long userId, Long taskId);
}
