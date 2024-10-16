package com.example.sql.exceptions;

public class InvalidValueError extends Exception {
    public InvalidValueError(String clause){
        super("Invalid values for clause "+clause);
    }

    public InvalidValueError(String clause, String msg) {
        super("Invalid values for clause " + clause + ": " + msg);
    }
}
