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

}
