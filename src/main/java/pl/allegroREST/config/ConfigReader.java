package pl.allegroREST.config;

import lombok.extern.slf4j.Slf4j;

import java.util.Properties;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ConfigReader {
    private static final String AUTHORIZATION_PREFIX = "authorization.";
    private static final String TEST_PREFIX = "test_properties.";

    private static final Map<String, String> configuration = createConfiguration();

    /**
     * Loads authorization properties from configuration that starts with AUTHORIZATION_PREFIX
     *
     * @return Map with key-value pairs of authorization properties
     */
    public static Map<String, String> getAuthorizationProperties() {
        Map<String, String> authorizationProperties = new HashMap<>();
        for (Map.Entry<String, String> entry : configuration.entrySet()) {
            if (entry.getKey().startsWith(AUTHORIZATION_PREFIX)) {
                authorizationProperties.put(entry.getKey(), entry.getValue());
            }
        }
        return authorizationProperties;
    }

    /**
     * Loads test properties from configuration that starts with TEST_PREFIX
     *
     * @return Map with key-value pairs of test properties
     */
    public static Map<String, String> getTestProperties() {
        Map<String, String> testProperties = new HashMap<>();
        for (Map.Entry<String, String> entry : configuration.entrySet()) {
            if (entry.getKey().startsWith(TEST_PREFIX)) {
                testProperties.put(entry.getKey(), entry.getValue());
            }
        }
        return testProperties;
    }

    /**
     * Loads all properties from configuration file and converts it into Map
     *
     * @return Map with key-value pairs of properties
     */
    private static Map<String, String> createConfiguration() {
        final Map<String, String> configuration = new HashMap<>();

        loadConfigurationFile("configuration.properties").forEach((key, value) -> configuration.put((String) key, (String) value));

        return configuration;
    }

    /**
     * Loads all properties from file
     *
     * @param filename path to configuration file in resources
     * @return set of properties
     */
    private static Properties loadConfigurationFile(String filename) {
        try {
            return PropertiesLoaderUtils.loadAllProperties(filename);
        } catch (IOException exception) {
            log.error("Could not load configuration from file: {}", filename);
            throw new UncheckedIOException(exception);
        }
    }

    /**
     * Get value of property from configuration. Configuration need to bede initialized first!
     *
     * @param name full name of property e.g. "authorization.email"
     * @return String value of property
     */
    public static String getValue(String name) {
        return configuration.get(name);
    }

    /**
     * Get value of property from configuration. Configuration need to bede initialized first!
     *
     * @param name of authorization property e.g. "url"
     * @return String value of property
     */
    public static String getAuthorizationPropertyValue(String name) {
        return getValue(AUTHORIZATION_PREFIX + name);
    }

    /**
     * Get value of property from configuration. Configuration need to bede initialized first!
     *
     * @param name of test property e.g. "update_data"
     * @return String value of property
     */
    public static String getTestPropertyValue(String name) {
        return getValue(TEST_PREFIX + name);
    }
}
