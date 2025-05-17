package com.novatech.taskmanager.dao.impl;

import com.novatech.taskmanager.dao.TaskDAO;
import com.novatech.taskmanager.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAOImpl implements TaskDAO {

    private final String URL = "jdbc:postgresql://localhost:5432/task_db";
    private final String USERNAME = "stennis";
    private final String PASSWORD = "stennis12345";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    @Override
    public void addTask(Task task) {
        String sql = "INSERT INTO tasks (title, description, due_date, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setDate(3, Date.valueOf(task.getDueDate()));
            stmt.setString(4, task.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTask(Task task) {
        String sql = "UPDATE tasks SET title=?, description=?, due_date=?, status=? WHERE id=?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setDate(3, Date.valueOf(task.getDueDate()));
            stmt.setString(4, task.getStatus());
            stmt.setLong(5, task.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTask(int id) {
        String sql = "DELETE FROM tasks WHERE id=?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Task getTask(int id) {
        String sql = "SELECT * FROM tasks WHERE id=?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractTask(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tasks.add(extractTask(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    private Task extractTask(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setId(rs.getLong("id"));
        task.setTitle(rs.getString("title"));
        task.setDescription(rs.getString("description"));
        task.setDueDate(rs.getDate("due_date").toLocalDate());
        task.setStatus(rs.getString("status"));
        return task;
    }
}
