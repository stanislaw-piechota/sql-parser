package com.example.sql.clauses;

import java.io.IOException;
import java.net.URISyntaxException;

import com.example.sql.db.DbStorage;
import com.example.sql.db.DbTable;
import com.example.sql.exceptions.InvalidSyntaxError;
import com.example.sql.exceptions.InvalidValueError;
import com.example.sql.exceptions.TableNotFoundError;

public class InsertClause extends BaseClause implements SqlClause {
    final static String[] NEXT_CLAUSES = { "INTO" };
    final static String[] PREV_CLAUSES = { null };

    public InsertClause(){
        super("INSERT", 5, PREV_CLAUSES, NEXT_CLAUSES);
    }

    @Override
    public boolean checkValuesRequired(){
        return this.getValues().isEmpty();
    }

    public DbTable execute(DbStorage db, DbTable table) 
        throws TableNotFoundError, InvalidValueError, InvalidSyntaxError, URISyntaxException, IOException {
        db.setTable(table.getName(), table);
        db.save();
        return new DbTable();
    }
}
