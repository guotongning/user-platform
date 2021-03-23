package com.ning.geekbang.user.web.repository.autorepository;

import com.ning.commons.function.ThrowableFunction;

import javax.annotation.Resource;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.apache.commons.lang.ClassUtils.wrapperToPrimitive;

public abstract class AbstractRepository implements Repository {


    private static Logger logger = Logger.getLogger(AbstractRepository.class.getName());

    /**
     * 通用处理方式
     */
    protected static Consumer<Throwable> COMMON_EXCEPTION_HANDLER = e -> logger.log(Level.SEVERE, e.getMessage());

    @Resource(name = "bean/DBDerbyConnectionManager")
    private DBDerbyConnectionManager derbyConnectionManager;

    /**
     * 数据类型与 ResultSet 方法名映射
     */
    protected static Map<Class, String> resultSetMethodMappings = new HashMap<>();

    protected static Map<Class, String> preparedStatementMethodMappings = new HashMap<>();

    static {
        resultSetMethodMappings.put(Long.class, "getLong");
        preparedStatementMethodMappings.put(Long.class, "setLong");
        resultSetMethodMappings.put(String.class, "getString");
        preparedStatementMethodMappings.put(String.class, "setString");
    }

    @Override
    public <T> T executeQuery(String sql, ThrowableFunction<ResultSet, T> function, Consumer<Throwable> exceptionHandler, Object... args) {
        Connection connection = null;
        try {
            connection = derbyConnectionManager.getConnection();
            PreparedStatement preparedStatement = getPreparedStatement(sql, connection, args);
            ResultSet resultSet = preparedStatement.executeQuery();
            return function.apply(resultSet);
        } catch (Throwable e) {
            if (exceptionHandler == null) {
                COMMON_EXCEPTION_HANDLER.accept(e);
            } else {
                exceptionHandler.accept(e);
            }
        } finally {
            derbyConnectionManager.closeConnection(connection);
        }
        return null;
    }

    @Override
    public int executeUpdate(String sql, Consumer<Throwable> exceptionHandler, Object... args) {
        Connection connection = null;
        try {
            connection = derbyConnectionManager.getConnection();
            PreparedStatement preparedStatement = getPreparedStatement(sql, connection, args);
            return preparedStatement.executeUpdate();
        } catch (Throwable e) {
            if (exceptionHandler == null) {
                COMMON_EXCEPTION_HANDLER.accept(e);
            } else {
                exceptionHandler.accept(e);
            }
        } finally {
            derbyConnectionManager.closeConnection(connection);
        }
        return 0;
    }

    @Override
    public boolean executeSQL(String sql, Consumer<Throwable> exceptionHandler) {
        Connection connection = null;
        try {
            connection = derbyConnectionManager.getConnection();
            return connection.prepareStatement(sql).execute();
        } catch (Throwable e) {
            if (exceptionHandler == null) {
                COMMON_EXCEPTION_HANDLER.accept(e);
            } else {
                exceptionHandler.accept(e);
            }
        } finally {
            derbyConnectionManager.closeConnection(connection);
        }
        return false;
    }


    public <T> List<T> resultSetMapToList(Class<T> clazz, ResultSet resultSet) throws Exception {
        List<T> beans = new ArrayList<>();
        while (resultSet.next()) {
            beans.add(defaultBeanMapping(clazz, resultSet));
        }
        return beans;
    }

    public <T> T resultSetMapToBean(Class<T> clazz, ResultSet resultSet) throws Exception {
        resultSet.next();
        return defaultBeanMapping(clazz, resultSet);
    }

    private <T> T defaultBeanMapping(Class<T> clazz, ResultSet resultSet) throws Exception {
        BeanInfo userBeanInfo = Introspector.getBeanInfo(clazz, Object.class);
        T t = clazz.newInstance();
        for (PropertyDescriptor propertyDescriptor : userBeanInfo.getPropertyDescriptors()) {
            String fieldName = propertyDescriptor.getName();
            Class fieldType = propertyDescriptor.getPropertyType();
            String methodName = resultSetMethodMappings.get(fieldType);
            String columnLabel = mapColumnLabel(fieldName);
            Method resultSetMethod = ResultSet.class.getMethod(methodName, String.class);
            Object resultValue = resultSetMethod.invoke(resultSet, columnLabel);
            Method setterMethodFromUser = propertyDescriptor.getWriteMethod();
            setterMethodFromUser.invoke(t, resultValue);
        }
        return t;
    }

    private String mapColumnLabel(String fieldName) {
        //映射关系就不管了。
        return fieldName;
    }

    private PreparedStatement getPreparedStatement(String sql, Connection connection, Object... args) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            Class argType = arg.getClass();
            //基础类型和包装类型的转换
            Class wrapperType = wrapperToPrimitive(argType);
            if (wrapperType == null) {
                wrapperType = argType;
            }
            String methodName = preparedStatementMethodMappings.get(argType);
            //获取
            Method method = PreparedStatement.class.getMethod(methodName, int.class, wrapperType);
            method.invoke(preparedStatement, i + 1, arg);
        }
        return preparedStatement;
    }

}
