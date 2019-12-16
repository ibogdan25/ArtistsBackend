package utils;

public class ServerContext {
    private static Properties properties;

    public ServerContext() {

    }

    public static Properties getProperties() {
        return properties;
    }

    public static void setProperties(Properties properties) {
        ServerContext.properties = properties;
    }
}
