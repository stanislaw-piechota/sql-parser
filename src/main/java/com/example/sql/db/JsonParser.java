package com.example.sql.db;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.List;
import java.io.IOException;

public class JsonParser {
    public static Map<String, List<List<String>>> parseFile(String filename) throws IOException {
        if (!filename.endsWith(".json")){
            throw new IOException("File not in .json format");
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(JsonParser.class.getResource("/"+filename), new TypeReference<Map<String, List<List<String>>>>(){});
    }
}
