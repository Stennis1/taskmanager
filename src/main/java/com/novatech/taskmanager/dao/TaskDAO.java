package com.novatech.taskmanager.dao;

import com.novatech.taskmanager.model.Task;
import java.util.List;

public interface TaskDAO {
    void addTask(Task task);
    void updateTask(Task task);
    void deleteTask(int id);
    Task getTask(int id);
    List<Task> getAllTasks();
}
