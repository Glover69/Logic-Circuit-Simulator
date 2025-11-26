package com.danielglover.circuitapp.logic.circuit;

import com.danielglover.circuitapp.logic.enums.GateType;
import com.danielglover.circuitapp.logic.nodes.*;

import java.util.*;

public class CircuitBuilder {
    private static Double startXPosition = 100.0;
    private static Double startYPosition = 300.0;
    private static Double layerSpacing = 200.0;
    private static Double gateSpacing = 150.0;

    private Map<ExprNode, Gate> nodeToGate;
    private int gateCounterID;

    public CircuitBuilder(){

    }

    public Circuit buildCircuit(ExprNode root) throws Exception {

        this.nodeToGate = new LinkedHashMap<>();
        this.gateCounterID = 0;

        createGates(root);

        calculateLayout(root);

        List<Wire> wires = createWires(root);

        Circuit circuit = new Circuit();

        nodeToGate.forEach((node, gate) -> {
            circuit.addGate(gate);
        });

        for (Wire wire: wires){
            circuit.addWire(wire);
        }

        Gate output = this.nodeToGate.get(root);
        circuit.setOutputGate(output);

        return circuit;
    }

    public void createGates(ExprNode node) throws Exception {
        if (this.nodeToGate.containsKey(node)){
            return;
        }

        if (node instanceof VariableNode varNode){
            Gate inputGate = new Gate(GateType.INPUT, 0, 0, varNode.getVariableName());
            this.nodeToGate.put(node, inputGate);

        }else if (node instanceof NotNode notNode){
            createGates(notNode.getChild());
            Gate notGate = new Gate(GateType.NOT, 0, 0);

            notGate.addInput(this.nodeToGate.get(notNode.getChild()));

            this.nodeToGate.put(node, notGate);

        }else if (node instanceof ANDNode andNode){
            createGates(andNode.getLeft());
            createGates(andNode.getRight());

            Gate andGate = new Gate(GateType.AND, 0, 0);

            andGate.addInput(this.nodeToGate.get(andNode.getLeft()));
            andGate.addInput(this.nodeToGate.get(andNode.getRight()));

            this.nodeToGate.put(node, andGate);

        }else if (node instanceof ORNode orNode){
            createGates(orNode.getLeft());
            createGates(orNode.getRight());

            Gate orGate = new Gate(GateType.OR, 0, 0);

            orGate.addInput(this.nodeToGate.get(orNode.getLeft()));
            orGate.addInput(this.nodeToGate.get(orNode.getRight()));

            this.nodeToGate.put(node, orGate);
        }
    }

    private void calculateLayout(ExprNode root){
        int maxDepth = calculateDepth(root);
        Map<ExprNode, Integer> layers = assignLayers(root, maxDepth);
        positionGates(layers);
    }

    private int calculateDepth(ExprNode node){
        if (node instanceof VariableNode){
            return 0;
        }else if (node instanceof NotNode notNode){
            return 1 + calculateDepth(notNode.getChild());
        }else if (node instanceof ANDNode andNode){
            return 1 + Math.max(calculateDepth(andNode.getLeft()), calculateDepth(andNode.getRight()));
        }else if (node instanceof ORNode orNode){
            return 1 + Math.max(calculateDepth(orNode.getLeft()), calculateDepth(orNode.getRight()));
        }

        return 0;
    }

    private Map<ExprNode, Integer> assignLayers(ExprNode node, int maxDepth){
        Map<ExprNode, Integer> layers = new LinkedHashMap<>();

        assignLayersHelper(node, maxDepth, layers, maxDepth);

        // After assignLayerRecursive completes
        System.out.println("\n=== LAYER ASSIGNMENTS ===");
        for (Map.Entry<ExprNode, Integer> entry : layers.entrySet()) {
            node = entry.getKey();
            Integer layer = entry.getValue();
            String nodeType = node.getClass().getSimpleName();
            String nodeLabel = "";
            if (node instanceof VariableNode) {
                nodeLabel = ((VariableNode) node).getVariableName();
            }
            System.out.println(nodeType + " " + nodeLabel + " -> Layer: " + layer);
        }

        return layers;
    }

    private void assignLayersHelper(ExprNode node, int currentDepth, Map<ExprNode, Integer> layers, int maxDepth){

        int layer = currentDepth;
        layers.put(node, layer);

        if (node instanceof VariableNode){
            layers.put(node, 0);
            return;
        }

        if (node instanceof NotNode notNode) {
            assignLayersHelper(notNode.getChild(), currentDepth - 1, layers, maxDepth);
            return;
        }

        if (node instanceof ANDNode andNode) {
            assignLayersHelper(andNode.getLeft(), currentDepth - 1, layers, maxDepth);
            assignLayersHelper(andNode.getRight(), currentDepth - 1, layers, maxDepth);
            return;
        }

        if (node instanceof ORNode orNode) {
            assignLayersHelper(orNode.getLeft(), currentDepth - 1, layers, maxDepth);
            assignLayersHelper(orNode.getRight(), currentDepth - 1, layers, maxDepth);
        }
    }

    private List<Wire> createWires(ExprNode root) throws Exception {
        List<Wire> wires = new java.util.ArrayList<>();

        class Helper {
            void createWiresHelper(ExprNode node) throws Exception {
                Gate currentGate = nodeToGate.get(node);

                if (node instanceof VariableNode){
                    return;
                }

                Gate gate = nodeToGate.get(node);

                if (node instanceof NotNode notNode) {
                    Gate childGate = nodeToGate.get(notNode.getChild());

                    Wire wire = new Wire(childGate, currentGate);
                    wires.add(wire);

                    createWiresHelper(notNode.getChild());

                } else if (node instanceof ANDNode andNode) {
                    Gate leftGate = nodeToGate.get(andNode.getLeft());
                    Gate rightGate = nodeToGate.get(andNode.getRight());

                    Wire leftWire = new Wire(leftGate, currentGate);
                    Wire rightWire = new Wire(rightGate, currentGate);

                    wires.add(leftWire);
                    wires.add(rightWire);

                    createWiresHelper(andNode.getLeft());
                    createWiresHelper(andNode.getRight());

                } else if (node instanceof ORNode orNode) {
                    Gate leftGate = nodeToGate.get(orNode.getLeft());
                    Gate rightGate = nodeToGate.get(orNode.getRight());

                    Wire leftWire = new Wire(leftGate, currentGate);
                    Wire rightWire = new Wire(rightGate, currentGate);

                    wires.add(leftWire);
                    wires.add(rightWire);

                    createWiresHelper(orNode.getLeft());
                    createWiresHelper(orNode.getRight());
                }
            }
        }

        new Helper().createWiresHelper(root);

        return wires;
    }

    private void positionGates(Map<ExprNode, Integer> layers){
        Map<Integer, List<Gate>> gatesByLayer = new LinkedHashMap<>();

        // Group gates by layer
        nodeToGate.forEach((node, gate) -> {
            int layer = layers.get(node);

            if (!gatesByLayer.containsKey(layer)){
                gatesByLayer.put(layer, new ArrayList<>());
            }

            gatesByLayer.get(layer).add(gate);

        });


        // Position each gate
        gatesByLayer.forEach((layer, gatesInLayer) -> {
            double x = startXPosition + (layer * layerSpacing);

            int numOfGates = gatesInLayer.size();

            double totalHeight = (numOfGates - 1) * gateSpacing;
            double startY = startYPosition + 200 - (totalHeight / 2);

            if (numOfGates == 1){
                startY = startYPosition + 200;
            }

            for (int i = 0; i < numOfGates; i++){
                Gate gate = gatesInLayer.get(i);
                double y = startY + (i * gateSpacing);
                gate.setPosition((int) x, (int) y);
            }
        });

        // After grouping gates by layer
        System.out.println("\n=== GATES BY LAYER ===");
        for (Map.Entry<Integer, List<Gate>> entry : gatesByLayer.entrySet()) {
            Integer layer = entry.getKey();
            List<Gate> gates = entry.getValue();
            System.out.println("Layer " + layer + ":");
            for (Gate gate : gates) {
                System.out.println("  - " + gate.getType() + " " + gate.getGateName());
            }
        }
    }


}
