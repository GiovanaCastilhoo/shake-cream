package com.shakecream.api.category;

import static spark.Spark.*;

import com.google.gson.Gson;
import com.shakecream.model.Category;
import com.shakecream.model.User;
import com.shakecream.service.CategoryService;

public class CategoryController {

    private static final Gson gson = new Gson();

    public static void init(CategoryService categoryService) {

        post("/categories", (req, res) -> {

            User user = req.attribute("user");
            Category body = gson.fromJson(req.body(), Category.class);
            String name = body.getName();

            try {
                Category created = categoryService.createCategory(name, user);
                return gson.toJson(created);

            } catch (RuntimeException e) {
                e.printStackTrace();
                res.status(400);
                return gson.toJson(e.getMessage());
            }
        });

        get("/categories", (req, res) -> {
            res.type("application/json");
            return gson.toJson(categoryService.getAll());
        });

        put("/categories/:id", (req, res) -> {

            User user = req.attribute("user");

            int id = Integer.parseInt(req.params(":id"));
            Category body = gson.fromJson(req.body(), Category.class);
            String name = body.getName();

            try {
                Category updated = categoryService.updateCategory(id, name, user);
                return gson.toJson(updated);

            } catch (RuntimeException e) {
                e.printStackTrace();
                res.status(400);
                return gson.toJson(e.getMessage());
            }
        });
    }
}