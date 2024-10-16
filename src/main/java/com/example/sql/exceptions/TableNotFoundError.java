package com.example.sql.exceptions;

public class TableNotFoundError extends Exception {
    public TableNotFoundError(String filename, String table){
        super("Database " + filename + " doesn't contain " + table + " table");
    }
}
