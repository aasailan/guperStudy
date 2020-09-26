package guperstudy.design.template.rowmapper;

import guperstudy.design.template.entity.User;

import java.sql.ResultSet;

/**
 * @author joe
 * @program: design
 * @description: 将结果集映射成用户类
 * @date 2020-09-26 16:46:49
 */
public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws Exception {
        User user = new User();
        user.setId(rs.getString("id"));
        user.setOpenId(rs.getString("open_id"));
        user.setNickName(rs.getString("nick_name"));
        user.setAvatar(rs.getString("avatar"));
        user.setRegisterTime(rs.getString("register_time"));
        return user;
    }
}
