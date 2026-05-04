package com.aigrowth.task.repository;

import com.aigrowth.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByIsActiveTrue();
    List<Task> findByCategoryAndIsActiveTrue(String category);
}
