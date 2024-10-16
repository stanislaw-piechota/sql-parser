package com.example.sql.db;

import java.util.List;

public class ColumnRow extends TableRecord<TableColumn> {
    final static String ROW_CHAR_WIDTH = "20";
    
    public ColumnRow(TableRecord<TableColumn> data){
        super(data);
    }

    public ColumnRow(List<TableColumn> data){
        super(data);
    }

    public ColumnRow(TableColumn data) {
        super(List.of(data));
    }

    public ColumnRow(){
        super();
    }

    public int getIndexByAlias(String alias) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).isAlias(alias)) {
                return i;
            }
        }
        return -1;
    }

    public ColumnRow join(ColumnRow other){
        for (TableColumn column: this){
            for (TableColumn otherColumn : other) {
                column.getAliases().remove(otherColumn.getName());
                otherColumn.getAliases().remove(column.getName());
            }
        }
        return new ColumnRow(super.join(other));
    }

    @Override
    public String toString(){
        return super.toString(true);
    }
}
