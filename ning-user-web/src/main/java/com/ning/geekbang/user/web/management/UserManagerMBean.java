package com.ning.geekbang.user.web.management;

import com.ning.geekbang.user.web.domain.User;

/**
 * {@link User} MBean 接口描述
 */
public interface UserManagerMBean {

    // MBeanAttributeInfo 列表
    Integer getId();

    void setId(Integer id);

    String getName();

    void setName(String name);

    String getPassword();

    void setPassword(String password);

    String getEmail();

    void setEmail(String email);

    String getPhoneNumber();

    void setPhoneNumber(String phoneNumber);

    // MBeanOperationInfo
    String toString();

    User getUser();

}
