package com.danielglover.circuitapp;

import com.danielglover.circuitapp.logic.Parser;
import com.danielglover.circuitapp.logic.Tokenizer;
import com.danielglover.circuitapp.logic.circuit.Circuit;
import com.danielglover.circuitapp.logic.circuit.CircuitBuilder;
import com.danielglover.circuitapp.logic.circuit.Gate;
import com.danielglover.circuitapp.logic.circuit.Wire;
import com.danielglover.circuitapp.logic.enums.GateType;
import com.danielglover.circuitapp.logic.nodes.ExprNode;
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


        String expression = "(A && B) || C";
        Parser parser = new Parser();
        Tokenizer tokenizer = new Tokenizer();
        ExprNode tree = parser.parse(tokenizer.tokenize(expression));

        CircuitBuilder builder = new CircuitBuilder();
        Circuit circuit = builder.buildCircuit(tree);


        System.out.println(circuit.getGates().size());
        System.out.println(circuit.getWires().size());

        Gate gateA = circuit.getInputGates().get("A");
        Gate gateB = circuit.getInputGates().get("B");
        Gate gateC = circuit.getInputGates().get("C");

         System.out.println(gateA.getxCoordinate());
         System.out.println(gateB.getxCoordinate());
         System.out.println(gateC.getxCoordinate());

        circuit.setInputValue("A", true);
        circuit.setInputValue("B", false);
        circuit.setInputValue("C", true);

        System.out.println(circuit.evaluate());

        circuit.draw(gc);

        System.out.println("Tree: " + tree.toString());
        System.out.println("Variables: " + tree.getAllVariables());

        System.out.println("Total gates: " + circuit.getGates().size());
        for (Gate gate : circuit.getGates()) {
            System.out.println("Gate: type=" + gate.getType() +
                    ", pos=(" + gate.getxCoordinate() + "," + gate.getyCoordinate() + ")" +
                    ", label=" + gate.getGateName());
        }

        System.out.println("\nTotal wires: " + circuit.getWires().size());










    }
}
