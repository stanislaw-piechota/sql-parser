package com.example.sql.clauses;

import java.util.regex.Matcher;
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

public class AssignmentClause extends BaseClause implements SqlClause {
    final String[] NEXT_CLAUSES = { "WHERE", null };
    final String[] PREV_CLAUSES = { "JOIN" };
    final Pattern ALLOWED_OP_REGEX;
    public String operation, v1, v2;
    public int v1Index = -1, v2Index = -1;
    public Comparator cmp = (v1, v2) -> v1.equals(v2);

    public AssignmentClause(String clause, int priority, String[] prevClauses, String[] nextClauses, Pattern allowedOpRegex) 
    {
        super(clause, priority, prevClauses, nextClauses);
        this.ALLOWED_OP_REGEX = allowedOpRegex;
    }

    public interface Comparator {
        boolean run(String v1, String v2);
    }

    public boolean cmpStringToString(String v1, String v2, Comparator comparator) {
        return comparator.run(v1, v2);
    }

    @Override
    public boolean checkValuesRequired() {
        return this.getValues().size() >= 1 && this.getValues().size() <= 3;
    }

    public static boolean isString(String val) {
        return val.startsWith("\"") ? (val.endsWith("\"") ? true : false) : false;
    }

    public void setExecuteParams(DbTable table, String valuesString)
        throws IndexOutOfBoundsException, ColumnNotFoundError, InvalidSyntaxError, InvalidValueError {
        Matcher matcher = ALLOWED_OP_REGEX.matcher(valuesString);
        if (!matcher.find()) {
            throw new InvalidSyntaxError(this.getClause(), "Unsupported operation sign");
        }
        this.operation = matcher.group(0);
        String[] values = valuesString.split(this.operation);
        this.v1 = values[0].strip();
        this.v2 = values[1].strip();

        if (!isString(this.v1) && (this.v1Index = table.getColumns().getIndexByAlias(this.v1)) < 0) {
            throw new ColumnNotFoundError("<result_table>", this.v1);
        }
        if (!isString(this.v2) && (this.v2Index = table.getColumns().getIndexByAlias(this.v2)) < 0) {
            throw new ColumnNotFoundError("<result_table>", this.v2);
        }
    }

    public DbTable execute(DbStorage db, DbTable table)
        throws ColumnNotFoundError, InvalidSyntaxError, InvalidValueError, TableNotFoundError, URISyntaxException, IOException {
        DbTable result = new DbTable(table.getName());
        result.setColumns(table.getColumns());

        for (TableRecord<String> row: table){
            String cmpArg1 = v1Index >= 0 ? "\""+row.get(v1Index)+"\"" : this.v1;
            String cmpArg2 = v2Index >= 0 ? "\""+row.get(v2Index)+"\"" : this.v2;
            if (cmpStringToString(cmpArg1, cmpArg2, this.cmp))
                result.add(row);
        }
        return result;
    }
}
