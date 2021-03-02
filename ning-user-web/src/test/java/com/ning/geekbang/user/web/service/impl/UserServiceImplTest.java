package com.ning.geekbang.user.web.service.impl;

import com.ning.geekbang.user.web.domain.User;
import com.ning.geekbang.user.web.repository.UserRepository;
import com.ning.geekbang.user.web.repository.impl.UserRepositoryImpl;
import com.ning.geekbang.user.web.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

/**
 * @Author: nicholas
 * @Date: 2021/3/1 21:57
 * @Descreption:
 */
public class UserServiceImplTest {

    private UserService userService = new UserServiceImpl();

    private UserRepository userRepository = new UserRepositoryImpl();

    @Before
    public void testCreateTable() {
        userRepository.dropTable();
        userRepository.createTable();
    }

    @Test
    public void testRegister() {
        User user = new User();
        user.setName("nicholas");
        user.setEmail("guotongning@126.com");
        user.setPassword("123456");
        user.setPhoneNumber("12312312312");
        boolean register = userService.register(user);
        System.out.println("测试添加 = " + register);
    }

    @Test
    public void testQueryAll() {
        Collection<User> all = userRepository.getAll();
        System.out.println("测试查询所有：users=" + all);
    }

    @Test
    public void testDeregister() {

    }

    @Test
    public void testUpdate() {
    }

    @Test
    public void testQueryUserById() {
    }

    @Test
    public void testQueryUserByNameAndPassword() {
        User nicholas = userService.queryUserByNameAndPassword("nicholas", "123456");
        System.out.println("测试查询 （name,password） = " + nicholas);
    }
}
