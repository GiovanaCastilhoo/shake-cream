package com.shakecream.service;
import java.util.UUID;
import com.shakecream.model.ClientSession;

public class ClientService {

    public ClientSession createSession(String name, int table) {
        ClientSession session = new ClientSession();
        session.setSessionId(UUID.randomUUID().toString());
        session.setName(name);
        session.setTableNumber(table);
        return session;
    }
}