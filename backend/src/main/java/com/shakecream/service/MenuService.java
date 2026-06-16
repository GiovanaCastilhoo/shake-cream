package com.shakecream.service;

import java.util.List;

import com.shakecream.dao.menu.MenuDAO;
import com.shakecream.model.Product;
import com.shakecream.model.User;
import com.shakecream.util.AuthUtil;

public class MenuService {

    private MenuDAO menuDAO;

    public MenuService(MenuDAO menuDAO) {
        this.menuDAO = menuDAO;
    }

    public String createMenu(String name, User user) {

        try {
            AuthUtil.validateAdmin(user);

            if (name == null || name.trim().isEmpty()) {
                return "INVALID_NAME";
            }

            if (menuDAO.exists(name)) {
                return "MENU_ALREADY_EXISTS";
            }

            menuDAO.insert(name);
            return "MENU_CREATED";

        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public String deleteMenu(int id, User user) {

        try {
            AuthUtil.validateAdmin(user);

            menuDAO.delete(id);
            return "MENU_DELETED";

        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public String addProductToMenu(int menuId, int productId, User user) {

        try {
            AuthUtil.validateAdmin(user);

            menuDAO.addProductToMenu(menuId, productId);
            return "PRODUCT_ADDED_TO_MENU";

        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public String removeProductFromMenu(int menuId, int productId, User user) {

        try {
            AuthUtil.validateAdmin(user);

            menuDAO.removeProduct(menuId, productId);
            return "PRODUCT_REMOVED_FROM_MENU";

        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public List<Product> getProductsByMenu(int menuId) {
        return menuDAO.findProductsByMenuId(menuId);
    }
}