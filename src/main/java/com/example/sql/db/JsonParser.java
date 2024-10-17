package com.example.sql.db;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonParser {
    public static Map<String, List<List<String>>> parseFile(String filename) throws IOException, URISyntaxException {
        if (!filename.endsWith(".json")){
            throw new IOException("File not in .json format");
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(filename).toURI().toURL(), new TypeReference<Map<String, List<List<String>>>>(){});
    }

    public static void save(String filename, String content) throws URISyntaxException, IOException {
        Files.writeString(Paths.get(new File(filename).toURI()), content);
    }
}
