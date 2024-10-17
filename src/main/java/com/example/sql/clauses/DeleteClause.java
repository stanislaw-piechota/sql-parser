package com.example.sql.clauses;

import com.example.sql.db.DbStorage;
import com.example.sql.db.DbTable;
import com.example.sql.db.TableRecord;
import com.example.sql.exceptions.InvalidSyntaxError;
import com.example.sql.exceptions.InvalidValueError;
import com.example.sql.exceptions.TableNotFoundError;
import java.net.URISyntaxException;
import java.io.IOException;

public class DeleteClause extends BaseClause implements SqlClause {
    final static String[] NEXT_CLAUSES = { "FROM" };
    final static String[] PREV_CLAUSES = { null };

    public DeleteClause(){
        super("DELETE", 5, PREV_CLAUSES, NEXT_CLAUSES);
    }

    @Override
    public boolean checkValuesRequired(){
        return this.getValues().size() == 0;
    }

    public DbTable execute(DbStorage db, DbTable table)
        throws TableNotFoundError, InvalidValueError, InvalidSyntaxError, IOException, URISyntaxException {
        if (table.getName().isEmpty() || table.getName().equals("<result_table>"))
            throw new InvalidValueError(this.getClause(), "Table is not single (join, multi-from)");

        int count = 0, i=0;
        DbTable result = db.getTable(table.getName());
        for (TableRecord<String> toUpdate: table){
            while (i<result.size()){
                if (toUpdate.equals(result.get(i))){
                    result.remove(i);
                    count++;
                    continue;
                }
                i++;
            }
        }

        db.setTable(table.getName(), result);
        db.save();
        System.out.println("Successfully deleted "+count+" records");
        return table;
    }
}
