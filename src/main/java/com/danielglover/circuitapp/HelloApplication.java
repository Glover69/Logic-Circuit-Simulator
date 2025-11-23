package com.danielglover.circuitapp;

import com.danielglover.circuitapp.logic.circuit.Gate;
import com.danielglover.circuitapp.logic.enums.GateType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Pane pane = new Pane();

        // Create a circle with the specified center coordinates and radius
//        double centerX = 100.0;
//        double centerY = 100.0;
//        double radius = 50.0;
//        Circle circle = new Circle(centerX, centerY, radius, Color.TRANSPARENT);
//        circle.setCenterX(centerX);
//        circle.setCenterY(centerY);
//        circle.setRadius(radius);
//
//        // Configure the circle to be an outline (transparent fill, black stroke)
//        circle.setStrokeWidth(2);
//        circle.setStroke(Color.RED);
//


        Canvas canvas = new Canvas(300, 400);
        GraphicsContext gc = canvas.getGraphicsContext2D();


        Gate gate = new Gate(GateType.INPUT, 20.0, 20.0, "P");
        gate.draw(gc);


        Scene scene = new Scene(pane, 1000, 1000);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        pane.getChildren().add(canvas);





        stage.show();
    }
}
