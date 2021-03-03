package com.ning.geekbang.user.web.service.impl;

import com.ning.geekbang.user.web.domain.User;
import com.ning.geekbang.user.web.repository.UserRepository;
import com.ning.geekbang.user.web.repository.impl.UserRepositoryImpl;
import com.ning.geekbang.user.web.service.UserService;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Author: nicholas
 * @Date: 2021/3/1 21:27
 * @Descreption:
 */
public class UserServiceImpl implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    private UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public boolean register(User user) {
        User existUser = queryUserByNameAndPassword(user.getName(), user.getPassword());
        if (existUser != null) {
            logger.log(Level.SEVERE, String.format("用户注册失败！已注册! name=%s password=%s", existUser.getName(), existUser.getPassword()));
            return false;
        }
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
