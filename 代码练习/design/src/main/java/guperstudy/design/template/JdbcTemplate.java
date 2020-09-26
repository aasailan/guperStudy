package guperstudy.design.template;

import guperstudy.db.pool.DbConnectPool;
import guperstudy.design.template.rowmapper.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author joe
 * @program: design
 * @description: 自定义jdbctemplate实现
 * @date 2020-09-26 16:04:53
 */
public class JdbcTemplate {

    /**
     * 获取数据链接池实例
     */
    private static final DbConnectPool POOL = DbConnectPool.getInstance();

    public static  <T> List<T> executeQuery(String sql, Object[] values, RowMapper<T> mapper) throws Exception {
        // 1) 获取数据库连接
        Connection connection = POOL.getConnection();
        // 2) 创建语句集
        PreparedStatement ps = connection.prepareStatement(sql);
        // 3) 执行语句集
        ResultSet resultSet = executeQuery(ps, values);
        // 4) 处理结果集（将结果集映射成实体）
        List<T> results = mapResult(resultSet, mapper);
        // 5) 关闭结果集
        closeResultSet(resultSet);
        // 6) 关闭语句集
        closeStatement(ps);
        // 7) 释放连接
        POOL.freeConnection(connection);
        return results;
    }

    private static void closeResultSet(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void closeStatement(PreparedStatement ps) {
        try {
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ResultSet executeQuery(PreparedStatement ps, Object[] values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            // NOTE: setObject方法是从1开始的
            ps.setObject(i + 1, values[i]);
        }
        return ps.executeQuery();
    }

    private static  <T> List<T> mapResult(ResultSet resultSet, RowMapper<T> mapper) throws Exception {
        List<T> results = new ArrayList<>();
        int rowIndex = 0;
        // ResultSet内部通过游标控制结果集
        while (resultSet.next()) {
            results.add(mapper.mapRow(resultSet, rowIndex++));
        }
        return results;
    }
}
