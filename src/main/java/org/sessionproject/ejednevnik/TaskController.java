package org.sessionproject.ejednevnik;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskController {

    @FXML private VBox taskVBox;
    @FXML private TableView<Task> taskTableView;
    @FXML private TableColumn<Task, String> titleColumn;
    @FXML private TableColumn<Task, String> descriptionColumn;
    @FXML private TableColumn<Task, Date> dateColumn;
    @FXML private TableColumn<Task, String> priorityColumn;
    @FXML private TableColumn<Task, String> statusColumn;
    @FXML private TableColumn<Task, String> categoryColumn;
    @FXML private TableColumn<Task, Date> creationDateColumn;
    @FXML private TableColumn<Task, Date> deadlineColumn;
    @FXML private TextField titleField;
    @FXML private TextArea descriptionArea;
    @FXML private DatePicker datePicker;
    @FXML private DatePicker deadlineDatePicker;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private ComboBox<String> priorityComboBox;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private Button addButton;
    @FXML private Button deleteButton;

    private DatabaseHandler databaseHandler;

    public TaskController() {
        // Инициализация объекта для работы с базой данных
        databaseHandler = new DatabaseHandler("jdbc:mysql://localhost:3306/diary", "root", "152346789Ch$$");
    }

    @FXML
    private void initialize() {
        // Заполняем ComboBox значениями
        loadCategories();

        // Устанавливаем столбцы таблицы
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        priorityColumn.setCellValueFactory(cellData -> cellData.getValue().priorityProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryNameProperty());
        creationDateColumn.setCellValueFactory(cellData -> cellData.getValue().creationDateProperty());
        deadlineColumn.setCellValueFactory(cellData -> cellData.getValue().deadlineProperty());

        // Обработка действий кнопок
        addButton.setOnAction(e -> addTask());
        deleteButton.setOnAction(e -> deleteTask());

        // Загрузка задач из базы данных при старте
        loadTasks();
    }

    private void loadCategories() {
        // Пример загрузки данных в ComboBox
        categoryComboBox.getItems().addAll("Работа", "Учеба", "Личные дела");
        priorityComboBox.getItems().addAll("Высокий", "Средний", "Низкий");
        statusComboBox.getItems().addAll("В процессе", "Выполнена", "Отложена");
    }

    private void loadTasks() {
        taskTableView.getItems().clear(); // Очищаем таблицу

        try {
            // Получаем данные из базы
            ResultSet resultSet = databaseHandler.getTasks();
            while (resultSet != null && resultSet.next()) {
                // Извлекаем все нужные данные из результата
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Date date = resultSet.getDate("date");
                String priority = resultSet.getString("priority");
                String status = resultSet.getString("status");
                String category = getCategoryName(resultSet.getInt("category_id")); // Извлекаем название категории
                Date deadline = resultSet.getDate("deadline");
                Date creationDate = resultSet.getDate("creation_date");

                // Создаем объект Task
                Task task = new Task(title, description, date, getCategoryId(category), priority, status, deadline, creationDate);

                // Добавляем задачу в таблицу
                taskTableView.getItems().add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getCategoryName(int categoryId) {
        switch (categoryId) {
            case 1: return "Работа";
            case 2: return "Учеба";
            case 3: return "Личные дела";
            default: return "Неизвестная категория";
        }
    }

    // Метод для получения ID категории
    private int getCategoryId(String categoryName) {
        switch (categoryName) {
            case "Работа":
                return 1; // ID для "Работа"
            case "Учеба":
                return 2; // ID для "Учеба"
            case "Личные дела":
                return 3; // ID для "Личные дела"
            default:
                return -1; // Если категория не найдена
        }
    }

    public void addTask() {
        // Получаем данные из интерфейса
        String title = titleField.getText();
        String description = descriptionArea.getText();
        Date date = null;

        // Проверяем, выбрана ли дата
        try {
            date = Date.valueOf(datePicker.getValue());
        } catch (NullPointerException e) {
            System.out.println("Дата не выбрана.");
        }

        // Получаем данные из ComboBox
        String category = categoryComboBox.getValue();
        String priority = priorityComboBox.getValue();
        String status = statusComboBox.getValue();

        Date deadline = null;
        try {
            deadline = Date.valueOf(deadlineDatePicker.getValue());
        } catch (NullPointerException e) {
            System.out.println("Дедлайн не выбран.");
        }

        // Проверяем, чтобы все поля были заполнены
        if (title.isEmpty() || date == null || category == null || priority == null || status == null) {
            System.out.println("Все поля обязательны.");
            return;
        }

        // Получаем categoryId с помощью метода
        int categoryId = getCategoryId(category);
        if (categoryId == -1) {
            System.out.println("Ошибка: категория не найдена.");
            return;
        }

        // Получаем текущую дату как дату создания задачи
        Date creationDate = new Date(System.currentTimeMillis());

        // Создаем объект Task с categoryId, creationDate и deadline
        Task task = new Task(title, description, date, categoryId, priority, status, deadline, creationDate);

        // Добавляем задачу в базу данных
        databaseHandler.addTask(task);

        // Обновляем таблицу задач
        loadTasks();

        // Очищаем поля ввода
        titleField.clear();
        descriptionArea.clear();
        datePicker.setValue(null);
        deadlineDatePicker.setValue(null);
        categoryComboBox.setValue(null);
        priorityComboBox.setValue(null);
        statusComboBox.setValue(null);
    }

    public void deleteTask() {
        Task selectedTask = taskTableView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            // Удаляем задачу из базы данных
            databaseHandler.deleteTask(selectedTask.getTitle());

            // Обновляем таблицу задач
            loadTasks();
        } else {
            System.out.println("Выберите задачу для удаления.");
        }
    }

    @FXML
    private void handleAddTask() {
        addTask();  // Вызов вашего метода addTask
    }

    @FXML
    private void handleDeleteTask() {
        deleteTask();  // Вызовите ваш метод deleteTask()
    }
}
