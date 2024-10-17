package com.example.sql.clauses;

import java.util.regex.Pattern;

import com.example.sql.db.DbStorage;
import com.example.sql.db.DbTable;
import com.example.sql.exceptions.ColumnNotFoundError;
import com.example.sql.exceptions.InvalidSyntaxError;
import com.example.sql.exceptions.InvalidValueError;
import com.example.sql.exceptions.TableNotFoundError;
import java.io.IOException;
import java.net.URISyntaxException;

public class OnClause extends AssignmentClause {
    final static String[] NEXT_CLAUSES = { "WHERE", null };
    final static String[] PREV_CLAUSES = { "JOIN" };
    final static Pattern ALLOWED_OP_REGEX = Pattern.compile("=");

    public OnClause(){
        super("ON", 3, PREV_CLAUSES, NEXT_CLAUSES, ALLOWED_OP_REGEX);
    }

    @Override
    public boolean checkValuesRequired() {
        return this.getValues().size() >= 1 && this.getValues().size() <= 3;
    }

    @Override
    public DbTable execute(DbStorage db, DbTable table)
        throws ColumnNotFoundError, InvalidSyntaxError, InvalidValueError, TableNotFoundError, URISyntaxException, IOException {
        this.setExecuteParams(table, String.join("", this.getValues()));
        if (isString(this.v1) || isString(this.v2))
            throw new InvalidValueError(this.getClause(), "One or two operands are strings");
        return super.execute(db, table);
    }
}
