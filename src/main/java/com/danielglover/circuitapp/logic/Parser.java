package com.danielglover.circuitapp.logic;

import com.danielglover.circuitapp.logic.enums.TokenType;
import com.danielglover.circuitapp.logic.nodes.*;

import java.util.List;


/*

The parser class is a bit confusing to understand at first but here's a
simplified way of how it works:

The parser takes a flat list of tokens (e.g. Token(VAR,"A")) from the token class and builds a tree structure
that represents the logical expression that the user inputs

An example would be:
Input: [Token(VAR,"A"), Token(AND,"&&"), Token(VAR,"B")]
Output: A tree where AND is the root with A and B as children

It works based on operator precedence, kinda like BODMAS in math. If you have an expression like 2 * 3 + 4,
you would execute the multiplication before the addition, because it has a higher precedence

So in logic, the operator precedence would be: OR < AND < NOT

We use three methods to do this evaluation:
The class has 3 methods;
parseExpression() [Which handles OR],
parseTerm() [Which handles AND],
parseFactor() [Which handles NOT, parentheses and variables]

So, the call is done from the main Parser class, which calls parseExpression(), which then calls parseTerm(), and then that
calls parseFactor()

Now for instance, when this chain call is done, and we get to the parseFactor(), and that identifies a variable, it brings that variable
up to parseTerm(), which looks for AND's until there are no more, and then the result (A AND) goes back to the parseFactor() because
a variable is expected after a gate. the result is brought up all the way to parseExpression as ANDNode(A, B)

When the entire expression is consumed, then the parsed form of the expression is returned as a tree for building the circuit


*/

public class Parser {
    private List<Token> tokens;
    private int position;

    public Parser() {

    }


    public ExprNode parse(List<Token> tokens) throws Exception {
        // Initialize state
        this.tokens = tokens;
        this.position = 0;

        // Throw error if expression is empty
        if (tokens.isEmpty()){
            System.out.println("Cannot parse empty expression");
        }

        // Start parsing from the lowest precedence of gates (OR)
        ExprNode result = parseExpression();

        // Now, we check if all the tokens were used
        if (position < tokens.size()){
            Token remainingTokens = tokens.get(position);
            throw new Exception("Unexpected token after expression: " + remainingTokens.value +
                    " at position  " + position);

        }

        return result;
    }

    // Parses OR expressions
    public ExprNode parseExpression() throws Exception {
        // Parse left side of expression

        ExprNode left = parseTerm();

        while (doesTokenTypeMatch(TokenType.OR)){
            moveToNextToken();

            ExprNode right = parseTerm();

            left = new ORNode(left, right);
        }

        return left;
    }


    public ExprNode parseTerm() throws Exception{

        ExprNode left = parseFactor();

        while (doesTokenTypeMatch(TokenType.AND)){
            moveToNextToken();

            ExprNode right = parseFactor();

            left = new ANDNode(left, right);
        }

        return left;
    }


    public ExprNode parseFactor() throws Exception{
        Token currentToken = seeTokenPosition();

        if (currentToken == null){
            throw new Exception("Unexpected end of expression at position" + position);
        }

        if (currentToken.getType() == TokenType.NOT){
            moveToNextToken();

            ExprNode c = parseFactor();

            return new NotNode(c);
        }

        if (currentToken.getType() == TokenType.LPAREN){
            moveToNextToken();

            ExprNode inner = parseExpression();

            Token closingBracket = seeTokenPosition();

            if (closingBracket == null){

            }

            if (closingBracket.getType() != TokenType.RPAREN){
                throw new Exception("Expected ')', but found: " + closingBracket.value +
                        " at position " + position);
            }

            moveToNextToken();
            return inner;
        }

        if (currentToken.getType() == TokenType.VAR){
            moveToNextToken();

            return new VariableNode(currentToken.getValue());
        }

        throw new Exception("Unexpected token: " + currentToken.value + ("type: " + currentToken.type + ") at position " + position));
    }






    // Helper methods

    // Checks for the current token w/o any further actions
    public Token seeTokenPosition(){
        // Returns null if we're at the end of the list
        if (position >= tokens.size()){
            return null;
        }

        return tokens.get(position);
    }

    // Checks the type of the current token. Also, w/o any further actions
    public TokenType seeTokenPositionType(){
        Token token = seeTokenPosition();

        if (token == null){
            return null;
        }

        return token.getType();
    }

    // Gets the current token and moves to the next
    public Token moveToNextToken() throws Exception{
        // Returns null if we're at the end of the list
        if (position >= tokens.size()){
            throw new Exception("Unexpected end of expression at position \" + position");
        }

        Token token = tokens.get(position);
        position++;
        return token;
    }

    // Checks if the current token's type is the same as the expected in the param
    public Boolean doesTokenTypeMatch(TokenType expectedTokenType){
        TokenType currentType = seeTokenPositionType();

        // Covering edge case for an empty type
        if (currentType == null){
            return false;
        }

        // Returns the boolean value output of the comparison
        return currentType == expectedTokenType;
    }

}
