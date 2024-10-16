package com.example.sql.exceptions;

public class InvalidSyntaxError extends Exception {
    public InvalidSyntaxError(String clause){
        super("Invalid SQL syntax near "+clause);
    }

    public InvalidSyntaxError(String clause, String msg) {
        super("Invalid SQL syntax near " + clause + ": " + msg);
    }
}
