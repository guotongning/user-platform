package com.ning.geekbang.user.web.service;

import com.ning.geekbang.user.web.domain.User;

public interface UserService {

    /**
     * 用户注册
     *
     * @param user 用户对象
     * @return 成功返回<code>true</code>
     */
    boolean register(User user);

    /**
     * 注销用户
     *
     * @param user 用户对象
     * @return 成功返回<code>true</code>
     */
    boolean deregister(User user);

    /**
     * 修改用户信息
     *
     * @param user 用户对象
     * @return 成功返回更新后的用户对象, 失败返回更新前的用户对象
     * @see User
     */
    User update(User user);

    /**
     * 根据Id查询用户
     *
     * @param id 用户的id
     * @return 成功返回, 失败返回null
     * @see User
     */
    User queryUserById(Long id);

    /**
     * 根据用户名和密码查询用户对象
     *
     * @param name     用户名
     * @param password 密码
     * @return 成功返回用户对象, 失败返回null
     * @see User
     */
    User queryUserByNameAndPassword(String name, String password);

    /*
    1.1 the jdbc api
the jdbc api provides programmatic access to relational data from the java
programming language. using the jdbc api, applications written in the java
programming language can execute sql statements, retrieve results, and propagate
changes back to an underlying data source. the jdbc api can also be used to
interact with multiple data sources in a distributed, heterogeneous environment.
the jdbc api is based on the x/open sql cli, which is also the basis for odbc.
jdbc provides a natural and easy-to-use mapping from the java programming
language to the abstractions and concepts defined in the x/open cli and sql
standards.
since its introduction in january 1997, the jdbc api has become widely accepted
and implemented. the flexibility of the api allows for a broad range of
implementations.
     */

}
