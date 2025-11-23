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
//        setFrom(from);
//        setTo(to);
    }

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

// Helper methods

    public Boolean getValue(){
        return null;
    }

    public void draw(GraphicsContext context){

    }


}
