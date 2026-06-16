package com.shakecream.service;

import java.util.List;

import com.shakecream.dao.order.OrderDAO;
import com.shakecream.dao.order.OrderProductDAO;
import com.shakecream.model.OrderItem;
import com.shakecream.dao.order.OrderProductAdditionalDAO;
import com.shakecream.dao.additional.AdditionalDAO;

public class OrderService {

  private OrderDAO orderDAO;
  private OrderProductDAO orderProductDAO;
  private OrderProductAdditionalDAO orderProductAdditionalDAO;
  private AdditionalDAO additionalDAO;

  public OrderService(OrderDAO orderDAO, OrderProductDAO orderProductDAO, OrderProductAdditionalDAO orderProductAdditionalDAO,  AdditionalDAO additionalDAO) {
    this.orderDAO = orderDAO;
    this.orderProductDAO = orderProductDAO;
    this.orderProductAdditionalDAO = orderProductAdditionalDAO;
    this.additionalDAO = additionalDAO;
  }

  // =========================
  // CRIAR PEDIDO COMPLETO
  // =========================
  public String createFullOrder(int userId, int tableId, List<OrderItem> items) {

    try {
      int orderId = orderDAO.createOrder(userId, tableId);

      double total = 0;

      for (OrderItem item : items) {

        // cria produto no pedido
        int orderProductId = orderProductDAO.addProduct(
            orderId,
            item.getProductId(),
            item.getQuantity(),
            item.getPrice());

        double itemTotal = item.getPrice() * item.getQuantity();

        // adiciona adicionais (se tiver)
        if (item.getAdditionals() != null) {
          for (Integer addId : item.getAdditionals()) {

            orderProductAdditionalDAO.addAdditional(orderProductId, addId);

            double addPrice = additionalDAO.findPrice(addId);
            itemTotal += addPrice;
          }
        }

        total += itemTotal;
      }

      // atualiza total do pedido
      orderDAO.updateTotal(orderId, total);

      return "ORDER_CREATED:" + orderId;

    } catch (RuntimeException e) {
      return e.getMessage();
    }
  }

  public Object getOrderById(int orderId) {

    try {
      return orderDAO.getOrderDetails(orderId);
    } catch (RuntimeException e) {
      return e.getMessage();
    }
  }
}