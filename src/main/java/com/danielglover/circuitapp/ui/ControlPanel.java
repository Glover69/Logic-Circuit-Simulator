package com.danielglover.circuitapp.ui;

import com.danielglover.circuitapp.logic.circuit.Circuit;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.*;

public class ControlPanel extends VBox {
    private Circuit circuit;
    private Map<String, CheckBox> inputCheckBoxes;
    private Label outputLabel;
    private CircuitCanvas canvas;

    public ControlPanel(){
        super(10);
        this.setStyle("-fx-spacing: 10px; -fx-padding: 10px 10px 10px 10px;");

        this.inputCheckBoxes = new LinkedHashMap<>();

        createUI();
    }

    private void createUI() {
        Label inputLabel = new Label("Inputs:");
        inputLabel.setStyle("-fx-font-weight: bold");

        HBox inputBox = new HBox(15);
        this.getChildren().addAll(inputLabel, inputBox);

        // Output section
        Separator separator = new Separator();

        HBox outputBox = new HBox(10);
        Label outputTitleLabel = new Label("Output:");
        outputTitleLabel.setStyle("-fx-font-weight: bold");
        outputLabel = new Label("--");
        outputLabel.setStyle("-fx-font-size: 18; -fx-text-fill: blue");

        outputBox.getChildren().addAll(outputTitleLabel, outputLabel);

        // Reset button
        Button resetButton = new Button("Reset All");
        resetButton.setOnAction(event -> {
            try {
                handleReset();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        this.getChildren().addAll(separator, outputBox, resetButton);
    }

    public void setCircuit(Circuit circuit) {
        this.circuit = circuit;
        updateFromCircuit();
    }

    public void updateFromCircuit() {
        if (circuit == null){
            return;
        }

        inputCheckBoxes.clear();

        HBox inputBox = (HBox) this.getChildren().get(1);
        inputBox.getChildren().clear();

         Set<String> variables = circuit.getAllVariables();
         List<String> sortedVariables = new ArrayList<>(variables);
         Collections.sort(sortedVariables);

         for (String var: sortedVariables){
             CheckBox cb = new CheckBox();
             cb.setSelected(false);
             cb.setOnAction(event -> {
                 try {
                     handleInputChange(var, cb.isSelected());
                 } catch (Exception e) {
                     throw new RuntimeException(e);
                 }
             });
             inputCheckBoxes.put(var, cb);
         }

         updateOutput();
    }

    private void handleInputChange(String var, Boolean value) throws Exception {
        if (circuit != null){
            circuit.setInputValue(var, value);
            updateOutput();

            if (canvas != null){
                canvas.redraw();
            }
        }

    }

    private void handleReset() throws Exception {
        if (circuit != null) {
            circuit.reset();
        }

        // Uncheck all checkboxes
        for (CheckBox checkBox: inputCheckBoxes.values()){
            checkBox.setSelected(false);
        }

        updateOutput();

        if (canvas != null){
            canvas.redraw();
        }
    }

    private void updateOutput() {
        if (circuit == null){
            outputLabel.setText("--");
            return;
        }

        try {
            Boolean result = circuit.evaluate();
            outputLabel.setText(result ? "TRUE" : "FALSE");
            outputLabel.setTextFill(result ? Color.GREEN : Color.RED);

        }catch (Exception e){
            outputLabel.setText("ERROR");
            outputLabel.setTextFill(Color.ORANGE);
        }
    }

    private void setCanvas(CircuitCanvas canvas){
        this.canvas = canvas;
    }


}
