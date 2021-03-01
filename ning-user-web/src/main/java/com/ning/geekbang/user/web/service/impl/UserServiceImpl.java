package com.ning.geekbang.user.web.service.impl;

import com.ning.geekbang.user.web.domain.User;
import com.ning.geekbang.user.web.repository.UserRepository;
import com.ning.geekbang.user.web.service.UserService;

/**
 * @Author: nicholas
 * @Date: 2021/3/1 21:27
 * @Descreption:
 */
public class UserServiceImpl implements UserService {

    private UserRepository userRepository = new UserRepository();

    @Override
    public boolean register(User user) {
        System.out.printf("用户注册 user=%s\r\n", user.toString());
        return true;
    }

    @Override
    public boolean deregister(User user) {
        return false;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User queryUserById(Long id) {
        return null;
    }

    @Override
    public User queryUserByNameAndPassword(String name, String password) {
        return null;
    }
}
