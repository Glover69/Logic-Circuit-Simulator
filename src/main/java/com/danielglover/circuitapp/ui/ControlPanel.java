package com.danielglover.circuitapp.ui;

import com.danielglover.circuitapp.logic.circuit.Circuit;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
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

//    private void createUI() {
//        Label inputLabel = new Label("Inputs:");
//        inputLabel.setStyle("-fx-font-weight: bold");
//
//        HBox inputBox = new HBox(15);
//        this.getChildren().addAll(inputLabel, inputBox);
//
//        // Output section
//        Separator separator = new Separator();
//
//        HBox outputBox = new HBox(10);
//        Label outputTitleLabel = new Label("Output:");
//        outputTitleLabel.setStyle("-fx-font-weight: bold");
//        outputLabel = new Label("--");
//        outputLabel.setStyle("-fx-font-size: 18; -fx-text-fill: blue");
//
//        outputBox.getChildren().addAll(outputTitleLabel, outputLabel);
//
//        // Reset button
//        Button resetButton = new Button("Reset All");
//        resetButton.setOnAction(event -> {
//            try {
//                handleReset();
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });
//
//        this.getChildren().addAll(separator, outputBox, resetButton);
//    }

    private void createUI(){
        VBox vbox = new VBox();
        vbox.setStyle("-fx-min-width: 100%; -fx-alignment: center; -fx-padding: 0px 0px 20px 0px");

        VBox mainContainer = new VBox();
        mainContainer.setStyle("-fx-spacing: 12px; -fx-max-width: 750px; -fx-min-height: 50px; -fx-background-radius: 12; -fx-border-radius: 12; -fx-background-color: white; -fx-alignment: center; -fx-padding: 16px 16px 22px 16px;");

        // Top bar containing Header and reset button
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox topBar = new HBox();
        topBar.setStyle("-fx-alignment: center;");

        Label title = new Label("Test Circuit");
        title.setStyle("-fx-font-family: 'SF Pro Display'; -fx-font-size: 18px; -fx-font-weight: bold;");

        Button resetButton = new Button("Reset");
        resetButton.setStyle("-fx-background-color: #E5E7EB; -fx-background-radius: 8; -fx-border-radius: 8; -fx-font-weight: bold; -fx-font-size: 18px; -fx-max-height: 40px; -fx-font-family: 'SF Pro Display'; -fx-text-fill: #374151;");
        resetButton.setOnAction(actionEvent -> {
            try {
                handleReset();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        topBar.getChildren().addAll(title, spacer, resetButton);


        mainContainer.getChildren().addAll(topBar);
        vbox.getChildren().addAll(mainContainer);


        this.getChildren().addAll(vbox);

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
