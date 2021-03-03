package com.ning.geekbang.user.web.service.impl;

import com.ning.geekbang.user.web.domain.User;
import com.ning.geekbang.user.web.repository.UserRepository;
import com.ning.geekbang.user.web.repository.impl.UserRepositoryImpl;
import com.ning.geekbang.user.web.service.UserService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Collection;
import java.util.logging.Logger;

/**
 * @Author: nicholas
 * @Date: 2021/3/1 21:57
 * @Descreption: 测试UserService的所有方法。
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest {

    private static final Logger logger = Logger.getLogger(UserServiceImplTest.class.getName());

    private UserService userService = new UserServiceImpl();

    private UserRepository userRepository = new UserRepositoryImpl();


    @Test
    public void test1Register() {
        userRepository.dropTable();
        userRepository.createTable();
        User user = new User();
        user.setName("nicholas1");
        user.setEmail("guotongning@126.com");
        user.setPassword("123456");
        user.setPhoneNumber("12312312312");
        boolean register = userService.register(user);
        logger.info("测试添加 = " + register);
    }

    @Test
    public void test2QueryAll() {
        Collection<User> all = userRepository.getAll();
        logger.info("测试查询所有：users=" + all);
    }

    @Test
    public void test3Update() {
        User user = userService.queryUserById(1L);
        String passwordPre = user.getPassword();
        user.setPassword("234234");
        userService.update(user);
        User update = userService.queryUserById(1L);
        logger.info("测试修改用户信息 修改前：user.password = " + passwordPre + " 修改后 user.password = " + update.getPassword());
    }

    @Test
    public void test4QueryUserById() {
        User user = userService.queryUserById(1L);
        logger.info("测试根据id查询 user = " + user);
    }

    @Test
    public void test5QueryUserByNameAndPassword() {
        User user = userService.queryUserById(1L);
        User nicholas = userService.queryUserByNameAndPassword(user.getName(), user.getPassword());
        logger.info("测试查询 （name,password） = " + nicholas);
    }

    @Test
    public void tes6tDeregister() {
        User user = userService.queryUserById(1L);
        boolean deregister = userService.deregister(user);
        logger.info("测试注销用户 deregister = " + deregister);
    }

}
