package com.danielglover.circuitapp.logic.nodes;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ANDNode extends ExprNode {
    private ExprNode left;
    private ExprNode right;

    public ANDNode(ExprNode left, ExprNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> values) {
        boolean leftValue = this.left.evaluate(values);
        boolean rightValue = this.right.evaluate(values);
        return leftValue && rightValue;
    }

    @Override
    public String toString() {
        return "(" + this.left.toString() + " && " + this.right.toString() + ")";
    }

    @Override
    public Set<String> getAllVariables() {
        Set<String> variables = new HashSet<>();
        Set<String> leftVars = this.left.getAllVariables();
        variables.addAll(leftVars);
        Set<String> rightVars = this.right.getAllVariables();
        variables.addAll(rightVars);
        return variables;
    }

    public ExprNode getLeft() {
        return this.left;
    }

    public ExprNode getRight() {
        return this.right;
    }
}
