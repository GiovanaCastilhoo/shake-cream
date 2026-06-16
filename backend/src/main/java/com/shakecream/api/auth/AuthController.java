package com.shakecream.api.auth;

import static spark.Spark.*;

import com.shakecream.model.LoginResponse;
import com.shakecream.service.AuthService;
import com.google.gson.Gson;

public class AuthController {
  private static final Gson gson = new Gson();

  public static void init(AuthService authService) {
    post("/login", (req, res) -> {
      res.type("application/json");

      var body = gson.fromJson(req.body(), LoginRequest.class);

      LoginResponse response = authService.login(body.email, body.password);

      if (response == null) {
        res.status(401);
        return gson.toJson("Credenciais inválidas");
      }

      return gson.toJson(response);
    });
  }

  static class LoginRequest {
    String email;
    String password;
  }
}
