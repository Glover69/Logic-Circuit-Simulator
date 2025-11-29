package com.danielglover.circuitapp.logic.nodes;

import java.util.Map;
import java.util.Set;


public abstract class ExprNode {

    public abstract Boolean evaluate(Map<String, Boolean> values);


    public abstract String toString();

    public abstract Set<String> getAllVariables();
}