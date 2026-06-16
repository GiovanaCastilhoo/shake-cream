package com.shakecream.service;

import java.util.List;

import com.shakecream.dao.category.CategoryDAO;
import com.shakecream.model.Category;
import com.shakecream.model.User;
import com.shakecream.util.AuthUtil;

public class CategoryService {

    private CategoryDAO categoryDAO;

    public CategoryService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public Category createCategory(String name, User user) {

        AuthUtil.validateAdmin(user);

        if (name == null || name.trim().isEmpty()) {
            throw new RuntimeException("INVALID_NAME");
        }

        if (categoryDAO.exists(name)) {
            throw new RuntimeException("CATEGORY_ALREADY_EXISTS");
        }

        categoryDAO.insert(name);
        return categoryDAO.findByName(name);
    }

    public List<Category> getAll() {
        return categoryDAO.findAll();
    }

    public Category updateCategory(int id, String name, User user) {

        AuthUtil.validateAdmin(user);

        if (name == null || name.trim().isEmpty()) {
            throw new RuntimeException("INVALID_NAME");
        }

        if (categoryDAO.exists(name)) {
            throw new RuntimeException("CATEGORY_ALREADY_EXISTS");
        }

        categoryDAO.update(id, name);
        return categoryDAO.findById(id);
    }
}