package com.danielglover.circuitapp.ui;

import com.danielglover.circuitapp.logic.circuit.Circuit;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ControlPanel extends VBox {
    private Circuit circuit;
    private Map<String, CheckBox> inputCheckBoxes;
    private Label outputLabel;
    private CircuitCanvas canvas;
    private HBox inputBox;
    private VBox outputContainer;

    public ControlPanel(){
        super(10);
        this.setStyle("-fx-spacing: 10px; -fx-padding: 10px 10px 10px 10px;");


        this.inputCheckBoxes = new LinkedHashMap<>();

        createUI();
    }


    private void createUI() {
        VBox vbox = new VBox();
        vbox.setStyle("-fx-min-width: 100%; -fx-alignment: center; -fx-padding: 0px 0px 20px 0px");

        VBox mainContainer = new VBox();
        mainContainer.setStyle("-fx-spacing: 12px; -fx-max-width: 750px; -fx-min-height: 50px; -fx-background-radius: 12; -fx-border-radius: 12; -fx-background-color: white; -fx-alignment: start; -fx-padding: 16px 16px 22px 16px;");

        // Top bar containing Header and reset button
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        inputBox = new HBox(10);
        inputBox.setStyle("-fx-alignment: center-left;");


        HBox topBar = new HBox();
        topBar.setStyle("-fx-alignment: center;");

        Label title = new Label("Test Circuit");
        title.setStyle("-fx-font-family: 'SF Pro Display'; -fx-font-size: 18px; -fx-font-weight: bold;");

        Button resetButton = new Button("Reset");
        resetButton.setStyle("-fx-background-color: #E5E7EB; -fx-background-radius: 8; -fx-border-radius: 8; -fx-font-weight: bold; -fx-font-size: 18px; -fx-max-height: 40px; -fx-font-family: 'SF Pro Display'; -fx-text-fill: #374151;");
        resetButton.setOnAction(actionEvent -> {
            if (circuit != null) {
                try {
                    circuit.resetCircuit();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            // Refresh ino
            updateFromCircuit();
            updateOutput();

            if (canvas != null) {
                canvas.redraw();
            }
        });

        // Output Box

        Label outputTitle = new Label("Output ");
        outputTitle.setStyle("-fx-font-family: 'SF Pro Display'; -fx-text-fill: #374151; -fx-font-weight: bold; -fx-font-size: 16px;");

        outputContainer = new VBox();
        outputContainer.setStyle("-fx-padding: 12px; -fx-background-radius: 12px; -fx-border-radius: 12px; -fx-border-color: #E5E7EB; -fx-border-width: 1px; -fx-min-height: 50px; -fx-alignment: center;");


        topBar.getChildren().addAll(title, spacer);


        mainContainer.getChildren().addAll(topBar, outputTitle, outputContainer, inputBox);
        vbox.getChildren().addAll(mainContainer);


        this.getChildren().addAll(vbox);

    }

    public void setCircuit(Circuit circuit) {
        this.circuit = circuit;
//        this.circuit.refreshVariablesFromInputGates();
//        variablePanel.setVariables(circuit.getVariables());
        updateFromCircuit();
    }

    public void updateFromCircuit() {
        if (circuit == null){
            return;
        }

        inputCheckBoxes.clear();

//        HBox inputBox = (HBox) this.getChildren().get(1);
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

    public void updateOutput() {
        outputLabel = new Label();

        if (circuit == null){
            outputLabel.setText("--");
            return;
        }

        try {
            Boolean result = circuit.evaluate();
            outputLabel.setText(result ? "TRUE" : "FALSE");
            outputLabel.setStyle("-fx-font-family: 'SF Pro Display'; -fx-font-size: 18px; -fx-font-weight: bold;");

            outputContainer.getChildren().setAll(outputLabel);
            outputContainer.setBackground(
                    new Background(
                     new BackgroundFill(
                            result ? Color.rgb(220, 252, 230) : Color.rgb(254, 226, 225),
                            new CornerRadii(12),
                            Insets.EMPTY
                    )));

            Color borderColor = result ? Color.GREEN : Color.RED;
            outputContainer.setBorder(
                    new Border(new BorderStroke(
                            borderColor, BorderStrokeStyle.SOLID, new CornerRadii(12), new BorderWidths(2)
                    ))
            );


        }catch (Exception e){
            outputLabel.setText("ERROR");
            outputLabel.setTextFill(Color.ORANGE);
        }
    }

    private void setCanvas(CircuitCanvas canvas){
        this.canvas = canvas;
    }


}
