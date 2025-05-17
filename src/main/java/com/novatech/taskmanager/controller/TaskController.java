package com.novatech.taskmanager.controller;

import com.novatech.taskmanager.model.Task;
import com.novatech.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // List all tasks
    @GetMapping
    public String listTasks(Model model, @RequestParam(required = false) String status) {
        if (status != null && !status.isEmpty()) {
            model.addAttribute("tasks", taskService.getTasksByStatus(status));
        } else {
            model.addAttribute("tasks", taskService.getAllTasks());
        }
        return "task-list";
    }

    // Show form to add new task
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("task", new Task());
        return "task-form";
    }

    // Save new or updated task
    @PostMapping("/save")
    public String saveTask(@ModelAttribute("task") Task task) {
        taskService.saveTask(task);
        return "redirect:/tasks";
    }

    // Show form to update task
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Task> task = taskService.getTaskById(id);
        if (task.isPresent()) {
            model.addAttribute("task", task.get());
            return "task-form";
        }
        return "redirect:/tasks";
    }

    // Delete task
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }
}
