package com.danielglover.circuitapp.logic.circuit;

import com.danielglover.circuitapp.logic.enums.GateType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Circuit {
    private List<Gate> gates;
    private List<Wire> wires;
    private Map<String, Gate> inputGates;
    private Gate outputGate;

    public Circuit() {
        this.gates = new ArrayList<>();
        this.wires = new ArrayList<>();
        this.inputGates = new HashMap<>();
        this.outputGate = null;
    }

    public void addGate(Gate gate) {
        this.gates.add(gate);

        if (gate.getType() == GateType.INPUT) {
            if (gate.getGateName() != null) {
                this.inputGates.put(gate.getGateName(), gate);
            }
        }
    }

    public void addWire(Wire wire) {
        this.wires.add(wire);
    }

    public void setOutputGate(Gate gate){
        this.outputGate = gate;
    }

    public Boolean evaluate() throws Exception{
        if (this.outputGate == null){
            throw new Exception("No output gate set for circuit");
        }

        return this.outputGate.evaluate();
    }


    public void toggleInput(String varName) throws Exception {
        if (!inputGates.containsKey(varName)){
            throw new Exception("No input gate for variable: " + varName);
        }

        Gate gate = inputGates.get(varName);
        Boolean currentValue = gate.getCurrentValue();

        if (currentValue == null){
            gate.setValue(true);
        }else{
            gate.setValue(!currentValue);
        }
    }

    public void setInputValue(String varName, Boolean value) throws Exception{
        if (!inputGates.containsKey(varName)){
            throw new Exception("No input gate for variable: " + varName);
        }

        Gate gate = inputGates.get(varName);
        gate.setValue(value);
    }

    public void reset() throws Exception {
        inputGates.forEach((key, value) -> {
            try {
//                System.out.println("Resetting input gate: " + key);
                value.setValue(false);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void draw(GraphicsContext context){
        context.setFill(Color.WHITE);
        context.fillRect(0, 0, context.getCanvas().getWidth(), context.getCanvas().getHeight());

        for (Wire wire : wires){
            wire.draw(context);
        }

        for(Gate gate : gates){
            gate.draw(context);
        }
    }


    public Gate getGateAt(double x, double y){
        for (Gate gate : gates){
            if (gate.containsPoint(x, y)){
                return gate;
            }
        }

        return null;
    }

    public List<Gate> getGates() {
        return gates;
    }

    public List<Wire> getWires() {
        return wires;
    }

    public Map<String, Gate> getInputGates() {
        return inputGates;
    }
}
