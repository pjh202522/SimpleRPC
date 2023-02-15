package com.pjh.register;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yueyinghaibao
 */
public class LocalRegister {

    private static Map<String, Class> map = new HashMap<>();

    public static void register(String interfaceName, String version, Class implClass) {
        map.put(interfaceName + version, implClass);
    }

    public static Class get(String interfaceName, String version) {
        return map.get(interfaceName + version);
    }
}
