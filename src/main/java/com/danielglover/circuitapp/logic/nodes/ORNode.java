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
    public String backToString() {
        return "(" + this.left.backToString() + " || " + this.right.backToString() + ")";
    }

    @Override
    public Set<String> getAllVariables() {
        Set<String> variables = new HashSet<>();

        // Get variables from left & right side
        Set<String> leftVariables = this.left.getAllVariables();
        Set<String> rightVariables = this.right.getAllVariables();

        for (String l: leftVariables){
            variables.add(l);
        }

        for (String r: rightVariables){
            variables.add(r);
        }

        return variables;
    }


    // Getters & setters

    public ExprNode getLeft() {
        return left;
    }

    public void setLeft(ExprNode left) {
        this.left = left;
    }

    public ExprNode getRight() {
        return right;
    }

    public void setRight(ExprNode right) {
        this.right = right;
    }
}
