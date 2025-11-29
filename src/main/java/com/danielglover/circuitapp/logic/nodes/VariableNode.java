package com.danielglover.circuitapp.logic.nodes;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class VariableNode extends ExprNode {

    private String name;

    public VariableNode(String name) {
        this.name = name;
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> values) {
        if (!values.containsKey(name)) {
            throw new IllegalArgumentException("Variable " + this.name + " not found in values");
        }
        return values.get(name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Set<String> getAllVariables() {
        Set<String> variables = new HashSet<>();
        variables.add(name);
        return variables;
    }
    public String getName(){
        return this.name;
    }
}