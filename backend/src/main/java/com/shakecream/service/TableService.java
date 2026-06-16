package com.shakecream.service;

import com.shakecream.dao.table.TableDAO;
import com.shakecream.model.User;
import com.shakecream.util.AuthUtil;

public class TableService {

    private TableDAO tableDAO;

    public TableService(TableDAO tableDAO) {
        this.tableDAO = tableDAO;
    }

    public String createTable(int number, User user) {

        try {
            AuthUtil.validateAdmin(user);

            if (number <= 0) {
                return "INVALID_TABLE_NUMBER";
            }

            if (tableDAO.exists(number)) {
                return "TABLE_ALREADY_EXISTS";
            }

            tableDAO.insert(number);

            return "TABLE_CREATED";

        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public String deleteTable(int id, User user) {

        try {
            AuthUtil.validateAdmin(user);

            tableDAO.delete(id);

            return "TABLE_DELETED";

        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}