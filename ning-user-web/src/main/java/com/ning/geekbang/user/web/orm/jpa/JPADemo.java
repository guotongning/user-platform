package com.ning.geekbang.user.web.orm.jpa;

import com.ning.geekbang.user.web.domain.User;
import org.apache.derby.jdbc.EmbeddedDataSource;

import javax.persistence.*;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: nicholas
 * @Date: 2021/3/9 20:27
 * @Descreption:
 */
public class JPADemo {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("emf", getProperties());
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user = new User();
        user.setName("nicholas");
        user.setEmail("guotongning@126.com");
        user.setPassword("************");
        user.setPhoneNumber("12345678910");
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(user);
        transaction.commit();
        System.out.println(user);
    }

    private static Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.DerbyDialect");
        properties.put("hibernate.id.new_generator_mappings", false);
        properties.put("hibernate.connection.datasource", getDataSource());
        return properties;
    }

    private static DataSource getDataSource() {
        EmbeddedDataSource dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("/db/user-platform");
        dataSource.setCreateDatabase("create");
        return dataSource;
    }
}
