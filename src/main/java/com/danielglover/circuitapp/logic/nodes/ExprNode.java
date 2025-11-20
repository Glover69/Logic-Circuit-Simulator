package com.danielglover.circuitapp.logic.nodes;

import java.util.Map;
import java.util.Set;

public abstract class ExprNode {

    abstract public Boolean evaluate(Map<String, Boolean> values);
    abstract public String backToString();
    abstract public Set<String> getAllVariables();

}
