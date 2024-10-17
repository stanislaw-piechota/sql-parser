package com.example.sql.db;

import java.util.List;

public class ColumnRow extends TableRecord<TableColumn> {
    final static String ROW_CHAR_WIDTH = "20";
    
    public ColumnRow(TableRecord<TableColumn> data){
        super();
        super.addAll(data);
    }

    public ColumnRow(List<TableColumn> data){
        super();
        super.addAll(data);
    }

    public ColumnRow(TableColumn data) {
        super();
        super.add(data);
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
        ColumnRow newColumn = new ColumnRow();
        for (TableColumn thisColumn: this)
            newColumn.add(new TableColumn(thisColumn));
        for (TableColumn otherColumn : other)
            newColumn.add(new TableColumn(otherColumn));
        for (int i=0; i<newColumn.size()-1; i++){
            for (int j=i+1; j<newColumn.size(); j++){
                TableColumn first = newColumn.get(i), second = newColumn.get(j);
                first.getAliases().remove(second.getName());
                second.getAliases().remove(first.getName());
            }
        }
        return newColumn;
    }

    @Override
    public String toString(){
        return super.toString(true);
    }

    @Override
    public String serialize() {
        String retval = "[";
        for (int i = 0; i < this.size() - 1; i++)
            retval += "\"" + this.get(i).serialize() + "\",";
        retval += "\"" + this.getLast().serialize() + "\"]";
        return retval;
    }
}
