package com.ning.geekbang.user.web.repository.autorepository;

import com.ning.commons.function.ThrowableFunction;

import java.sql.ResultSet;
import java.util.function.Consumer;

public interface Repository {

    /**
     * 处理select语句
     *
     * @param sql      sql
     * @param function 结果集处理
     * @param <T>      结果集类型
     * @return 查询的结果
     */
    <T> T executeQuery(String sql, ThrowableFunction<ResultSet, T> function,
                       Consumer<Throwable> exceptionHandler, Object... args);

    /**
     * 处理insert update delete 语句
     *
     * @param sql              sql
     * @param exceptionHandler 异常处理
     * @param args             sql params
     * @return 修改的行数
     */
    int executeUpdate(String sql, Consumer<Throwable> exceptionHandler, Object... args);

    /**
     * 执行没有参数的sql
     *
     * @param sql
     * @param exceptionHandler 异常处理
     * @return 是否执行成功
     */
    boolean executeSQL(String sql, Consumer<Throwable> exceptionHandler);

}
