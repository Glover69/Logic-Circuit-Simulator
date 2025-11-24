package com.danielglover.circuitapp;

import com.danielglover.circuitapp.logic.circuit.Circuit;
import com.danielglover.circuitapp.logic.circuit.Gate;
import com.danielglover.circuitapp.logic.circuit.Wire;
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
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Pane pane = new Pane();

        Canvas canvas = new Canvas(1200, 1200);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Scene scene = new Scene(pane, 1000, 1000);
        pane.getChildren().add(canvas);
        stage.setTitle("Logic Circuit Simulator");
        stage.setScene(scene);



        stage.show();

        Gate gate = new Gate(GateType.INPUT, 50, 135, "P");
        Gate gate2 = new Gate(GateType.INPUT, 50, 165, "Q");
        Gate andGate = new Gate(GateType.AND, 150, 120);

        andGate.addInput(gate);
        andGate.addInput(gate2);

        Circuit circuit = new Circuit();
        circuit.addGate(gate);
        circuit.addGate(gate2);
        circuit.addGate(andGate);
        circuit.setOutputGate(andGate);

        Wire wire1 = new Wire(gate, andGate);
        Wire wire2 = new Wire(gate2, andGate);
        circuit.addWire(wire1);
        circuit.addWire(wire2);

        gate.setValue(true);
        gate2.setValue(true);

        System.out.println("Circuit Output: " + circuit.evaluate());

        // Print all start and end points for gates
        System.out.println("Gate 1 Output Point: " + gate.getOutputPoint().x + ", " + gate.getOutputPoint().y);
        System.out.println("Gate 2 Output Point: " + gate2.getOutputPoint().x + ", " + gate2.getOutputPoint().y);
        System.out.println("AND Gate Input Points: ");
        for (int i = 0; i < andGate.getInputs().size(); i++) {
            System.out.println("Input " + i + ": " + andGate.getInputPoint(i).x + ", " + andGate.getInputPoint(i).y);
        }

        circuit.draw(gc);

//        circuit.reset();
        circuit.toggleInput("P");
        circuit.toggleInput("Q");
        circuit.evaluate();
//        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        circuit.draw(gc);











    }
}
