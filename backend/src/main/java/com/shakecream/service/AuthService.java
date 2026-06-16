package com.shakecream.service;

import org.mindrot.jbcrypt.BCrypt;

import com.shakecream.dao.user.UserDAO;
import com.shakecream.model.LoginResponse;
import com.shakecream.model.User;
import com.shakecream.security.jwt.JwtUtil;

public class AuthService {

    private UserDAO userDAO;

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public LoginResponse login(String email, String password) {

        try {
            User user = userDAO.findByEmail(email);

            if (user == null) {
                return null;
            }

            if (!BCrypt.checkpw(password, user.getPassword())) {
                return null;
            }

            String token = JwtUtil.generateToken(user.getEmail(), user.getRole());

            return new LoginResponse(token, user);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String register(String name, String email, String password) {

        try {

            if (userDAO.findByEmail(email) != null) {
                return "EMAIL_ALREADY_EXISTS";
            }

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            userDAO.insert(name, email, hashedPassword);

            return "USER_CREATED";

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

}