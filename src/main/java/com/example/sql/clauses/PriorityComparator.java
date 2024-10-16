package com.example.sql.clauses;

import java.util.Comparator;

public class PriorityComparator implements Comparator<SqlClause> {
    public int compare(SqlClause c1, SqlClause c2){
        return Integer.compare(c1.getPriority(), c2.getPriority());
    }
}
