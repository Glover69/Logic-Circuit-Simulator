package com.danielglover.circuitapp.ui;

import com.danielglover.circuitapp.logic.CircuitVariable;
import com.danielglover.circuitapp.logic.circuit.Circuit;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class SingleVariableBar extends VBox {

    public void setVariables(ObservableList<CircuitVariable> variables) {
        this.getChildren().clear();

        // Create vertical div to hold label and rows
        VBox col = new VBox();


        Label label = new Label("Input Variables ");
        label.setStyle("-fx-font-family: 'SF Pro Display'; -fx-text-fill: #374151; -fx-font-weight: bold; -fx-font-size: 16px;");

        for (CircuitVariable var : variables) {
            System.out.println(var.getName());

            HBox row = new HBox(10);
            row.setStyle("-fx-min-width: 100%;");

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            Label name = new Label(var.getName());

            Button resetButton = new Button(var.valueProperty().getValue().toString());
            resetButton.setStyle("-fx-background-color: #E5E7EB; -fx-background-radius: 8; -fx-border-radius: 8; -fx-font-weight: bold; -fx-font-size: 18px; -fx-max-height: 40px; -fx-font-family: 'SF Pro Display'; -fx-text-fill: #374151;");
//            resetButton.setOnAction(actionEvent -> {
//                try {
//                   this.circuit.toggleInput(var.getName());
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            });

//            CheckBox toggle = new CheckBox();
//            toggle.selectedProperty().bindBidirectional(var.valueProperty());

            row.getChildren().addAll(name, spacer, resetButton);
            col.getChildren().add(row);
        }


        this.getChildren().addAll(label, col);

    }
}

