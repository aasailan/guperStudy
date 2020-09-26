package guperstudy.design.adapter.loginadapter.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author joe
 * @program: design
 * @description: 登录信息结果
 * @date 2020-09-26 17:15:14
 */
@Setter
@Getter
public class ResultMsg {

    public static final int SUCCESS_CODE = 0;

    private int code;
    private String msg;
    private Object data;

    public ResultMsg() {
        this.code = SUCCESS_CODE;
        this.msg = "登录成功";
    }

    public ResultMsg(int code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
