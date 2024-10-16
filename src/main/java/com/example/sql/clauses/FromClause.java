package com.example.sql.clauses;

import com.example.sql.db.DbStorage;
import com.example.sql.db.DbTable;
import com.example.sql.exceptions.InvalidSyntaxError;
import com.example.sql.exceptions.InvalidValueError;
import com.example.sql.exceptions.TableNotFoundError;

public class FromClause extends BaseClause implements SqlClause {
    final static String[] NEXT_CLAUSES = { "WHERE", "JOIN", null };
    final static String[] PREV_CLAUSES = { "SELECT" };

    public FromClause(){
        super("FROM", 1, PREV_CLAUSES, NEXT_CLAUSES);
    }

    public DbTable execute(DbStorage db, DbTable table) throws TableNotFoundError, InvalidValueError, InvalidSyntaxError {
        table = db.getTable(this.getValue(0));
        for (int i=1; i<this.getValues().size(); i++){
            if (table.getName().equals(this.getValue(i))) 
                throw new InvalidValueError(this.getClause(), "Repeating table name");
            table = table.merge(db.getTable(this.getValue(i)));
        }
        return table;
    }
}
