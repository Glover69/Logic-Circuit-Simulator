package com.danielglover.circuitapp.ui;

import com.danielglover.circuitapp.logic.circuit.Circuit;
import com.danielglover.circuitapp.logic.circuit.Gate;
import com.danielglover.circuitapp.logic.enums.GateType;
import com.danielglover.circuitapp.logic.interfaces.CircuitOutputListener;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class CircuitCanvas extends Canvas {
    private Circuit circuit;
    private GraphicsContext gc;
    private CircuitOutputListener outputListener;

    public CircuitCanvas(double width, double height){
        super(width, height);
        this.gc = this.getGraphicsContext2D();

        // Set up mouse click handler
        this.setOnMouseClicked(event -> {
            try {
                handleMouseClick(event);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void setCircuit(Circuit circuit){
        this.circuit = circuit;
    }


    // Redraws the circuit whenever the expression changes
    public void redraw(){
        // Clear canvas and draw
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, this.getWidth(), this.getHeight());

        if (circuit != null){
            circuit.draw(gc);
        }
    }

    public void handleMouseClick(MouseEvent event) throws Exception {
        if (circuit == null){
            return;
        }

        double varX = event.getX();
        double varY = event.getY();

        Gate clickedGate = circuit.getGateAt(varX, varY);

        if (clickedGate != null && clickedGate.getType() == GateType.INPUT){
            // If the gate is an input then we toggle it to either 0 or 1
            String gateName = clickedGate.getGateName();
            circuit.toggleInput(gateName);

            redraw();

            if (outputListener != null){
                outputListener.onOutputChange();
            }
        }
    }

    public void setOutputListener(CircuitOutputListener listener){
        this.outputListener = listener;
    }

}
