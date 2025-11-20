package com.danielglover.circuitapp.logic;

import com.danielglover.circuitapp.logic.enums.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    // Assuming string is "(A && B) || C"
    public List<Token> tokenize(String input) {

        ArrayList<Token> tokens = new ArrayList<>();
        int position = 0;

        while (position < input.length()) {
            char currentChar = input.charAt(position);

            // Handle all character types
            if (Character.isWhitespace(currentChar)) {
                position++;
                continue;
            }

            if (currentChar == '(') {
                tokens.add(new Token(TokenType.LPAREN, String.valueOf(currentChar)));
                position++;
                continue;
            }

            if (currentChar == ')'){
                tokens.add(new Token(TokenType.RPAREN, String.valueOf(currentChar)));
                position++;
                continue;
            }

            if (currentChar == '!'){
                tokens.add(new Token(TokenType.NOT, String.valueOf(currentChar)));
                position++;
                continue;
            }

            if (currentChar == '&'){
                if (position + 1 < input.length() && input.charAt(position + 1) == '&') {
                    tokens.add(new Token(TokenType.AND, "&&"));
                    position += 2;
                }else{
                    System.out.println("Invalid character: single &");
                }
                continue;
            }

            if (currentChar == '|'){
                if (position + 1 < input.length() && input.charAt(position + 1) == '|'){
                    tokens.add(new Token(TokenType.OR, "||"));
                    position += 2;
                }else{
                    System.out.println("Invalid character: single |");
                }

                continue;
            }

            if (Character.isLetter(currentChar)){
                int start = position;
                while (position < input.length() && Character.isLetter(input.charAt(position))) {
                    position++;
                }

                String varName = input.substring(start, position);
                tokens.add(new Token(TokenType.VAR, varName));
                continue;
            }

        }

        return tokens;
    }
}
