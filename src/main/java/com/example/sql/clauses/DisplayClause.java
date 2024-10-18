package com.example.sql.clauses;

import com.example.sql.db.DbStorage;
import com.example.sql.db.DbTable;
import com.example.sql.db.TableColumn;
import com.example.sql.exceptions.TableNotFoundError;
import java.util.Map;

public class DisplayClause extends BaseClause implements SqlClause {
    final static String[] NEXT_CLAUSES = { null };
    final static String[] PREV_CLAUSES = { null };

    public DisplayClause(){
        super("DISPLAY", 5, PREV_CLAUSES, NEXT_CLAUSES);
    }

    @Override
    public boolean checkValuesRequired(){
        return this.getValues().size() == 0;
    }

    public DbTable execute(DbStorage db, DbTable table)
        throws TableNotFoundError {
        
        for (Map.Entry<String, DbTable> entry: db.getData().entrySet()){
            System.out.println(entry.getKey());
            for (TableColumn column: entry.getValue().getColumns()){
                System.out.println("    "+column.getName());
            }
        }

        return table;
    }
}
