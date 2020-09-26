package guperstudy.design.adapter;

import guperstudy.design.adapter.loginadapter.SiginForThirdService;

/**
 * @author joe
 * @program: design
 * @description: 登录适配器测试类
 * @date 2020-09-26 17:11:48
 */
public class LoginAdapterMainTest {
    public static void main(String[] args) {
        SiginForThirdService service = new SiginForThirdService();
        service.loginForQQ("1111111111");
    }
}
