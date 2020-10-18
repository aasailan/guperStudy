package guperstudy.mvcframework.demo.service.impl;

import guperstudy.mvcframework.annotation.GPService;
import guperstudy.mvcframework.demo.service.IDemoService;

/**
 * @author joe
 * @program: design
 * @description: 测试服务
 * @date 2020-10-18 17:53:15
 */
@GPService
public class DemoService implements IDemoService {

    @Override
    public String get(String name) {
        return "My name is " + name;
    }
}
