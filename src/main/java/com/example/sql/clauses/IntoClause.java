package com.example.sql.clauses;

import com.example.sql.db.DbStorage;
import com.example.sql.db.DbTable;
import com.example.sql.db.TableRecord;
import com.example.sql.exceptions.ColumnNotFoundError;
import com.example.sql.exceptions.InvalidSyntaxError;
import com.example.sql.exceptions.InvalidValueError;
import com.example.sql.exceptions.TableNotFoundError;

public class IntoClause extends BaseClause implements SqlClause {
    final static String[] NEXT_CLAUSES = { "VALUES" };
    final static String[] PREV_CLAUSES = { "INSERT" };

    public IntoClause(){
        super("INTO", 3, PREV_CLAUSES, NEXT_CLAUSES);
    }

    @Override
    public boolean checkValuesRequired(){
        return this.getValues().size() >= 1;
    }

    public DbTable execute(DbStorage db, DbTable table)
        throws TableNotFoundError, InvalidValueError, InvalidSyntaxError, ColumnNotFoundError, IndexOutOfBoundsException {
        DbTable result = db.getTable(this.getValue(0));
        this.getValues().removeFirst();

        String columnString = String.join(",", this.getValues());
        String columnArray[];
        if (!columnString.isEmpty()) {
            if (!columnString.startsWith("(") || !columnString.endsWith(")"))
                throw new InvalidSyntaxError(this.getClause(), "No wrapping () around values");
            columnString = columnString.substring(1, columnString.length()-1);
            columnArray = columnString.split(",");
        } else {
            columnArray = new String[result.get(0).size()];
            for (int i=0; i<result.getColumns().size(); i++)
                columnArray[i] = result.getColumns().get(i).getName();
        }

        if (columnArray.length != table.get(0).size())
            throw new InvalidSyntaxError(this.getClause(), "Different number of columns and values");
        TableRecord<String> newRecord = new TableRecord<>();
        int columnIndex = -1, valuesCounter = 0;
        for (int i=0; i<result.getColumns().size(); i++) newRecord.add("");
        for (String column: columnArray){
            if ((columnIndex = result.getColumns().getIndexByAlias(column)) < 0)
                throw new ColumnNotFoundError(db.getFilename(), table.getName(), column);
            newRecord.set(columnIndex, table.get(0).get(valuesCounter++));
        }
        result.add(newRecord);
        return result;
    }
}
