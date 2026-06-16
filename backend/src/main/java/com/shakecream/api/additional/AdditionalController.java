package com.shakecream.api.additional;

import static spark.Spark.*;

import com.google.gson.Gson;
import com.shakecream.model.Additional;
import com.shakecream.model.User;
import com.shakecream.service.AdditionalService;

public class AdditionalController {

    private static final Gson gson = new Gson();

    public static void init(AdditionalService additionalService) {
        post("/additionals", (req, res) -> {

            User user = req.attribute("user");

            Additional body = gson.fromJson(req.body(), Additional.class);
            String name = body.getName();
            double price = body.getPrice();

            try {
                Additional result = additionalService.create(name, price, user);
                return gson.toJson(result);
            } catch (RuntimeException e) {
                e.printStackTrace();
                res.status(400);
                return gson.toJson(e.getMessage());
            }

        });

        get("/additionals", (req, res) -> {
            res.type("application/json");
            return gson.toJson(additionalService.getAll());
        });

        put("/additionals/:id", (req, res) -> {

            User user = req.attribute("user");

            int id = Integer.parseInt(req.params(":id"));
            Additional body = gson.fromJson(req.body(), Additional.class);
            String name = body.getName();
            double price = body.getPrice();

            try {
                Additional result = additionalService.update(id, name, price, user);

                return gson.toJson(result);
            } catch (RuntimeException e) {
                e.printStackTrace();
                res.status(400);
                return gson.toJson(e.getMessage());
            }

        });

        delete("/additionals/:id", (req, res) -> {

            User user = req.attribute("user");

            int id = Integer.parseInt(req.params(":id"));

            String result = additionalService.delete(id, user);

            return gson.toJson(result);
        });
    }
}