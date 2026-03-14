package config;

/**
 * Supported environment types.
 */
public enum Environment {

    DEV("dev"),
    QA("qa"),
    STAGING("staging"),
    PROD("prod");

    private final String name;

    Environment(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Environment fromString(String value) {
        if (value == null || value.isBlank()) {
            return DEV;
        }
        for (Environment env : values()) {
            if (env.name.equalsIgnoreCase(value.trim())) {
                return env;
            }
        }
        return DEV;
    }
}
