package com.example.sql.db;

import com.example.sql.exceptions.TableNotFoundError;
import java.io.IOException;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DbStorage {
    private String filename;
    private Map<String, DbTable> data = new TreeMap<>();

    public DbStorage(String filename) throws IOException, URISyntaxException {
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
        return new DbTable(this.data.get(tableName));
    }

    public void setTable(String tableName, DbTable newTable) throws TableNotFoundError {
        if (!this.data.containsKey(tableName))
            throw new TableNotFoundError(this.filename, tableName);
        this.data.replace(tableName, newTable);
    }

    public Map<String, DbTable> getData(){
        return this.data;
    }

    public String serialize(){
        String retval = "{";
        for (Map.Entry<String, DbTable> entry: data.entrySet()){
            retval += "\""+entry.getKey()+"\":"+entry.getValue().serialize()+",";
        }
        retval = retval.substring(0, retval.length()-1);
        retval += "}";
        return retval;
    }

    public void save() throws URISyntaxException, IOException {
        JsonParser.save(this.filename, this.serialize());
    }

    public static void main(String[] args) throws IOException, TableNotFoundError, URISyntaxException {
        DbStorage db = new DbStorage("db.json");
        DbTable students = db.getTable("students");
        System.out.println(students);
        students.add(new TableRecord<>(List.of("4", "Maja", "Nowak")));
        System.out.println(db.serialize());
    }
}
