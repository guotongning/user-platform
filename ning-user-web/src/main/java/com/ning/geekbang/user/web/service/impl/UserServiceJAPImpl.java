package com.ning.geekbang.user.web.service.impl;

import com.ning.geekbang.user.web.domain.User;
import com.ning.geekbang.user.web.service.UserService;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Author: nicholas
 * @Date: 2021/3/9 19:51
 * @Descreption:
 */
public class UserServiceJAPImpl implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceJAPImpl.class.getName());

    @Resource(name = "bean/EntityManager")
    private EntityManager entityManager;

    @Resource(name = "bean/Validator")
    private Validator validator;

    @Override
    public boolean register(User user) {
        Set<ConstraintViolation<User>> validate = validator.validate(user);

        if (validate.size() > 0) {
            validate.forEach(c -> logger.log(Level.SEVERE, c.getPropertyPath() + " - " + c.getMessage()));
            return false;
        }

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(user);
        transaction.commit();
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
