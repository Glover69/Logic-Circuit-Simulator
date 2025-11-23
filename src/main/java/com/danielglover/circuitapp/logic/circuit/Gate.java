package com.danielglover.circuitapp.logic.circuit;

import com.danielglover.circuitapp.logic.enums.GateType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.awt.*;
import java.util.ArrayList;

public class Gate {
    private GateType type; // Type of gate using the GateType enum
    private int xCoordinate;
    private int yCoordinate; // Both x and y tell us where to draw the gate in the GUI
    private Boolean currentValue; // The current output value of the gate
    private String gateName; // Name/Label for the gate
    private ArrayList<Gate> inputs;

    // Constants for drawing gates
    private static final int GATE_WIDTH = 85;
    private static final int GATE_HEIGHT = 65;



    public Gate(GateType type, int xCoordinate, int yCoordinate){
        setType(type);
        setPosition(xCoordinate, yCoordinate);
        this.inputs = new ArrayList<>();
        this.currentValue = true;
        this.gateName = null;
    }

    public Gate(GateType type, int xCoordinate, int yCoordinate, String gateName){
        this(type, xCoordinate, yCoordinate);
        this.gateName = gateName;
    }

    public GateType getType() {
        return type;
    }

    public void setType(GateType type) {
        this.type = type;
    }

    public String getGateName() {
        return gateName;
    }

    public void setGateName(String gateName) {
        this.gateName = gateName;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public Boolean getCurrentValue() {
        return currentValue;
    }

    public ArrayList<Gate> getInputs() {
        return inputs;
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
                drawANDGate(context);
            }
            case OR -> {
                drawORGate(context);
            }
            case NOT -> {
                drawNOTGate(context);
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
        int x = xCoordinate;
        int y = yCoordinate;

        // Draw the circle now
        context.setFill(fillColor);
        context.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        // Draw outline on circle
        context.setStroke(Color.BLACK);
        context.setLineWidth(2);
        context.strokeOval(x - radius, y - radius, radius * 2, radius * 2);

        // Draw label
        context.setFill(Color.WHITE);
        context.setFont(Font.font("Consolas", FontWeight.BOLD, 12));
        context.fillText(gateName, x - 3, y + 5);

    }

    // Draw AND gate as D-Shape
    private void drawANDGate(GraphicsContext context) {
        int leftX = xCoordinate;
        int topY = yCoordinate;

        double width = GATE_WIDTH;
        double height = GATE_HEIGHT;

        context.setFill(Color.TRANSPARENT);

        // Drawing an open arc to form D-shape
        context.fillArc(leftX, topY, width, height, -90, 180, ArcType.CHORD);

        // Draw outline for D
        context.setStroke(Color.BLACK);
        context.setLineWidth(2);
        context.strokeArc(leftX, topY, width, height, -90, 180, ArcType.CHORD);

        context.setFill(Color.BLACK);
        context.fillRect(leftX + 38, topY + 15, 5, 5); // Top rectangle part of D (First connector)
        context.fillRect(leftX + 38, topY + 45, 5, 5); // Bottom rectangle part of D (Second connector)
    }


    private void drawORGate(GraphicsContext context) {
        int leftX = xCoordinate;
        int topY = yCoordinate;

        context.setFill(Color.TRANSPARENT);

        // Start a single path for the whole OR gate shape (don't restart with moveTo repeatedly)
        context.beginPath();

        // Start at the left middle
        context.moveTo(leftX, topY + 20); // (400, 450)

        // Upper outer curve (uses your existing hard-coded control/anchor points)
        context.quadraticCurveTo(leftX + 60, topY + 25, leftX + 120, topY - 15);

        // Lower outer curve back toward the left-bottom area
        context.quadraticCurveTo(leftX + 60, topY - 50, leftX, topY - 45);

        // Inner curve that returns to the starting point and completes the shape
        context.quadraticCurveTo(leftX + 20, topY - 10, leftX, topY + 20);

        // Close the path to ensure the region is a single enclosed shape
        context.closePath();

        // Fill then stroke the closed path
        context.fill();

        context.setStroke(Color.BLACK);
        context.setLineWidth(2);
        context.stroke();

        context.setFill(Color.BLACK);
        context.fillRect(leftX + 5, topY - 25, 5, 5); // Top rectangle part of D (First connector)
        context.fillRect(leftX + 5, topY - 5, 5, 5); // Bottom rectangle part of D (Second connector)
    }

    // Triangle with small circle in front for NOT gate
    private void drawNOTGate(GraphicsContext context) {
        int leftX = xCoordinate;
        int topY = yCoordinate;

        double width = GATE_WIDTH;
        double height = GATE_HEIGHT;

        // Draw and fill triangle
        context.setFill(Color.TRANSPARENT);
        context.fillPolygon(
                new double[]{leftX, leftX, leftX + width},
                new double[]{topY, topY + height, topY + height / 2},
                3
        );
        context.strokePolygon(new double[]{leftX, leftX, leftX + width},
                new double[]{topY, topY + height, topY + height / 2},
                3);

        context.fillOval(leftX + 85, topY + 25, 15, 15); // Output circle
        context.strokeOval(leftX + 85, topY + 25, 15, 15); // Outline for output circle

        context.setFill(Color.BLACK);
        context.fillRect(leftX - 5, topY + 30, 5, 5);
    }

    private void drawOutputGate(GraphicsContext context, Color fillColor) {
        // Draw output as a circle
        int radius = 25;

        // Draw the circle now
        context.setFill(fillColor);
        context.fillOval(xCoordinate - radius, yCoordinate - radius, radius * 2, radius * 2);

        // Draw outline on circle
        context.setStroke(Color.BLACK);
        context.setLineWidth(2);
        context.strokeOval(xCoordinate - radius, yCoordinate - radius, radius * 2, radius * 2);

        // Draw label
        context.setFill(Color.WHITE);
        context.setFont(Font.font("Consolas", FontWeight.BOLD, 12));
        context.fillText(gateName, xCoordinate - 3, yCoordinate + 5);

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

    public void setPosition(int x, int y){
        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    // Sets the value for the INPUTs. This is how users toggle them
    public void setValue(Boolean boolValue) throws Exception{
        if (this.type != GateType.INPUT){
            throw new Exception("Values can only be set on inputs");
        }

        this.currentValue = boolValue;
    }

    public boolean containsPoint(Double x, Double y){

        boolean finalll = false;

        if (this.type == GateType.INPUT || this.type == GateType.OUTPUT){
            double radius = 20;
            double distance = Math.sqrt(Math.pow(x - this.xCoordinate, 2.0) + Math.pow((y - this.yCoordinate), 2.0));
            finalll = distance <= radius;
            return finalll;
        }else {
            finalll = x >= xCoordinate && x <= xCoordinate + GATE_WIDTH &&
                    y >= yCoordinate && y <= yCoordinate + GATE_HEIGHT;
        }

        return finalll;

    }

    public Point getOutputPoint(){
        if (this.type == GateType.INPUT){
            return new Point(this.xCoordinate + 20, this.yCoordinate);
        }

        if (this.type == GateType.NOT){
            return new Point(this.xCoordinate + GATE_WIDTH, this.yCoordinate + GATE_HEIGHT / 2);
        }

        if (this.type == GateType.AND || this.type == GateType.OR){
            return new Point(this.xCoordinate + GATE_WIDTH, this.yCoordinate + GATE_HEIGHT / 2);
        }

        return new Point(this.xCoordinate, this.yCoordinate);
    }

    public Point getInputPoint(int index) throws Exception{
        if (this.type == GateType.INPUT){
            throw new Exception("INPUT gates don't have input points");
        }

        if (this.type == GateType.NOT){
            return new Point(this.xCoordinate, this.yCoordinate + GATE_HEIGHT / 2);
        }

        if (this.type == GateType.AND || this.type == GateType.OR){
            if (index == 0){
                return new Point(xCoordinate, yCoordinate + GATE_HEIGHT / 4);
            }else if (index == 1){
                return new Point(xCoordinate, yCoordinate + 3 * GATE_HEIGHT / 4);
            }
        }

        throw new Exception("Invalid input index: " + index);
    }


}
