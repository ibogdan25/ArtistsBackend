package utils;

import java.util.HashMap;
import java.util.Map;

public class Properties {
    final Map<String, String> propertiesMap;

    public Properties() {
        propertiesMap = new HashMap<>();
    }

    public void add(final String name, final String value) {
        propertiesMap.put(name, value);
    }

    public String getValue(final String name) {
        return propertiesMap.get(name);
    }

    public boolean contains(final String name) {
        return propertiesMap.containsKey(name);
    }
}
