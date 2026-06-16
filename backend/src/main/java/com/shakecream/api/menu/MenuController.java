package com.shakecream.api.menu;

import static spark.Spark.*;

import com.google.gson.Gson;
import com.shakecream.model.User;
import com.shakecream.service.MenuService;

public class MenuController {

    private static final Gson gson = new Gson();

    public static void init(MenuService menuService) {

        post("/menus", (req, res) -> {

            User user = req.attribute("user");

            String name = req.queryParams("name");

            String result = menuService.createMenu(name, user);

            return gson.toJson(result);
        });

        delete("/menus/:id", (req, res) -> {

            User user = req.attribute("user");

            int id = Integer.parseInt(req.params(":id"));

            String result = menuService.deleteMenu(id, user);

            return gson.toJson(result);
        });

        post("/menus/:id/products", (req, res) -> {

            User user = req.attribute("user");

            int menuId = Integer.parseInt(req.params(":id"));
            int productId = Integer.parseInt(req.queryParams("productId"));

            String result = menuService.addProductToMenu(menuId, productId, user);

            return gson.toJson(result);
        });

        delete("/menus/:id/products/:productId", (req, res) -> {

            User user = req.attribute("user");

            int menuId = Integer.parseInt(req.params(":id"));
            int productId = Integer.parseInt(req.params(":productId"));

            String result = menuService.removeProductFromMenu(menuId, productId, user);

            return gson.toJson(result);
        });

        get("/menus/:id/products", (req, res) -> {

            int menuId = Integer.parseInt(req.params(":id"));

            return gson.toJson(menuService.getProductsByMenu(menuId));
        });
    }
}