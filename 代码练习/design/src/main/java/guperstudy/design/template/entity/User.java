package guperstudy.design.template.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author joe
 * @program: design
 * @description: 用户实体类
 * @date 2020-09-26 16:41:39
 */
@Setter
@Getter
public class User {
    private String id;
    private String openId;
    private String nickName;
    private String avatar;
    private String registerTime;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", openId='" + openId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", registerTime='" + registerTime + '\'' +
                '}';
    }
}
