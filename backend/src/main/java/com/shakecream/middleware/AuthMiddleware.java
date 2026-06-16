package com.shakecream.middleware;

import static spark.Spark.*;

import com.shakecream.model.User;
import com.shakecream.util.AuthUtil;

public class AuthMiddleware {

    public static void init() {

        before((req, res) -> {

            String path = req.pathInfo();
            System.out.println("METHOD: " + req.requestMethod());
            System.out.println("PATH: " + req.pathInfo());
            System.out.println("AUTH HEADER: " + req.headers("Authorization"));

            boolean isPublic = path.startsWith("/client/login") ||
                    path.startsWith("/login") ||
                    (req.requestMethod().equals("GET") && path.startsWith("/categories"));

            if (isPublic)
                return;

            try {
                User user = AuthUtil.getUserFromToken(req);
                req.attribute("user", user);

            } catch (Exception e) {
                halt(401, "UNAUTHORIZED");
            }
        });
    }
}
