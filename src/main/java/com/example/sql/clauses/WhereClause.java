package com.example.sql.clauses;

import java.util.Map;
import java.util.regex.Pattern;

import com.example.sql.db.DbStorage;
import com.example.sql.db.DbTable;
import com.example.sql.exceptions.ColumnNotFoundError;
import com.example.sql.exceptions.InvalidSyntaxError;
import com.example.sql.exceptions.InvalidValueError;
import com.example.sql.exceptions.TableNotFoundError;
import java.io.IOException;
import java.net.URISyntaxException;

public class WhereClause extends AssignmentClause {
    final static String[] NEXT_CLAUSES = { null };
    final static String[] PREV_CLAUSES = { "FROM", "ON", "SET" };
    final static Pattern ALLOWED_OP_REGEX = Pattern.compile("([><!]?=)|[<>]", Pattern.CASE_INSENSITIVE);
    final static Map<String, Comparator> ALLOWED_COMPARISONS = Map.of(
        "=", (v1, v2) -> v1.equals(v2),
        ">=", (v1, v2) -> v1.compareTo(v2) >= 0,
        "<=", (v1, v2) -> v1.compareTo(v2) <= 0,
        ">", (v1, v2) -> v1.compareTo(v2) > 0,
        "<", (v1, v2) -> v1.compareTo(v2) < 0,
        "!=", (v1, v2) -> !v1.equals(v2)
    );

    public WhereClause(){
        super("WHERE", 4, PREV_CLAUSES, NEXT_CLAUSES, ALLOWED_OP_REGEX);
    }

    @Override
    public DbTable execute(DbStorage db, DbTable table)
        throws ColumnNotFoundError, InvalidSyntaxError, InvalidValueError, TableNotFoundError, URISyntaxException, IOException {
        this.setExecuteParams(table, String.join("", this.getValues()));
        super.cmp = ALLOWED_COMPARISONS.get(super.operation);
        return super.execute(db, table);
    }
}
