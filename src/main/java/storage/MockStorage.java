package storage;

import java.util.HashMap;
import java.util.Map;

public class MockStorage {
    private static final Map<String,String> userBase = new HashMap<>();

    static {
        userBase.put("admin","123");
        userBase.put("user","123");
    }

    public static boolean doesUserExist(String username, String password) {
        if(userBase.containsKey(username)) {
            return true;
        }
        return false;
    }

    public static void addUser(String username, String password) {
        userBase.put(username,password);
    }
}
