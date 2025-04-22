package org.sessionproject.ejednevnik;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Загружаем FXML файл с помощью FXMLLoader
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/sessionproject/ejednevnik/hello-view.fxml"));

            // Загружаем корневой элемент из FXML (HBox)
            HBox root = loader.load();

            // Создаем сцену с загруженным корневым элементом
            Scene scene = new Scene(root, 800, 600);

            scene.getStylesheets().add(getClass().getResource("/org/sessionproject/ejednevnik/styles.css").toExternalForm());
            
            // Настроить окно
            primaryStage.setTitle("Ежедневник");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Логирование ошибки
            System.err.println("Ошибка загрузки FXML или CSS файлов.");
            System.exit(1); // Завершаем приложение в случае ошибки
        }
    }

    public static void main(String[] args) {
        launch(args); // Запуск JavaFX приложения
    }
}