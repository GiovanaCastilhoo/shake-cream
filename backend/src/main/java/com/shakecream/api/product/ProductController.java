package com.shakecream.api.product;

import static spark.Spark.*;

import com.shakecream.model.Product;
import com.shakecream.model.User;
import com.shakecream.service.ProductService;
import com.google.gson.Gson;

public class ProductController {

  private static final Gson gson = new Gson();

  public static void init(ProductService productService) {

    post("/products", (req, res) -> {

      res.type("application/json");

      User user = req.attribute("user");

      Product product = gson.fromJson(req.body(), Product.class);

      return gson.toJson(productService.createProduct(product, user));
    });

    get("/products", (req, res) -> {
      res.type("application/json");
      return gson.toJson(productService.getAll());
    });

    put("/products/:id", (req, res) -> {
      User user = req.attribute("user");

      int id = Integer.parseInt(req.params(":id"));

      Product product = gson.fromJson(req.body(), Product.class);
      product.setId(id);

      return gson.toJson(productService.update(product, user));
    });

    delete("/products/:id", (req, res) -> {

      User user = req.attribute("user");

      int id = Integer.parseInt(req.params(":id"));

      return gson.toJson(productService.delete(id, user));
    });
  }
}
