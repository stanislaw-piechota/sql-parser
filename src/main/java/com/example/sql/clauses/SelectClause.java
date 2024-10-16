package com.example.sql.clauses;

import com.example.sql.db.DbStorage;
import com.example.sql.db.DbTable;
import com.example.sql.db.TableColumn;
import com.example.sql.exceptions.ColumnNotFoundError;
import com.example.sql.exceptions.InvalidSyntaxError;

public class SelectClause extends BaseClause implements SqlClause {
    final static String[] NEXT_CLAUSES = { "FROM" };
    final static String[] PREV_CLAUSES = { null };

    public SelectClause(){
        super("SELECT", 5, PREV_CLAUSES, NEXT_CLAUSES);
    }

    public DbTable execute(DbStorage db, DbTable table) throws InvalidSyntaxError, ColumnNotFoundError {
        if (this.getValue(0).equals("*")){
            if (this.getValues().size() > 1 ){
                throw new InvalidSyntaxError(getClause(), "Repeating * operand");
            }

            this.getValues().clear();
            for (TableColumn column: table.getColumns()){
                this.addValue(column.getAliases().getFirst());
            }
        }
        DbTable result = table.getColumn(this.getValue(0));
        for (int i = 1; i < this.getValues().size(); i++)
            result.extend(table.getColumn(this.getValue(i)));
        return result;
    }
}
