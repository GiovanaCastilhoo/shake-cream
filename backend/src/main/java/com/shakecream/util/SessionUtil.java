package com.shakecream.util;

import java.util.HashMap;
import java.util.Map;
import com.shakecream.model.Client;

public class SessionUtil {

    private static Map<String, Client> sessions = new HashMap<>();

    public static void saveSession(String sessionId, Client client) {
        sessions.put(sessionId, client);
    }

    public static Client getClient(String sessionId) {
        return sessions.get(sessionId);
    }
}