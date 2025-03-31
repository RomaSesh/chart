package org.example.chart;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class chart extends Application {

    private static final double SCALE = 30; // Масштаб сетки
    private static final double ARROW_SIZE = 10; // Размер стрелок

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 600);

        // Центр координат
        double centerX = scene.getWidth() / 2;
        double centerY = scene.getHeight() / 2;

        // Ось X (горизонтальная)
        Line xAxis = new Line(50, centerY, scene.getWidth() - 50, centerY);
        xAxis.setStrokeWidth(2);

        // Стрелка оси X
        Line xArrow1 = new Line(scene.getWidth() - 50, centerY,
                scene.getWidth() - 50 - ARROW_SIZE, centerY - ARROW_SIZE/2);
        Line xArrow2 = new Line(scene.getWidth() - 50, centerY,
                scene.getWidth() - 50 - ARROW_SIZE, centerY + ARROW_SIZE/2);

        // Подпись оси X
        Text xLabel = new Text(scene.getWidth() - 40, centerY - 10, "X");

        // Ось Y (вертикальная)
        Line yAxis = new Line(centerX, scene.getHeight() - 50, centerX, 50);
        yAxis.setStrokeWidth(2);

        // Стрелка оси Y
        Line yArrow1 = new Line(centerX, 50, centerX - ARROW_SIZE/2, 50 + ARROW_SIZE);
        Line yArrow2 = new Line(centerX, 50, centerX + ARROW_SIZE/2, 50 + ARROW_SIZE);

        // Подпись оси Y
        Text yLabel = new Text(centerX + 10, 60, "Y");

        // Точка (0,0)
        Text originLabel = new Text(centerX + 5, centerY + 15, "0");

        // Сетка и подписи чисел
        drawGrid(root, centerX, centerY, scene.getWidth(), scene.getHeight());

        // График функции y = x²/2 - 2
        drawFunction(root, centerX, centerY);

        // Добавление всех элементов на сцену
        root.getChildren().addAll(xAxis, xArrow1, xArrow2, xLabel,
                yAxis, yArrow1, yArrow2, yLabel, originLabel);

        primaryStage.setTitle("Графиком функции y = x²/2 - 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawGrid(Pane root, double centerX, double centerY, double width, double height) {
        // Вертикальные линии сетки
        for (double x = centerX; x < width - 50; x += SCALE) {
            Line line = new Line(x, 50, x, height - 50);
            line.setStroke(Color.LIGHTGRAY);
            root.getChildren().add(line);

            // Подписи чисел на оси X (положительные)
            if (x != centerX) {
                double value = (x - centerX) / SCALE;
                Text label = new Text(x - 5, centerY + 20, String.valueOf((int)value));
                root.getChildren().add(label);
            }
        }

        for (double x = centerX; x > 50; x -= SCALE) {
            Line line = new Line(x, 50, x, height - 50);
            line.setStroke(Color.LIGHTGRAY);
            root.getChildren().add(line);

            // Подписи чисел на оси X (отрицательные)
            if (x != centerX) {
                double value = (x - centerX) / SCALE;
                Text label = new Text(x - 5, centerY + 20, String.valueOf((int)value));
                root.getChildren().add(label);
            }
        }

        // Горизонтальные линии сетки
        for (double y = centerY; y > 50; y -= SCALE) {
            Line line = new Line(50, y, width - 50, y);
            line.setStroke(Color.LIGHTGRAY);
            root.getChildren().add(line);

            // Подписи чисел на оси Y (положительные)
            if (y != centerY) {
                double value = (centerY - y) / SCALE;
                Text label = new Text(centerX + 10, y + 5, String.valueOf((int)value));
                root.getChildren().add(label);
            }
        }

        for (double y = centerY; y < height - 50; y += SCALE) {
            Line line = new Line(50, y, width - 50, y);
            line.setStroke(Color.LIGHTGRAY);
            root.getChildren().add(line);

            // Подписи чисел на оси Y (отрицательные)
            if (y != centerY) {
                double value = (centerY - y) / SCALE;
                Text label = new Text(centerX + 10, y + 5, String.valueOf((int)value));
                root.getChildren().add(label);
            }
        }
    }

    private void drawFunction(Pane root, double centerX, double centerY) {
        Circle prevPoint = null;

        for (double x = -10; x <= 10; x += 0.1) {
            double y = (x * x) / 2 - 2; // y = x²/2 - 2

            double screenX = centerX + x * SCALE;
            double screenY = centerY - y * SCALE;

            Circle point = new Circle(screenX, screenY, 2, Color.RED);
            root.getChildren().add(point);

            if (prevPoint != null) {
                Line line = new Line(prevPoint.getCenterX(), prevPoint.getCenterY(),
                        point.getCenterX(), point.getCenterY());
                line.setStroke(Color.BLUE);
                root.getChildren().add(line);
            }

            prevPoint = point;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}