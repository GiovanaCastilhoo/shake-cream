package com.shakecream.service;

import java.util.List;

import com.shakecream.dao.product.ProductDAO;
import com.shakecream.model.Product;
import com.shakecream.model.User;
import com.shakecream.util.AuthUtil;

public class ProductService {

    private ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public Product createProduct(Product product, User user) {

            AuthUtil.validateAdmin(user);

            if (product.getPrice() <= 0) {
                throw new RuntimeException("INVALID_NAME");
            }

            int id = productDAO.create(product);
            return productDAO.findById(id);

    }

    public List<Product> getAll() {
        return productDAO.findAll();
    }

    public List<Product> getByCategoryId(int categoryId) {
        return productDAO.findByCategoryId(categoryId);
    }

    public Product update(Product product, User user) {

            AuthUtil.validateAdmin(user);

            productDAO.update(product);
            return productDAO.findById(product.getId());

    }

    public String delete(int id, User user) {

        try {
            AuthUtil.validateAdmin(user);

            productDAO.delete(id);
            return "PRODUCT_DELETED";

        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}
