package config;

/**
 * Supported browser types for cross-browser testing.
 */
public enum BrowserType {

    CHROME("chrome"),
    FIREFOX("firefox"),
    EDGE("edge");

    private final String name;

    BrowserType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Resolves a browser type from string input (case-insensitive).
     * Falls back to CHROME if unrecognized.
     */
    public static BrowserType fromString(String value) {
        if (value == null || value.isBlank()) {
            return CHROME;
        }
        for (BrowserType type : values()) {
            if (type.name.equalsIgnoreCase(value.trim())) {
                return type;
            }
        }
        return CHROME;
    }
}
