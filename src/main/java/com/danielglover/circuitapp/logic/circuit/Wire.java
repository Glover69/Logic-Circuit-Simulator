package com.danielglover.circuitapp.logic.circuit;

import javafx.scene.canvas.GraphicsContext;

public class Wire {
    private Gate from;
    private Gate to;
    private int fromPointX;
    private int fromPointY;
    private int toPointX;
    private int toPointY;

    public Wire(Gate from, Gate to){
        setFrom(from);
        setTo(to);
    }

    public Gate getFrom() {
        return from;
    }

    public void setFrom(Gate from) {
        this.from = from;
    }

    public Gate getTo() {
        return to;
    }

    public void setTo(Gate to) {
        this.to = to;
    }

    public int getFromPointX() {
        return fromPointX;
    }

    public void setFromPointX(int fromPointX) {
        this.fromPointX = fromPointX;
    }

    public int getFromPointY() {
        return fromPointY;
    }

    public void setFromPointY(int fromPointY) {
        this.fromPointY = fromPointY;
    }

    public int getToPointX() {
        return toPointX;
    }

    public void setToPointX(int toPointX) {
        this.toPointX = toPointX;
    }

    public int getToPointY() {
        return toPointY;
    }

    public void setToPointY(int toPointY) {
        this.toPointY = toPointY;
    }


    // Helper methods

    public Boolean getValue(){
        return null;
    }

    public void draw(GraphicsContext context){

    }


}
