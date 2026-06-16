package com.shakecream.api.table;

import static spark.Spark.*;

import com.google.gson.Gson;
import com.shakecream.model.User;
import com.shakecream.service.TableService;

public class TableController {

    private static final Gson gson = new Gson();

    public static void init(TableService tableService) {

        post("/tables", (req, res) -> {

            User user = req.attribute("user");

            int number = Integer.parseInt(req.queryParams("number"));

            String result = tableService.createTable(number, user);

            return gson.toJson(result);
        });

        delete("/tables/:id", (req, res) -> {

            User user = req.attribute("user");

            int id = Integer.parseInt(req.params(":id"));

            String result = tableService.deleteTable(id, user);

            return gson.toJson(result);
        });
    }
}