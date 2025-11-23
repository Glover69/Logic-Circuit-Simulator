package com.danielglover.circuitapp.logic.circuit;

import com.danielglover.circuitapp.logic.enums.GateType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static java.lang.classfile.Opcode.NEW;

public class Gate {
    private GateType type; // Type of gate using the GateType enum
    private Double xCoordinate;
    private Double yCoordinate; // Both x and y tell us where to draw the gate in the GUI
    private Boolean currentValue; // The current output value of the gate
    private String gateName; // Name/Label for the gate
    private ArrayList<Gate> inputs;

    // Constants for drawing gates
    private static int GATE_WIDTH = 80;
    private static int GATE_HEIGHT = 60;



    public Gate(GateType type, Double xCoordinate, Double yCoordinate){
        setType(type);
//        setPosition(xCoordinate, yCoordinate);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.inputs = new ArrayList<>();
        this.currentValue = true;
        this.gateName = null;
    }

    public Gate(GateType type, Double xCoordinate, Double yCoordinate, String gateName){
        this(type, xCoordinate, yCoordinate);
        this.gateName = gateName;
    }

    public GateType getType() {
        return type;
    }

    public void setType(GateType type) {
        this.type = type;
    }

    // Helper methods


    // Calculate the output of a gate based on its type and input values
    public Boolean evaluate() throws Exception{

        // First case: INPUTS
        if (this.type == GateType.INPUT){
            if (this.currentValue == null){
                throw new Exception("INPUT gate " + this.gateName + " has no value set");
            }

            return this.currentValue;
        }



        // Second case: AND gates
        if (this.type == GateType.AND){
            if (inputs.size() != 2){
                throw new Exception("AND gate must have exactly 2 inputs");
            }

            boolean leftVal = inputs.get(0).evaluate();
            boolean rightVal = inputs.get(1).evaluate();

            this.currentValue = leftVal && rightVal;
            return currentValue;
        }

        // Third case: OR gates
        if (this.type == GateType.OR){
            if (inputs.size() != 2){
                throw new Exception("AND gate must have exactly 2 inputs");
            }

            boolean leftVal = inputs.get(0).evaluate();
            boolean rightVal = inputs.get(1).evaluate();

            this.currentValue = leftVal || rightVal;
            return currentValue;
        }

        // Lastly: NOT gates
        
        if (this.type == GateType.NOT){
            if (inputs.size() != 1){
                throw new Exception("NOT gates can have just one input");
            }

            this.currentValue = !inputs.getFirst().evaluate();
            return currentValue;
        }

        // Error when type is unknows
        throw new Exception("Unknown gate type: " + type);
    }


    // Draw the different kinds of gates (there are methods for each gate)
    public void draw(GraphicsContext context){
        Color fillColor;

        if (this.currentValue){
            fillColor = Color.GREEN;
        }else if (!this.currentValue){
            fillColor = Color.RED;
        }else{
            fillColor = Color.WHITE;
        }

        // Draw based on type of gate
        switch (type){
            case AND -> {
                drawANDGate(context, fillColor);
            }
            case OR -> {
                drawORGate(context, fillColor);
            }
            case NOT -> {
                drawNOTGate(context, fillColor);
            }
            case INPUT -> {
                drawInputGate(context, fillColor);
            }
            case OUTPUT ->{
                drawOutputGate(context, fillColor);
            }
        }
    }




    // Methods for drawing all gates in their specific shapes
    private void drawInputGate(GraphicsContext context, Color fillColor) {
        // Draw input as a circle
        int radius = 20;
        Double x = xCoordinate;
        Double y = yCoordinate;

        // Draw the circle now
        context.setFill(fillColor);
        context.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        // Draw outline on circle
        context.setStroke(Color.BLACK);
        context.setLineWidth(2);
        context.strokeOval(x - radius, y - radius, radius * 2, radius * 2);

        // Draw label
        context.setFill(Color.BLACK);
        context.setFont(Font.font("Consolas", FontWeight.BOLD, 12));
        context.fillText(gateName, x - 3, y + 5);

    }

    private void drawANDGate(GraphicsContext context, Color fillColor) {
    }

    private void drawNOTGate(GraphicsContext context, Color fillColor) {
    }

    private void drawORGate(GraphicsContext context, Color fillColor) {
    }

    private void drawOutputGate(GraphicsContext context, Color fillColor) {
    }




    public void addInput(Gate input) throws Exception{
        /*
         * Add a gate as an input to this gate
         * Used to build connections between gates
         */

        if (this.type == GateType.INPUT){
            throw new Exception("INPUT gates cannot have inputs");
        }

        if (this.type == GateType.NOT && !inputs.isEmpty()){
            throw new Exception("NOT gates can have only one input");
        }

        if ((this.type == GateType.AND || this.type == GateType.OR) && inputs.size() >= 2){
            throw new Exception("Binary gates can have just two inputs for now");
        }

        inputs.add(input);
    }

    public void setPosition(Double x, Double y){

    }

    // Sets the value for the INPUTs. This is how users toggle them
    public void setValue(Boolean boolValue) throws Exception{
        if (this.type != GateType.INPUT){
            throw new Exception("Values can only be set on inputs");
        }

        this.currentValue = boolValue;

    }

//    public boolean containsPoint(Double x, Double y){
//        return null;
//    }


}
