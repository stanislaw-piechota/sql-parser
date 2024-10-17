package com.example.sql.clauses;

import java.util.Arrays;
import java.util.List;

import com.example.sql.db.DbStorage;
import com.example.sql.db.DbTable;
import com.example.sql.db.TableRecord;
import com.example.sql.exceptions.InvalidSyntaxError;
import com.example.sql.exceptions.InvalidValueError;
import com.example.sql.exceptions.TableNotFoundError;

public class ValuesClause extends BaseClause implements SqlClause {
    final static String[] NEXT_CLAUSES = { null };
    final static String[] PREV_CLAUSES = { "INTO" };

    public ValuesClause(){
        super("VALUES", 1, PREV_CLAUSES, NEXT_CLAUSES);
    }

    private static boolean isString(String val) {
        return val.startsWith("\"") ? (val.endsWith("\"") ? true : false) : false;
    }

    public DbTable execute(DbStorage db, DbTable table) throws TableNotFoundError, InvalidValueError, InvalidSyntaxError {
        String valuesString = String.join(",", this.getValues());
        if (!valuesString.startsWith("(") || !valuesString.endsWith(")"))
            throw new InvalidSyntaxError(this.getClause(), "No parethesis around values");
        valuesString = stripWrapper(valuesString);

        List<String> insertValues = Arrays.asList(valuesString.split(","));
        if (insertValues.size() < 1)
            throw new InvalidSyntaxError(this.getClause(), "Empty insert values");
        for (int i=0; i<insertValues.size(); i++) {
            if (!isString(insertValues.get(i)))
                throw new InvalidValueError(this.getClause(), "One of the values is not string");
            insertValues.set(i, stripWrapper(insertValues.get(i)));
        }

        table = new DbTable();
        table.add(new TableRecord<String>(insertValues));
        return table;
    }
}
