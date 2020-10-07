package guperstudy.design.observer.guava;

import com.google.common.eventbus.Subscribe;
import guperstudy.design.observer.gper.Question;

/**
 * @author joe
 * @program: design
 * @description: 鼓泡学院教师
 * @date 2020-10-07 16:34:57
 */
public class Teacher {
    private String name;

    public Teacher(String name) {
        this.name = name;
    }

    /**
     * 使用@Subscribe注解将此方法标记为订阅回调方法
     * @param arg 事件参数
     */
    @Subscribe
    public void update(Object arg) {
        Question question = (Question) arg;
        System.out.println(name + "老师收到一个问题: " + question);
    }
}
