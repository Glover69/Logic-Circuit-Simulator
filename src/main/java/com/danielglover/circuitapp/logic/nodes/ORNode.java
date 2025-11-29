package com.danielglover.circuitapp.logic.nodes;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ORNode extends ExprNode {
    private ExprNode left;
    private ExprNode right;

    public ORNode(ExprNode left, ExprNode right){
        this.left = left;
        this.right = right;
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> values) {
        Boolean leftValue = this.left.evaluate(values);
        Boolean rightValue = this.right.evaluate(values);
        return leftValue || rightValue;
    }

    @Override
    public String toString() {
        return "(" + this.left.toString() + " || " + this.right.toString() + ")";
    }

    @Override
    public Set<String> getAllVariables() {

        // Get variables from left & right side
        Set<String> leftVars = this.left.getAllVariables();
        Set<String> variables = new HashSet<>(leftVars);
        Set<String> rightVars = this.right.getAllVariables();
        variables.addAll(rightVars);
        return variables;
    }

    public ExprNode getLeft() {
        return left;
    }

    public ExprNode getRight() {
        return right;
    }
}
