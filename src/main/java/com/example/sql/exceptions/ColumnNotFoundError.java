package com.example.sql.exceptions;

public class ColumnNotFoundError extends Exception {
    public ColumnNotFoundError(String filename, String table, String column){
        super(String.format(
            "Database %s doesn't have column \"%s\" in table \"%s\"",
            filename, column, table
        ));
    }

    public ColumnNotFoundError(String table, String column) {
        super(String.format(
            "Database doesn't have column \"%s\" in table \"%s\"",
            column, table
        ));
    }

    public ColumnNotFoundError(String column) {
        super(String.format("Column \"%s\" doesn't exist in this context", column));
    }
}
