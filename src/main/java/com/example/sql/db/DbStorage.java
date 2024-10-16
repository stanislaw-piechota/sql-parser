package com.example.sql.db;

import com.example.sql.exceptions.TableNotFoundError;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DbStorage {
    private String filename;
    private Map<String, DbTable> data = new TreeMap<>();

    public DbStorage(String filename) throws IOException {
        this.filename = filename;

        for (Map.Entry<String, List<List<String>>> table:  JsonParser.parseFile(filename).entrySet()){
            this.data.put(table.getKey(), new DbTable(table.getValue(), table.getKey()));
        }
    }

    public String getFilename(){
        return this.filename;
    }

    public DbTable getTable(String tableName) throws TableNotFoundError {
        if (!this.data.containsKey(tableName))
            throw new TableNotFoundError(this.filename, tableName);
        return this.data.get(tableName);
    }

    public static void main(String[] args) throws IOException, TableNotFoundError {
        DbStorage db = new DbStorage("db.json");
        DbTable students = db.getTable("students");
        DbTable ids = db.getTable("ids");
        System.out.println(students.toString());
        System.out.println(ids.toString());
        System.out.println(students.merge(ids).toString());
    }
}
