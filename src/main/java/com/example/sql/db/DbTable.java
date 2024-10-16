package com.example.sql.db;

import java.util.ArrayList;
import java.util.List;

import com.example.sql.exceptions.ColumnNotFoundError;

public class DbTable extends ArrayList<TableRecord<String>> {
    private String name = "";
    private ColumnRow columns = new ColumnRow();

    public DbTable(){
        super();
    }

    public DbTable(String name, List<TableRecord<String>> table){
        super(table);
        this.name = name;
        for (String column: table.get(0))
            this.columns.add(new TableColumn(column, List.of(column, name+"."+column)));

        this.remove(0);
    }

    public DbTable(List<List<String>> table, String name){
        super();
        this.name = name;
        for (String column: table.get(0))
            this.columns.add(new TableColumn(column, List.of(column, name+"."+column)));
        table.remove(0);
        for (List<String> row: table)
            this.add(new TableRecord<>(row));
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public ColumnRow getColumns(){
        return this.columns;
    }

    public void setColumns(ColumnRow row) {
        this.columns = row;
    }

    public void setColumns(TableRecord<TableColumn> row) {
        this.columns = new ColumnRow(row);
    }

    public void setColumns(List<String> row){
        this.columns.clear();
        for (String column : row)
            this.columns.add(new TableColumn(column, column));
    }

    public void setColumns(String row){
        this.columns = new ColumnRow(new TableColumn(row, row));
    }

    public void setColumns(TableColumn column){
        this.columns.clear();
        this.columns.add(column);
    }

    public DbTable merge(DbTable other){
        DbTable result = new DbTable();
        result.setColumns(this.columns.join(other.columns));
        for (TableRecord<String> row : this){
            for (TableRecord<String> otherRow : other){
                result.add(row.join(otherRow));
            }
        }
        return result;
    }

    public void extend(DbTable other){
        this.columns = new ColumnRow(this.columns.join(other.columns));
        for (int i=0; i<this.size(); i++){
            this.get(i).addAll(other.get(i));
        }
    }

    public DbTable getColumn(String column) throws ColumnNotFoundError{
        int columnIndex = this.columns.getIndexByAlias(column);
        if (columnIndex < 0){
            throw new ColumnNotFoundError(column);
        }

        DbTable result = new DbTable();
        result.setColumns(this.getColumns().get(columnIndex));
        for (TableRecord<String> row: this)
            result.add(new TableRecord<String>(row.get(columnIndex)));
        return result;
    }

    public String toString(){
        String retval = this.columns.toString(true);
        for (TableRecord<String> row: this)
            retval += row.toString();
        return retval;
    }
}
