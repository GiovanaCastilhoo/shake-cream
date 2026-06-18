package com.shakecream.middleware;

import static spark.Spark.*;

import com.shakecream.model.User;
import com.shakecream.util.AuthUtil;
import com.shakecream.model.Client;
import com.shakecream.util.SessionUtil;

public class AuthMiddleware {

    public static void init() {

        before((req, res) -> {

            String path = req.pathInfo();
            String method = req.requestMethod();

            boolean isPublic =
                path.startsWith("/client/login") ||
                path.startsWith("/login") ||
                (method.equals("GET") && (
                    path.startsWith("/categories") 
                ));
                
            if (isPublic) return;

            try {
                User user = AuthUtil.getUserFromToken(req);
                req.attribute("user", user);
                return;

            } catch (Exception e) {
            }

            // tenta session
            String sessionId = req.headers("Session-Id");

            if (sessionId != null) {
                Client client = SessionUtil.getClient(sessionId);

            if (client != null) {
                req.attribute("client", client);
                return;
            }}

            halt(401, "UNAUTHORIZED");
        });
    }
}
