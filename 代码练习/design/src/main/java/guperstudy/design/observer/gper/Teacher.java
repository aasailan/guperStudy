package guperstudy.design.observer.gper;

import java.util.Observable;
import java.util.Observer;

/**
 * @author joe
 * @program: design
 * @description: GPer教师 NOTE: 通过实现Observer接口来实现一个监听者
 * @date 2020-10-07 11:26:41
 */
public class Teacher implements Observer {

    private String name;

    public Teacher(String name) {
        this.name = name;
    }

    /**
     * 监听者回调方法
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        Question question = (Question) arg;
        System.out.println(name + "老师收到一个问题: " + question);
    }
}
