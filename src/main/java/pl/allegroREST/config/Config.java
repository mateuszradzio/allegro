package pl.allegroREST.config;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Config {

    /**
     * Loads REST API Authorization properties and sets them as System properties
     */
    public static void initAuthorizationProperties() {
        ConfigReader.getAuthorizationProperties().forEach((key, value) -> {
            log.info("Setting authorization property: {}={}", key, value);
            System.setProperty(key, value);
        });
    }

    /**
     * Loads tests properties and sets them as System properties
     */
    public static void initTestsProperties() {
        ConfigReader.getTestProperties().forEach((key, value) -> {
            log.info("Setting test property: {}={}", key, value);
            System.setProperty(key, value);
        });
    }

}
