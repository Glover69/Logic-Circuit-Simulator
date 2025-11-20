package com.danielglover.circuitapp.logic;

import com.danielglover.circuitapp.logic.enums.TokenType;

public class Token {
    public TokenType type;
    public String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return  "Token(" + type + ", " + value + ")";
    }
}
