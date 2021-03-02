package com.ning.geekbang.user.web.repository.impl;

import com.ning.geekbang.user.web.domain.User;
import com.ning.geekbang.user.web.repository.UserRepository;
import com.ning.geekbang.user.web.repository.autorepository.AbstractRepository;

import java.util.Collection;

/**
 * @Author: nicholas
 * @Date: 2021/3/1 22:13
 * @Descreption:
 */
public class UserRepositoryImpl extends AbstractRepository implements UserRepository {

    public static final String DROP_USERS_TABLE_DDL_SQL = "DROP TABLE users";

    public static final String CREATE_USERS_TABLE_DDL_SQL = "CREATE TABLE users(" +
            "id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
            "name VARCHAR(16) NOT NULL, " +
            "password VARCHAR(64) NOT NULL, " +
            "email VARCHAR(64) NOT NULL, " +
            "phoneNumber VARCHAR(64) NOT NULL" +
            ")";

    private static final String INSERT_SQL = "INSERT INTO users (name,password,email,phoneNumber) VALUES (?,?,?,?)";
    private static final String DELETE_SQL = "DELETE users WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE users SET name = ?,password = ?,email = ?,phoneNumber = ? WHERE id = ?";
    private static final String SELECT_BY_ID = "SELECT id,name,password,email,phoneNumber from users WHERE id = ?";
    private static final String SELECT_BY_NAME_PASSWORD = "SELECT id,name,password,email,phoneNumber FROM users WHERE name=? AND password=?";
    private static final String SELECT_ALL = "SELECT id,name,password,email,phoneNumber FROM users";

    @Override
    public boolean save(User user) {
        return executeUpdate(INSERT_SQL, COMMON_EXCEPTION_HANDLER, user.getName(), user.getPassword(), user.getEmail(), user.getPhoneNumber()) == 1;
    }

    @Override
    public boolean deleteById(Long userId) {
        return executeUpdate(DELETE_SQL, COMMON_EXCEPTION_HANDLER, userId) == 1;
    }

    @Override
    public boolean update(User user) {
        return executeUpdate(UPDATE_SQL, COMMON_EXCEPTION_HANDLER, user.getName(), user.getPassword(), user.getEmail(), user.getPhoneNumber(), user.getId()) == 1;
    }

    @Override
    public User getById(Long userId) {
        return executeQuery(SELECT_BY_ID, resultSet -> resultSetMapToBean(User.class, resultSet), COMMON_EXCEPTION_HANDLER, userId);
    }

    @Override
    public User getByNameAndPassword(String userName, String password) {
        return executeQuery(SELECT_BY_NAME_PASSWORD, resultSet -> resultSetMapToBean(User.class, resultSet), COMMON_EXCEPTION_HANDLER, userName, password);
    }

    @Override
    public Collection<User> getAll() {
        return executeQuery(SELECT_ALL, resultSet -> resultSetMapToList(User.class, resultSet), COMMON_EXCEPTION_HANDLER);
    }

    @Override
    public void createTable() {
        executeSQL(CREATE_USERS_TABLE_DDL_SQL, COMMON_EXCEPTION_HANDLER);
    }

    @Override
    public void dropTable() {
        executeSQL(DROP_USERS_TABLE_DDL_SQL, COMMON_EXCEPTION_HANDLER);
    }
}
