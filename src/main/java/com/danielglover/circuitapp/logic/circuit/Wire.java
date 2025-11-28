package com.danielglover.circuitapp.logic.circuit;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.GREEN;

public class Wire {
    private Gate from;
    private Gate to;
    private double fromPointX;
    private double fromPointY;
    private double toPointX;
    private double toPointY;
    private List<Point2D> pathPoints;

    public Wire(Gate from, Gate to) throws Exception{
        this.from = from;
        this.to = to;

        Point2D outputPoint = from.getOutputPoint();
        this.fromPointX = outputPoint.getX();
        this.fromPointY = outputPoint.getY();

        int i = to.getInputs().indexOf(from);
        
        if (i == -1){
            i = to.getInputs().size();
        }

        Point2D iPoint = to.getInputPoint(i);
        this.toPointX = iPoint.getX();
        this.toPointY = iPoint.getY();

        calculatePath();

        // In Wire constructor or createWires()
        System.out.println("Creating wire from " + from.getType() +
                " at (" + fromPointX + "," + fromPointY + ")" +
                " to " + to.getType() +
                " at (" + toPointX + "," + toPointY + ")");
    }

    public Wire(Gate from, Gate to, int fromPointX, int fromPointY, int toPointX, int toPointY) throws Exception {
        this(from, to);
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
        return (int) fromPointX;
    }

    public int getFromPointY() {
        return (int) fromPointY;
    }

    public int getToPointX() {
        return (int) toPointX;
    }

    public int getToPointY() {
        return (int) toPointY;
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

        for (int i = 0; i < pathPoints.size() - 1; i++) {
            Point2D p1 = pathPoints.get(i);
            Point2D p2 = pathPoints.get(i + 1);
            context.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        }

//        Point2D p1 = pathPoints.get(i);
//        Point2D p2 = pathPoints.get(i + 1);
//        context.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
//
//        context.strokeLine(fromPointX, fromPointY, toPointX, toPointY);

    }

    public void updateCoordinates() throws Exception{
        calculatePath();
    }


    // Update to Wire class

    private void calculatePath() throws Exception {
        // Get connection points from gates
        Point2D fromPoint = from.getOutputPoint();

        // Find which input this wire connects to
        int inputIndex = to.getInputs().indexOf(from);
        if (inputIndex == -1) {
            // If not found, this wire will be the first input
            inputIndex = 0;
        }

        Point2D toPoint = to.getInputPoint(inputIndex);

        // Choose routing strategy
        this.pathPoints = routeWithThreeSegments(fromPoint, toPoint);
    }

    private List<Point2D> routeWithThreeSegments(Point2D start, Point2D end) {
        List<Point2D> points = new ArrayList<>();

        double x1 = start.getX();
        double y1 = start.getY();
        double x2 = end.getX();
        double y2 = end.getY();

        // Calculate intermediate point (60% of horizontal distance)
        double midX = x1 + (x2 - x1) * 0.6;

        // Build path
        points.add(new Point2D(x1, y1));        // Start
        points.add(new Point2D(midX, y1));      // Go horizontal
        points.add(new Point2D(midX, y2));      // Go vertical
        points.add(new Point2D(x2, y2));        // Go horizontal to end

        return points;
    }


}
