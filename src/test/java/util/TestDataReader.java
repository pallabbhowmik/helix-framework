package util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class TestDataReader {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Map<String, Object>> readJson(String filePath) {
        try (InputStream is = TestDataReader.class.getClassLoader().getResourceAsStream(filePath)) {
            if (is == null) {
                throw new RuntimeException("Test data file not found in resources: " + filePath);
            }
            return mapper.readValue(is, new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to read test data: " + filePath, e);
        }
    }
}
