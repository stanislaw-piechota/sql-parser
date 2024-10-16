package com.example.sql.db;

import java.util.ArrayList;
import java.util.List;

public class TableRecord<T> extends ArrayList<T> {
    final static String ROW_CHAR_WIDTH = "20";
    
    public TableRecord(TableRecord<T> data){
        super(data);
    }

    public TableRecord(List<T> data){
        super(data);
    }

    public TableRecord(T data) {
        super(List.of(data));
    }

    public TableRecord(){
        super();
    }

    public TableRecord<T> join(TableRecord<T> other){
        TableRecord<T> result = new TableRecord<>();
        result.addAll(this);
        result.addAll(other);
        return result;
    }

    public String toString(boolean separator){
        String retval = "|";
        int increment = 3 + Integer.parseInt(ROW_CHAR_WIDTH);
        for (T value: this)
            retval += String.format(" %-"+ROW_CHAR_WIDTH+"s |", value.toString());
        retval += "\n";

        if (separator) {
            retval += new String(new char[1 + this.size()*increment]).replace('\0', '-');
            retval += "\n";
        }

        return retval;
    }

    public String toString(){
        return this.toString(false);
    }
}
