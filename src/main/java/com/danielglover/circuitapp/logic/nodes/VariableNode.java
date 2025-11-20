package com.danielglover.circuitapp.logic.nodes;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class VariableNode extends ExprNode{
    private String variableName;

    public VariableNode(String variableName) {
        setVariableName(variableName);
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> values) {
        if (!values.containsKey(variableName)) {
            System.err.println("Variable " + variableName + " does not exist");
        }

        return values.get(variableName);
    }

    @Override
    public String backToString() {
        return variableName;
    }

    @Override
    public Set<String> getAllVariables() {
        Set<String> variables = new HashSet<>();
        variables.add(variableName);

        return variables;
    }

    // Getters and Setters
    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }
}
