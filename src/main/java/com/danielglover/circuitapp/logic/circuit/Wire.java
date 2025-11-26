package com.danielglover.circuitapp.logic.circuit;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.awt.*;

import static java.awt.Color.GREEN;

public class Wire {
    private Gate from;
    private Gate to;
    private int fromPointX;
    private int fromPointY;
    private int toPointX;
    private int toPointY;

    public Wire(Gate from, Gate to) throws Exception{
        this.from = from;
        this.to = to;

        Point outputPoint = from.getOutputPoint();
        this.fromPointX = outputPoint.x;
        this.fromPointY = outputPoint.y;

        int i = to.getInputs().indexOf(from);
        
        if (i == -1){
            i = to.getInputs().size();
        }

        Point iPoint = to.getInputPoint(i);
        this.toPointX = iPoint.x;
        this.toPointY = iPoint.y;

        // In Wire constructor or createWires()
        System.out.println("Creating wire from " + from.getType() +
                " at (" + fromPointX + "," + fromPointY + ")" +
                " to " + to.getType() +
                " at (" + toPointX + "," + toPointY + ")");
    }

    public Wire(Gate from, Gate to, int fromPointX, int fromPointY, int toPointX, int toPointY){
        setFrom(from);
        setTo(to);
        setFromPointX(fromPointX);
        setFromPointY(fromPointY);
        setToPointX(toPointX);
        setToPointY(toPointY);
    }

    // Getters

    public Gate getFrom() {
        return from;
    }

    public Gate getTo() {
        return to;
    }

    public int getFromPointX() {
        return fromPointX;
    }

    public int getFromPointY() {
        return fromPointY;
    }

    public int getToPointX() {
        return toPointX;
    }

    public int getToPointY() {
        return toPointY;
    }

    // Setters

    public void setFrom(Gate from) {
        this.from = from;
    }

    public void setTo(Gate to) {
        this.to = to;
    }

    public void setFromPointX(int fromPointX) {
        this.fromPointX = fromPointX;
    }

    public void setFromPointY(int fromPointY) {
        this.fromPointY = fromPointY;
    }

    public void setToPointX(int toPointX) {
        this.toPointX = toPointX;
    }

    public void setToPointY(int toPointY) {
        this.toPointY = toPointY;
    }


    // Helper methods

    public Boolean getValue() throws Exception{
        return this.from.evaluate();
    }

    public void draw(GraphicsContext context){
        try {
            boolean value = getValue();

            if (value){
                context.setStroke(Color.GREEN);
                context.setLineWidth(3);
            }else{
                context.setStroke(Color.RED);
                context.setLineWidth(3);
            }

        } catch (Exception e) {
            // Make it gray when the above evaluation fails
            context.setStroke(Color.GRAY);
            context.setLineWidth(2);
        }

        context.strokeLine(fromPointX, fromPointY, toPointX, toPointY);

    }

    public void updateCoordinates() throws Exception{
        Point outputPoint = from.getOutputPoint();
        this.fromPointX = outputPoint.x;
        this.fromPointY = outputPoint.y;

        int i = to.getInputs().indexOf(from);

        Point iPoint = to.getInputPoint(i);
        this.toPointX = iPoint.x;
        this.toPointY = iPoint.y;


    }


}
