package com.ning.geekbang.user.web.service.impl;

import com.ning.geekbang.user.web.domain.User;
import com.ning.geekbang.user.web.repository.UserRepository;
import com.ning.geekbang.user.web.repository.impl.UserRepositoryImpl;
import com.ning.geekbang.user.web.service.UserService;

/**
 * @Author: nicholas
 * @Date: 2021/3/1 21:27
 * @Descreption:
 */
public class UserServiceImpl implements UserService {

    private UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public boolean register(User user) {
        System.out.printf("用户注册 user=%s\r\n", user.toString());
        return userRepository.save(user);
    }

    @Override
    public boolean deregister(User user) {
        return userRepository.deleteById(user.getId());
    }

    @Override
    public User update(User user) {
        return userRepository.update(user) ? user : null;
    }

    @Override
    public User queryUserById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public User queryUserByNameAndPassword(String name, String password) {
        return userRepository.getByNameAndPassword(name, password);
    }
}
