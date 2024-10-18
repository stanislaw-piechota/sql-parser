package com.example.sql;

import java.util.Map;
import java.util.Map.Entry;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.example.sql.clauses.*;
import com.example.sql.db.DbStorage;
import com.example.sql.db.DbTable;
import com.example.sql.exceptions.ColumnNotFoundError;
import com.example.sql.exceptions.InvalidSyntaxError;
import com.example.sql.exceptions.InvalidValueError;
import com.example.sql.exceptions.TableNotFoundError;

public class SqlQuery {
    final static String SPACE_CHARS = "[ ,]+";
    private DbStorage db;
    private String command;
    private Map<String, SqlClause> clauses = Map.ofEntries(
        entry("FROM", new FromClause()),
        entry("JOIN", new JoinClause()),
        entry("ON", new OnClause()),
        entry("WHERE", new WhereClause()),
        entry("SELECT", new SelectClause()),
        entry("INSERT", new InsertClause()),
        entry("INTO", new IntoClause()),
        entry("VALUES", new ValuesClause()),
        entry("UPDATE", new UpdateClause()),
        entry("SET", new SetClause()),
        entry("DELETE", new DeleteClause()),
        entry("DISPLAY", new DisplayClause())
    );
    private List<SqlClause> sortedClauses = new ArrayList<>();
    private DbTable result;

    public SqlQuery(String command, DbStorage db) throws InvalidSyntaxError, InvalidValueError {
        this.command = command;
        this.db = db;
        this.result = new DbTable();
        this.parseClauses();
        this.sortClauses();
    }

    private static <K, V> Entry<K, V> entry(K key, V value) {
        return Map.entry(key, value);
    }

    private void parseClauses() throws InvalidSyntaxError, InvalidValueError {
        String[] tokens = this.command.split(SPACE_CHARS);
        String token = tokens[0].toUpperCase(), prevClause = null;
        SqlClause currentClause;
        
        if ((currentClause = clauses.get(token)) == null)
            throw new InvalidSyntaxError(token, "Invalid clause");
        if (!currentClause.isPreviousClause(null))
            throw new InvalidSyntaxError(currentClause.getClause());

        for (int i=1; i < tokens.length; i++){
            token = tokens[i];
            if (clauses.containsKey(token.toUpperCase())){
                token = token.toUpperCase();
                if (!currentClause.isPreviousClause(prevClause) || !currentClause.isNextClause(token))
                    throw new InvalidSyntaxError(currentClause.getClause());
                if (!currentClause.checkValuesRequired())
                    throw new InvalidValueError(currentClause.getClause());
                prevClause = currentClause.getClause();
                this.sortedClauses.add(currentClause);
                currentClause = clauses.get(token);
                continue;
            }
            currentClause.addValue(token);
        }

        this.sortedClauses.add(currentClause);
        if (!currentClause.isNextClause(null))
            throw new InvalidSyntaxError(currentClause.getClause());
        if (!currentClause.checkValuesRequired())
            throw new InvalidValueError(currentClause.getClause());
    }

    private void sortClauses() throws InvalidSyntaxError {
        sortedClauses.sort(new PriorityComparator());
    }

    public long run() throws TableNotFoundError, InvalidSyntaxError, InvalidValueError, ColumnNotFoundError, IOException, URISyntaxException {
        long startTime = System.currentTimeMillis();
        for (SqlClause clause: this.sortedClauses)
            this.result = clause.execute(this.db, this.result);
        return System.currentTimeMillis() - startTime;
    }

    public DbTable getResult(){
        return this.result;
    }
}
