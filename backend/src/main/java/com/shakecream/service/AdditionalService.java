package com.shakecream.service;

import java.util.List;

import com.shakecream.dao.additional.AdditionalDAO;
import com.shakecream.model.Additional;
import com.shakecream.model.User;
import com.shakecream.util.AuthUtil;

public class AdditionalService {

    private AdditionalDAO additionalDAO;

    public AdditionalService(AdditionalDAO additionalDAO) {
        this.additionalDAO = additionalDAO;
    }

    public Additional create(String name, double price, User user) {

            AuthUtil.validateAdmin(user);

            if (name == null || name.trim().isEmpty()) {
                throw new RuntimeException("INVALID_NAME");            
            }

            if (price <= 0) {
                throw new RuntimeException("INVALID_PRICE");  
            }

            if (additionalDAO.exists(name)) {
                throw new RuntimeException("ADDITIONAL_ALREADY_EXISTS");  
            }

            int id = additionalDAO.insert(name, price);
            return additionalDAO.findById(id);

    }

    public List<Additional> getAll() {
        return additionalDAO.findAll();
    }

    // UPDATE
    public Additional update(int id, String name, double price, User user) {

            AuthUtil.validateAdmin(user);

            if (name == null || name.trim().isEmpty()) {
                throw new RuntimeException("INVALID_NAME");
            }

            if (price <= 0) {
                throw new RuntimeException("INVALID_PRICE");  
            }

            additionalDAO.update(id, name, price);

            return additionalDAO.findById(id);

    }

    // DELETE
    public String delete(int id, User user) {

        try {
            AuthUtil.validateAdmin(user);

            additionalDAO.delete(id);

            return "ADDITIONAL_DELETED";

        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}