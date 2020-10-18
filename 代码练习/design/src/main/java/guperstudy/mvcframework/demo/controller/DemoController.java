package guperstudy.mvcframework.demo.controller;

import guperstudy.mvcframework.annotation.GPAutowired;
import guperstudy.mvcframework.annotation.GPController;
import guperstudy.mvcframework.annotation.GPRequestMapping;
import guperstudy.mvcframework.annotation.GPRequestParam;
import guperstudy.mvcframework.demo.service.IDemoService;
import guperstudy.mvcframework.demo.service.impl.DemoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author joe
 * @program: design
 * @description: 测试控制器
 * @date 2020-10-18 17:51:21
 */
@GPController
@GPRequestMapping("/query")
public class DemoController {

    @GPAutowired
    private IDemoService demoService;

    @GPAutowired("demoService")
    private IDemoService demoService2;

    @GPAutowired
    private DemoService demoService3;

    @GPRequestMapping("/query")
    public String query(HttpServletRequest req, @GPRequestParam("name") String name){
        return demoService.get(name);
    }

    @GPRequestMapping("/query2")
    public String query2(HttpServletRequest req, @GPRequestParam("name") String name){
        return demoService2.get(name);
    }

    @GPRequestMapping("/query3")
    public String query3(HttpServletRequest req, @GPRequestParam("name") String name){
        return demoService3.get(name);
    }


    @GPRequestMapping("/add")
    public String add(HttpServletRequest req, @GPRequestParam("a") Integer a, @GPRequestParam("b") Integer b){
        return a + "+" + b + "=" + (a + b);
    }

    @GPRequestMapping("/remove")
    public void remove(HttpServletRequest req,HttpServletResponse resp,
                       @GPRequestParam("id") Integer id){
    }
}
