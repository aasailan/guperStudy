package guperstudy.design.template;

import guperstudy.design.template.entity.User;
import guperstudy.design.template.rowmapper.UserMapper;

import java.util.List;

/**
 * @author joe
 * @program: design
 * @description: jdbcTemplate测试类
 * @date 2020-09-26 16:43:07
 */
public class JdbcTemplateMainTest {
    public static void main(String[] args) throws Exception {
        String sql = "select * from yiDev.user_mds where id < ?";
        // 执行sql语句
        List<User> users = JdbcTemplate.executeQuery(sql, new Object[] { new Integer(3) }, new UserMapper());
        System.out.println(users);
    }
}
