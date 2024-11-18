package ch.zhaw.pm3.aBrain.backend.configuration;

/**
 * This enum contains all database attributs.
 *
 * @author wilphi01
 * @version 1.0
 */
public enum ServerConfig {

    DEFAULT_SERVER_PORT("3306"),
    DEFAULT_SERVER_ADDRESS("server41.hostfactory.ch"),
    DEFAULT_DATABASE_NAME("aBrain"),
    DEFAULT_DATABASE_TIMEZONE("Europe/Zurich"),
    DEFAULT_DATABASE_USER("PM3_user"),
    DEFAULT_DATABASE_USER_PASSWORD("#aBrain2022");

    private final String value;

    ServerConfig(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
