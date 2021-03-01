package com.ning.geekbang.user.web.domain;

import com.ning.commons.utils.StringUtils;
import com.ning.geekbang.user.web.exception.RequestParamException;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Author: nicholas
 * @Date: 2021/3/1 21:23
 * @Descreption:
 */
public class User {
    private long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    public User() {
    }

    public User(String name, String email, String password, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public static User getCheckedUserFromRequest(HttpServletRequest request) throws Exception {
        String name = request.getParameter("name");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            throw new RequestParamException("参数不完整！");
        }
        return new User(name, email, password, phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, phoneNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return this.id == user.id && this.name.equals(user.name) && this.email.equals(user.email) && this.phoneNumber.equals(user.phoneNumber) && this.password.equals(user.password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
