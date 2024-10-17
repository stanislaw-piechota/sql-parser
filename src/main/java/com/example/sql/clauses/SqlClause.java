package com.example.sql.clauses;

import com.example.sql.db.DbStorage;
import com.example.sql.db.DbTable;
import com.example.sql.exceptions.ColumnNotFoundError;
import com.example.sql.exceptions.InvalidSyntaxError;
import com.example.sql.exceptions.InvalidValueError;
import com.example.sql.exceptions.TableNotFoundError;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface SqlClause {
    abstract public void setValues(List<String> values);
    abstract public void addValue(String value);
    abstract public String toString();
    abstract public DbTable execute(DbStorage db, DbTable table) 
        throws TableNotFoundError, InvalidSyntaxError, InvalidValueError, ColumnNotFoundError, URISyntaxException, IOException;
    abstract public boolean isEmpty();
    abstract public int getPriority();
    abstract public String getClause();
    abstract public boolean isPreviousClause(String clause);
    abstract public boolean isNextClause(String clause);
    abstract public boolean checkValuesRequired();
}
