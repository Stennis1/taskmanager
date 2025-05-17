package com.novatech.taskmanager.service;

import com.novatech.taskmanager.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> getAllTasks();
    Optional<Task> getTaskById(Long id);
    Task saveTask(Task task);
    void deleteTask(Long id);
    List<Task> getTasksByStatus(String status);
}
