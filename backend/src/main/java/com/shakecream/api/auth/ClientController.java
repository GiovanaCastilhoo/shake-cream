package com.shakecream.api.auth;

import static spark.Spark.*;

import com.shakecream.model.ClientLoginRequest;
import com.shakecream.model.ClientSession;
import com.shakecream.service.ClientService;
import com.google.gson.*;

public class ClientController {
  private static final Gson gson = new Gson();

  public static void init(ClientService clientService) {
    post("/client/login", (req, res) -> {

      ClientLoginRequest body = gson.fromJson(req.body(), ClientLoginRequest.class);

      ClientSession session = clientService.createSession(
          body.getName(),
          body.getTableNumber());
      
          System.out.println("SESSION CRIADA: " + session.getSessionId());
      return gson.toJson(session);
    });
  }
}
