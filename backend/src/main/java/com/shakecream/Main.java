package com.shakecream;

import static spark.Spark.*;
import java.sql.Connection;

import com.shakecream.api.additional.AdditionalController;
import com.shakecream.api.auth.AuthController;
import com.shakecream.api.auth.ClientController;
import com.shakecream.api.auth.UserController;
import com.shakecream.api.category.CategoryController;
import com.shakecream.api.menu.MenuController;
import com.shakecream.api.order.OrderController;
import com.shakecream.api.product.ProductController;
import com.shakecream.api.table.TableController;
import com.shakecream.dao.additional.AdditionalDAO;
import com.shakecream.dao.category.CategoryDAO;
import com.shakecream.dao.menu.MenuDAO;
import com.shakecream.dao.order.OrderDAO;
import com.shakecream.dao.order.OrderProductAdditionalDAO;
import com.shakecream.dao.order.OrderProductDAO;
import com.shakecream.dao.product.ProductDAO;
import com.shakecream.dao.table.TableDAO;
import com.shakecream.dao.user.UserDAO;
import com.shakecream.db.DB;
import com.shakecream.middleware.AuthMiddleware;
import com.shakecream.service.AdditionalService;
import com.shakecream.service.AuthService;
import com.shakecream.service.CategoryService;
import com.shakecream.service.ClientService;
import com.shakecream.service.MenuService;
import com.shakecream.service.OrderService;
import com.shakecream.service.ProductService;
import com.shakecream.service.TableService;

public class Main {

    public static void main(String[] args) {
        Connection conn = DB.getConnection();
        
        port(4567);
        AuthMiddleware.init();

        get("/health", (req, res) -> {
            return "API OK";
        });

        UserDAO userDAO = new UserDAO(conn);
        OrderDAO orderDAO = new OrderDAO(conn);
        OrderProductDAO orderProductDAO = new OrderProductDAO(conn);
        OrderProductAdditionalDAO orderProductAdditionalDAO = new OrderProductAdditionalDAO(conn);
        AdditionalDAO additionalDAO = new AdditionalDAO(conn);
        ProductDAO productDAO = new ProductDAO(conn);
        TableDAO tableDAO = new TableDAO(conn);
        MenuDAO menuDAO = new MenuDAO(conn);
        CategoryDAO categoryDAO = new CategoryDAO(conn);
        

        AuthService authService = new AuthService(userDAO);
        OrderService orderService = new OrderService(orderDAO, orderProductDAO, orderProductAdditionalDAO, additionalDAO);
        AdditionalService additionalService = new AdditionalService(additionalDAO);
        ProductService productService = new ProductService(productDAO);
        TableService tableService = new TableService(tableDAO);
        MenuService menuService = new MenuService(menuDAO);
        CategoryService categoryService = new CategoryService(categoryDAO);
        ClientService clientService = new ClientService();

        // AUTH
        AuthController.init(authService);
        UserController.init(authService);

        // PRODUTO
        ProductController.init(productService);
        CategoryController.init(categoryService);
        MenuController.init(menuService);

        // RESTAURANTE
        TableController.init(tableService);
        AdditionalController.init(additionalService);

        // PEDIDO
        OrderController.init(orderService);

        //CLIENT/LOGIN
        ClientController.init(clientService);
    }
}