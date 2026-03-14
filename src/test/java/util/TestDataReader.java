package util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.FrameworkException;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Utility for reading test data from JSON files in the classpath.
 * Supports both generic Map-based and type-safe POJO deserialization.
 */
public final class TestDataReader {

    private static final ObjectMapper mapper = new ObjectMapper();

    private TestDataReader() {
        // utility class
    }

    /**
     * Reads a JSON array file into a List of Maps.
     */
    public static List<Map<String, Object>> readJson(String resourcePath) {
        try (InputStream is = getResourceStream(resourcePath)) {
            return mapper.readValue(is, new TypeReference<>() {});
        } catch (Exception e) {
            throw new FrameworkException("Failed to read test data: " + resourcePath, e);
        }
    }

    /**
     * Reads a JSON file and deserializes into a specific type.
     * Example: {@code readJson("testdata/user.json", User.class)}
     */
    public static <T> T readJson(String resourcePath, Class<T> type) {
        try (InputStream is = getResourceStream(resourcePath)) {
            return mapper.readValue(is, type);
        } catch (Exception e) {
            throw new FrameworkException("Failed to read test data: " + resourcePath, e);
        }
    }

    /**
     * Reads a JSON array file and deserializes into a list of specific types.
     * Example: {@code readJsonList("testdata/users.json", User.class)}
     */
    public static <T> List<T> readJsonList(String resourcePath, Class<T> elementType) {
        try (InputStream is = getResourceStream(resourcePath)) {
            return mapper.readValue(is,
                    mapper.getTypeFactory().constructCollectionType(List.class, elementType));
        } catch (Exception e) {
            throw new FrameworkException("Failed to read test data list: " + resourcePath, e);
        }
    }

    private static InputStream getResourceStream(String resourcePath) {
        InputStream is = TestDataReader.class.getClassLoader().getResourceAsStream(resourcePath);
        if (is == null) {
            throw new FrameworkException("Test data file not found in resources: " + resourcePath);
        }
        return is;
    }
}
