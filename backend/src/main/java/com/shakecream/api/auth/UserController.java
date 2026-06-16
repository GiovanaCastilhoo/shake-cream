package com.shakecream.api.auth;

import static spark.Spark.*;
import com.shakecream.service.AuthService;
import com.google.gson.Gson;

public class UserController {
  private static final Gson gson = new Gson();

  public static void init(AuthService authService) {
    post("/users", (req, res) -> {
      res.type("application/json");

      CreateUserRequest body = gson.fromJson(req.body(), CreateUserRequest.class);

      String result = authService.register(body.name, body.email, body.password);

      if (result.equals("USER_CREATED")) {
        res.status(201);
        return gson.toJson(result);
      }

      res.status(400);
      return gson.toJson(result);

    });
  }

  static class CreateUserRequest {
    public String name;
    public String email;
    public String password;
  }
}
