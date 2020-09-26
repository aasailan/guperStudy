package guperstudy.design.adapter.loginadapter;

import guperstudy.design.adapter.loginadapter.entity.ResultMsg;

/**
 * @author joe
 * @program: design
 * @description: 用户名密码登录注册服务
 * @date 2020-09-26 17:12:19
 */
public class SiginService {

    public void register(String userId, String pwd) {
        System.out.println("账户密码注册 userid: " + userId + " password: " + pwd);
    }

    public ResultMsg login(String userId, String pwd) {
        System.out.println("账户密码登录 userid: " + userId + " password: " + pwd);
        System.out.println("创建登录会话，返回登录结果信息");
        return new ResultMsg();
    }
}
