package com.example.sql.db;

import java.util.List;

public class TableColumn {
    private String name;
    private TableRecord<String> aliases;

    public TableColumn(String name, List<String> aliases){
        this.name = name;
        this.aliases = new TableRecord<>(aliases);
    }

    public TableColumn(String name, String alias) {
        this.name = name;
        this.aliases = new TableRecord<>(alias);
    }

    public boolean isAlias(String alias){
        return this.aliases.contains(alias);
    }

    public TableRecord<String> getAliases(){
        return this.aliases;
    }

    public void addAlias(String alias){
        if (!this.aliases.contains(alias)){
            this.aliases.add(alias);
        }
    }

    public String getName(){
        return this.name;
    }

    public String toString(){
        return this.aliases.getFirst();
    }
}
