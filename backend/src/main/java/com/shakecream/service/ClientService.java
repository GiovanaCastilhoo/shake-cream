package com.shakecream.service;
import java.util.UUID;
import com.shakecream.model.ClientSession;
import com.shakecream.util.SessionUtil;
import com.shakecream.model.Client;

public class ClientService {

    public ClientSession createSession(String name, int table) {
            Client client = new Client(name, table);

            String sessionId = UUID.randomUUID().toString();

            SessionUtil.saveSession(sessionId, client);

            ClientSession clientSession = new ClientSession();
            clientSession.setSessionId(sessionId);
            clientSession.setName(name);
            clientSession.setTableNumber(table);

            return clientSession;
    }
}