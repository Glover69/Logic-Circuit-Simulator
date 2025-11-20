package com.danielglover.circuitapp.logic.nodes;

import java.util.Map;
import java.util.Set;

public class NotNode extends ExprNode {
    private ExprNode child;

    public NotNode(ExprNode child){
        this.child = child;
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> values) {
        Boolean childValue = this.child.evaluate(values);

        // Apply the NOT operation (negate it)
        return !childValue;
    }

    @Override
    public String backToString() {
        return "!" + this.child.backToString();
    }

    @Override
    public Set<String> getAllVariables() {
        return this.child.getAllVariables();
    }

    // Getter & Setter


    public ExprNode getChild() {
        return child;
    }

    public void setChild(ExprNode child) {
        this.child = child;
    }
}
