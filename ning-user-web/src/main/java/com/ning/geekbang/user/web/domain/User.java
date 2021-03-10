package com.ning.geekbang.user.web.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

import static javax.persistence.GenerationType.AUTO;

/**
 * @Author: nicholas
 * @Date: 2021/3/1 21:23
 * @Descreption:
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    @Size(max = 32, min = 6)
    private String password;
    @Column
    @Size(max = 11, min = 11)
    private String phoneNumber;

    public User() {
    }

    public User(String name, String email, String password, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
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
        return this.id.equals(user.id) && this.name.equals(user.name) && this.email.equals(user.email) && this.phoneNumber.equals(user.phoneNumber) && this.password.equals(user.password);
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
