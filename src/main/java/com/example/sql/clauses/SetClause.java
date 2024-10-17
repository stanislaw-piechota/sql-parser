package com.example.sql.clauses;

import java.util.regex.Pattern;

import com.example.sql.db.DbStorage;
import com.example.sql.db.DbTable;
import com.example.sql.db.TableRecord;
import com.example.sql.exceptions.ColumnNotFoundError;
import com.example.sql.exceptions.InvalidSyntaxError;
import com.example.sql.exceptions.InvalidValueError;
import com.example.sql.exceptions.TableNotFoundError;
import java.io.IOException;
import java.net.URISyntaxException;

public class SetClause extends AssignmentClause {
    final static String[] NEXT_CLAUSES = { "WHERE" };
    final static String[] PREV_CLAUSES = { "UPDATE" };
    final static Pattern ALLOWED_OP_REGEX = Pattern.compile("=");

    public SetClause(){
        super("SET", 5, PREV_CLAUSES, NEXT_CLAUSES, ALLOWED_OP_REGEX);
    }
    
    @Override
    public DbTable execute(DbStorage db, DbTable table) 
        throws ColumnNotFoundError, InvalidSyntaxError, InvalidValueError, TableNotFoundError, URISyntaxException, IOException {
        this.setExecuteParams(table, String.join("", this.getValues()));
        if (isString(this.v1) || (this.v1Index = table.getColumns().getIndexByAlias(this.v1)) < 0) {
            throw new ColumnNotFoundError("<result_table>", this.v1);
        }
        if (!isString(this.v2)) {
            throw new InvalidValueError(this.getClause(), this.v2);
        }

        int count = 0;
        DbTable result = new DbTable(db.getTable(table.getName()));
        for (TableRecord<String> toUpdate: table){
            for (TableRecord<String> record: result){
                if (toUpdate.equals(record)){
                    record.set(this.v1Index, stripWrapper(this.v2));
                    toUpdate.set(this.v1Index, stripWrapper(this.v2));
                    count++;
                }
            }
        }
        db.setTable(table.getName(), result);
        db.save();
        System.out.println("Successfully updated "+count+" records");
        return table;
    }
}
