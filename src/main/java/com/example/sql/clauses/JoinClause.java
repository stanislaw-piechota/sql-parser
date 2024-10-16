package com.example.sql.clauses;

import com.example.sql.db.DbStorage;
import com.example.sql.db.DbTable;
import com.example.sql.exceptions.InvalidSyntaxError;
import com.example.sql.exceptions.TableNotFoundError;

public class JoinClause extends BaseClause implements SqlClause {
    final static String[] NEXT_CLAUSES = { "ON" };
    final static String[] PREV_CLAUSES = { "FROM" };
    
    public JoinClause(){
        super("JOIN", 2, PREV_CLAUSES, NEXT_CLAUSES);
    }

    @Override
    public boolean checkValuesRequired(){
        return this.getValues().size()==1;
    }

    public DbTable execute(DbStorage db, DbTable table) throws TableNotFoundError, InvalidSyntaxError {
        DbTable joinTable = db.getTable(this.getValue(0));
        return table.merge(joinTable);
    }
}
