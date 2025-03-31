package org.example.chart;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;

/**
 * Класс для отображения координатной системы с графиком функции.
 * <p>
 * Отображает декартову систему координат с осями X и Y, координатной сеткой,
 * графиком функции y = x²/2 - 2 с подписью уравнения на самом графике,
 * а также показывает координаты точки при наведении курсора мыши.
 */
public class chart extends Application {

    /** Масштаб координатной сетки (пикселей на единицу) */
    private static final double SCALE = 30;

    /** Размер стрелок на осях координат */
    private static final double ARROW_SIZE = 10;

    /** Текст для отображения текущих координат */
    private Text coordinatesLabel;

    /**
     * Основной метод запуска приложения.
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Точка входа для JavaFX приложений.
     * @param primaryStage главное окно приложения
     */
    @Override
    public void start(Stage primaryStage) {
        // Создание корневого контейнера и сцены
        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 600);

        // Вычисление центра координат (точка (0,0))
        double centerX = scene.getWidth() / 2;
        double centerY = scene.getHeight() / 2;

        // ========== ОСЬ X ==========
        // Основная линия оси X
        Line xAxis = new Line(50, centerY, scene.getWidth() - 50, centerY);
        xAxis.setStrokeWidth(2);

        // Стрелка оси X (состоит из двух линий)
        Line xArrow1 = new Line(scene.getWidth() - 50, centerY,
                scene.getWidth() - 50 - ARROW_SIZE, centerY - ARROW_SIZE/2);
        Line xArrow2 = new Line(scene.getWidth() - 50, centerY,
                scene.getWidth() - 50 - ARROW_SIZE, centerY + ARROW_SIZE/2);

        // Подпись оси X
        Text xLabel = new Text(scene.getWidth() - 40, centerY - 10, "X");

        // ========== ОСЬ Y ==========
        // Основная линия оси Y
        Line yAxis = new Line(centerX, scene.getHeight() - 50, centerX, 50);
        yAxis.setStrokeWidth(2);

        // Стрелка оси Y (состоит из двух линий)
        Line yArrow1 = new Line(centerX, 50, centerX - ARROW_SIZE/2, 50 + ARROW_SIZE);
        Line yArrow2 = new Line(centerX, 50, centerX + ARROW_SIZE/2, 50 + ARROW_SIZE);

        // Подпись оси Y
        Text yLabel = new Text(centerX + 10, 60, "Y");

        // Подпись начала координат (0,0)
        Text originLabel = new Text(centerX + 5, centerY + 15, "0");

        // Построение координатной сетки
        drawGrid(root, centerX, centerY, scene.getWidth(), scene.getHeight());

        // Создание графика функции с подписью уравнения
        Group functionGroup = drawFunctionWithLabel(root, centerX, centerY);

        // Создание текстового поля для отображения координат
        coordinatesLabel = new Text(20, 50, "");
        coordinatesLabel.setStyle("-fx-font-size: 14;");

        // Назначение обработчика движения мыши для графика
        functionGroup.setOnMouseMoved(this::handleMouseMoved);

        // Добавление всех элементов на сцену
        root.getChildren().addAll(xAxis, xArrow1, xArrow2, xLabel,
                yAxis, yArrow1, yArrow2, yLabel, originLabel,
                coordinatesLabel);

        // Настройка и отображение основного окна
        primaryStage.setTitle("График функции y = x²/2 - 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Рисует график функции с подписью уравнения.
     * @param root корневой контейнер для добавления элементов
     * @param centerX координата X центра системы
     * @param centerY координата Y центра системы
     * @return Group содержащая все элементы графика
     */
    private Group drawFunctionWithLabel(Pane root, double centerX, double centerY) {
        Group functionGroup = new Group();
        Circle prevPoint = null;

        // Построение графика по точкам
        for (double x = -10; x <= 10; x += 0.1) {
            // Вычисление значения функции y = x²/2 - 2
            double y = (x * x) / 2 - 2;

            // Преобразование математических координат в экранные
            double screenX = centerX + x * SCALE;
            double screenY = centerY - y * SCALE;

            // Создание точки графика
            Circle point = new Circle(screenX, screenY, 2, Color.RED);
            functionGroup.getChildren().add(point);

            // Соединение точек линиями (если это не первая точка)
            if (prevPoint != null) {
                Line line = new Line(prevPoint.getCenterX(), prevPoint.getCenterY(),
                        point.getCenterX(), point.getCenterY());
                line.setStroke(Color.BLUE);
                functionGroup.getChildren().add(line);
            }

            prevPoint = point;
        }

        // Добавление подписи уравнения на график
        Text equationLabel = new Text(centerX + 5 * SCALE, centerY - 5 * SCALE, "y = x²/2 - 2");
        equationLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-fill: blue;");
        functionGroup.getChildren().add(equationLabel);

        // Добавление группы графика на основную сцену
        root.getChildren().add(functionGroup);
        return functionGroup;
    }

    /**
     * Обработчик движения мыши для отображения координат.
     * @param event событие движения мыши
     */
    private void handleMouseMoved(MouseEvent event) {
        // Центр координат (предполагаемые размеры окна)
        double centerX = 400;
        double centerY = 300;

        // Преобразование экранных координат в математические
        double x = (event.getX() - centerX) / SCALE;
        double y = (centerY - event.getY()) / SCALE;

        // Вычисление ожидаемого значения Y для текущего X
        double expectedY = (x * x) / 2 - 2;

        // Если курсор близко к графику - показываем координаты
        if (Math.abs(y - expectedY) < 0.5) { // Порог близости 0.5 единицы
            coordinatesLabel.setText(String.format("x: %.2f, y: %.2f", x, expectedY));
        } else {
            coordinatesLabel.setText(""); // Иначе скрываем текст
        }
    }

    /**
     * Рисует координатную сетку с подписями.
     * @param root корневой контейнер
     * @param centerX центр по X
     * @param centerY центр по Y
     * @param width ширина области рисования
     * @param height высота области рисования
     */
    private void drawGrid(Pane root, double centerX, double centerY, double width, double height) {
        // ========== ВЕРТИКАЛЬНЫЕ ЛИНИИ ==========
        // Линии справа от центра
        for (double x = centerX; x < width - 50; x += SCALE) {
            Line line = new Line(x, 50, x, height - 50);
            line.setStroke(Color.LIGHTGRAY);
            root.getChildren().add(line);

            // Подписи положительных значений X
            if (x != centerX) {
                double value = (x - centerX) / SCALE;
                Text label = new Text(x - 5, centerY + 20, String.valueOf((int)value));
                root.getChildren().add(label);
            }
        }

        // Линии слева от центра
        for (double x = centerX; x > 50; x -= SCALE) {
            Line line = new Line(x, 50, x, height - 50);
            line.setStroke(Color.LIGHTGRAY);
            root.getChildren().add(line);

            // Подписи отрицательных значений X
            if (x != centerX) {
                double value = (x - centerX) / SCALE;
                Text label = new Text(x - 5, centerY + 20, String.valueOf((int)value));
                root.getChildren().add(label);
            }
        }

        // ========== ГОРИЗОНТАЛЬНЫЕ ЛИНИИ ==========
        // Линии выше центра
        for (double y = centerY; y > 50; y -= SCALE) {
            Line line = new Line(50, y, width - 50, y);
            line.setStroke(Color.LIGHTGRAY);
            root.getChildren().add(line);

            // Подписи положительных значений Y
            if (y != centerY) {
                double value = (centerY - y) / SCALE;
                Text label = new Text(centerX + 10, y + 5, String.valueOf((int)value));
                root.getChildren().add(label);
            }
        }

        // Линии ниже центра
        for (double y = centerY; y < height - 50; y += SCALE) {
            Line line = new Line(50, y, width - 50, y);
            line.setStroke(Color.LIGHTGRAY);
            root.getChildren().add(line);

            // Подписи отрицательных значений Y
            if (y != centerY) {
                double value = (centerY - y) / SCALE;
                Text label = new Text(centerX + 10, y + 5, String.valueOf((int)value));
                root.getChildren().add(label);
            }
        }
    }
}