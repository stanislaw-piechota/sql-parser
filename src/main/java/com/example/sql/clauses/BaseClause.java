package com.example.sql.clauses;

import java.util.List;
import java.util.ArrayList;

public class BaseClause {
    private String clause;
    private int priority;
    private List<String> values;
    private String[] nextClauses, prevClauses;

    public BaseClause(String clause, int priority, String[] prevClauses, String[] nextClauses) {
        this.clause = clause;
        this.priority = priority;
        this.nextClauses = nextClauses;
        this.prevClauses = prevClauses;
        this.values = new ArrayList<>();
    }

    public void setValues(List<String> values){
        this.values = values;
    }

    public List<String> getValues(){
        return this.values;
    }

    public String getValue(int index){
        return this.values.get(index);
    }

    public void addValue(String value){
        this.values.add(value);
    }

    public int getPriority(){
        return this.priority;
    }

    public static String stripWrapper(String val) {
        return val.substring(1, val.length() - 1);
    }

    public String getClause(){
        return this.clause;
    }

    public String toString(){
        return this.clause + " " + this.values.toString();
    }

    public boolean isEmpty(){
        return this.values.isEmpty();
    }

    private static boolean arrayContainsClause(String[] array, String clause){
        for (String el: array){
            if (el == null)
                return clause == null ? true : false;
            else if (el.equals(clause))
                return true;
        }
        return false;
    }

    public boolean isNextClause(String clause) {
        return arrayContainsClause(this.nextClauses, clause);
    }

    public boolean isPreviousClause(String clause) {
        return arrayContainsClause(this.prevClauses, clause);
    }

    public boolean checkValuesRequired(){
        return !this.values.isEmpty();
    }
}
