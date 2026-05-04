package com.aigrowth.task.service;

import com.aigrowth.task.dto.TaskDTO;
import com.aigrowth.task.entity.Task;
import com.aigrowth.task.repository.TaskRepository;
import com.aigrowth.common.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskDTO> getAllActiveTasks() {
        return taskRepository.findByIsActiveTrue()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "Task not found"));
        return toDTO(task);
    }

    public TaskDTO createTask(TaskDTO dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCategory(dto.getCategory());
        task.setXp(dto.getXp() != null ? dto.getXp() : 0);
        task.setDifficulty(dto.getDifficulty() != null ? dto.getDifficulty() : "medium");
        task.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        task = taskRepository.save(task);
        return toDTO(task);
    }

    public TaskDTO updateTask(Long id, TaskDTO dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "Task not found"));
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCategory(dto.getCategory());
        if (dto.getXp() != null) task.setXp(dto.getXp());
        if (dto.getDifficulty() != null) task.setDifficulty(dto.getDifficulty());
        if (dto.getIsActive() != null) task.setIsActive(dto.getIsActive());
        task = taskRepository.save(task);
        return toDTO(task);
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "Task not found"));
        task.setIsActive(false);
        taskRepository.save(task);
    }

    private TaskDTO toDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCategory(),
                task.getXp(),
                task.getDifficulty(),
                task.getIsActive()
        );
    }
}
