package com.danielglover.circuitapp.logic.test;

import com.danielglover.circuitapp.logic.Parser;
import com.danielglover.circuitapp.logic.Token;
import com.danielglover.circuitapp.logic.Tokenizer;
import com.danielglover.circuitapp.logic.nodes.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDriver {
    public static void main(String[] args) throws Exception {
        Tokenizer tokenizer = new Tokenizer();

        System.out.println(tokenizer.tokenize("(A && B) || C"));

        List<Token> tokens = tokenizer.tokenize("");

        Parser parser = new Parser();

        ExprNode result = parser.parse(tokens);

        Map<String, Boolean> values = new HashMap<>(Map.of("A", true));
        values.put("B", true);
        values.put("C", false);

        System.out.println(result.toString());
        System.out.println(result.evaluate(values));

//        VariableNode nodeA = new VariableNode("A");
//        VariableNode nodeB = new VariableNode("B");
//
//        ANDNode andNode = new ANDNode(nodeA, nodeB);
//        NotNode notNode = new NotNode(andNode);
//
//        Map<String, Boolean> values = new HashMap<>(Map.of("A", true));
//        values.put("B", false);
//
//        System.out.print(notNode.evaluate(values));

    }
}
