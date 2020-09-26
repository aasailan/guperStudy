package guperstudy.design.adapter.loginadapter;

import guperstudy.design.adapter.loginadapter.entity.ResultMsg;

/**
 * @author joe
 * @program: design
 * @description: 第三方登录适配器类
 * @date 2020-09-26 17:22:38
 */
public class SiginForThirdService extends SiginService {

    private static final String QQ_DEFAULT_PWD = "123456";

    public ResultMsg loginForQQ(String openid) {
        System.out.println("从QQ登录获取到用户的openid: " + openid);
        System.out.println("以openid为默认用户号，默认登录密码为： " + QQ_DEFAULT_PWD);
        return registerAndLogin(openid, QQ_DEFAULT_PWD);
    }

    private ResultMsg registerAndLogin(String userId, String pwd) {
        super.register(userId, pwd);
        return super.login(userId, pwd);
    }
}
