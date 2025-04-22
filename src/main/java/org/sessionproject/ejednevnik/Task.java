package org.sessionproject.ejednevnik;

import javafx.beans.property.*;
import java.sql.Date;

public class Task {
    private StringProperty title;
    private StringProperty description;
    private ObjectProperty<Date> date;  // Дата задачи
    private IntegerProperty categoryId;  // ID категории
    private StringProperty priority;  // Приоритет
    private StringProperty status;  // Статус задачи
    private ObjectProperty<Date> deadline;  // Дедлайн
    private ObjectProperty<Date> creationDate;  // Дата создания

    public Task(String title, String description, Date date, int categoryId, String priority, String status, Date deadline, Date creationDate) {
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.date = new SimpleObjectProperty<>(date);
        this.categoryId = new SimpleIntegerProperty(categoryId);
        this.priority = new SimpleStringProperty(priority);
        this.status = new SimpleStringProperty(status);
        this.deadline = new SimpleObjectProperty<>(deadline);
        this.creationDate = new SimpleObjectProperty<>(creationDate);
    }


    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public Date getDate() {
        return date.get();
    }

    public void setDate(Date date) {
        this.date.set(date);
    }

    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    public int getCategoryId() {
        return categoryId.get();
    }

    public void setCategoryId(int categoryId) {
        this.categoryId.set(categoryId);
    }

    public IntegerProperty categoryIdProperty() {
        return categoryId;
    }

    public String getPriority() {
        return priority.get();
    }

    public void setPriority(String priority) {
        this.priority.set(priority);
    }

    public StringProperty priorityProperty() {
        return priority;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public StringProperty statusProperty() {
        return status;
    }

    public Date getDeadline() {
        return deadline.get();
    }

    public void setDeadline(Date deadline) {
        this.deadline.set(deadline);
    }

    public ObjectProperty<Date> deadlineProperty() {
        return deadline;
    }

    public Date getCreationDate() {
        return creationDate.get();
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate.set(creationDate);
    }

    public ObjectProperty<Date> creationDateProperty() {
        return creationDate;
    }

    // Методы для получения строкового представления категории и статуса
    public String getCategoryName() {
        switch (categoryId.get()) {
            case 1: return "Работа";
            case 2: return "Учеба";
            case 3: return "Личные дела";
            default: return "Неизвестная категория";
        }
    }

    public StringProperty categoryNameProperty() {
        return new SimpleStringProperty(getCategoryName());
    }

    public String getPriorityDisplay() {
        switch (priority.get()) {
            case "Высокий": return "Высокий";
            case "Средний": return "Средний";
            case "Низкий": return "Низкий";
            default: return "Неизвестный приоритет";
        }
    }

    public String getStatusDisplay() {
        switch (status.get()) {
            case "В процессе": return "В процессе";
            case "Выполнена": return "Выполнена";
            case "Отложена": return "Отложена";
            default: return "Неизвестный статус";
        }
    }

    // Метод для получения строки, которая будет отображаться в таблице
    public String getFullDescription() {
        return String.format("Задача: %s\nОписание: %s\nДата: %s\nПриоритет: %s\nСтатус: %s",
                title.get(), description.get(), date.get().toString(), getPriorityDisplay(), getStatusDisplay());
    }

    // Дополнительные методы для более удобного отображения статуса и приоритета в интерфейсе
    public StringProperty priorityDisplayProperty() {
        return new SimpleStringProperty(getPriorityDisplay());
    }

    public StringProperty statusDisplayProperty() {
        return new SimpleStringProperty(getStatusDisplay());
    }
}
