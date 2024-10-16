package com.example.sql.clauses;

import java.util.function.Predicate;

public class EmptyClausePredicate implements Predicate<SqlClause> {
    @Override
    public boolean test(SqlClause arg0) {
        return arg0.isEmpty();
    }
}
