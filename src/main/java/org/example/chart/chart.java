package org.example.chart;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class chart extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 600, 400);

        // Центр координат (точка 0,0)
        double centerX = scene.getWidth() / 2;
        double centerY = scene.getHeight() / 2;

        // Ось X (горизонтальная)
        Line xAxis = new Line(50, centerY, scene.getWidth() - 50, centerY);
        xAxis.setStrokeWidth(2);

        // Стрелка оси X
        Line xArrow1 = new Line(scene.getWidth() - 50, centerY, scene.getWidth() - 60, centerY - 5);
        Line xArrow2 = new Line(scene.getWidth() - 50, centerY, scene.getWidth() - 60, centerY + 5);

        // Подпись оси X
        Text xLabel = new Text(scene.getWidth() - 40, centerY - 10, "X");

        // Ось Y (вертикальная)
        Line yAxis = new Line(centerX, scene.getHeight() - 50, centerX, 50);
        yAxis.setStrokeWidth(2);

        // Стрелка оси Y
        Line yArrow1 = new Line(centerX, 50, centerX - 5, 60);
        Line yArrow2 = new Line(centerX, 50, centerX + 5, 60);

        // Подпись оси Y
        Text yLabel = new Text(centerX + 10, 60, "Y");

        // Точка (0,0)
        Text originLabel = new Text(centerX + 5, centerY + 15, "0");

        // Добавление всех элементов на сцену
        root.getChildren().addAll(xAxis, xArrow1, xArrow2, xLabel, yAxis, yArrow1, yArrow2, yLabel, originLabel);

        primaryStage.setTitle("График");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);//Запуск программы
    }
}