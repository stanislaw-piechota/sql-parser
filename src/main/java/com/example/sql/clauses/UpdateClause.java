package com.example.sql.clauses;

import com.example.sql.db.DbStorage;
import com.example.sql.db.DbTable;
import com.example.sql.exceptions.InvalidSyntaxError;
import com.example.sql.exceptions.InvalidValueError;
import com.example.sql.exceptions.TableNotFoundError;

public class UpdateClause extends BaseClause implements SqlClause {
    final static String[] NEXT_CLAUSES = { "SET" };
    final static String[] PREV_CLAUSES = { null };

    public UpdateClause(){
        super("UPDATE", 1, PREV_CLAUSES, NEXT_CLAUSES);
    }

    @Override
    public boolean checkValuesRequired(){
        return this.getValues().size() == 1;
    }

    public DbTable execute(DbStorage db, DbTable table) throws TableNotFoundError, InvalidValueError, InvalidSyntaxError {
        return db.getTable(this.getValue(0));
    }
}
