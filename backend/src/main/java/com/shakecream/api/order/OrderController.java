package com.shakecream.api.order;

import static spark.Spark.*;

import com.google.gson.Gson;
import com.shakecream.model.OrderRequest;
import com.shakecream.service.OrderService;

public class OrderController {

  private static final Gson gson = new Gson();

  public static void init(OrderService orderService) {

    post("/orders/complete", (req, res) -> {

      res.type("application/json");

      OrderRequest body = gson.fromJson(req.body(), OrderRequest.class);

      String result = orderService.createFullOrder(
          body.getUserId(),
          body.getTableId(),
          body.getItems());

      return gson.toJson(result);
    });

    get("/orders/:id", (req, res) -> {

      res.type("application/json");

      int orderId = Integer.parseInt(req.params(":id"));

      Object order = orderService.getOrderById(orderId);

      return gson.toJson(order);
    });
  }
}
