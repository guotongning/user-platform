package com.ning.geekbang.user.web.repository;

import com.ning.geekbang.user.web.domain.User;

import java.util.Collection;

public interface UserRepository {

    boolean save(User user);

    boolean deleteById(Long userId);

    boolean update(User user);

    User getById(Long userId);

    User getByNameAndPassword(String userName, String password);

    Collection<User> getAll();

    void createTable();

    void dropTable();

}