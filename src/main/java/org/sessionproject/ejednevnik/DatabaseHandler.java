package org.sessionproject.ejednevnik;

import java.sql.*;

public class DatabaseHandler {
    private Connection connection;

    public DatabaseHandler(String url, String user, String password) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/diary", "root", "152346789Ch$$");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для добавления задачи в базу данных
    public void addTask(Task task) {
        String query = "INSERT INTO tasks (title, description, date, category_id, priority, status, deadline) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Установка параметров запроса
            preparedStatement.setString(1, task.getTitle());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setDate(3, task.getDate());  // Используем setDate для поля типа java.sql.Date
            preparedStatement.setInt(4, task.getCategoryId());  // category_id
            preparedStatement.setString(5, task.getPriority());
            preparedStatement.setString(6, task.getStatus());
            preparedStatement.setTimestamp(7, task.getDeadline() != null ? new Timestamp(task.getDeadline().getTime()) : null);

            // Выполнение запроса
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для удаления задачи по названию
    public void deleteTask(String title) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM tasks WHERE title = ?");
            preparedStatement.setString(1, title);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Метод для получения всех задач из базы данных
    public ResultSet getTasks() {
        try {
            String query = "SELECT * FROM tasks";
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



}
