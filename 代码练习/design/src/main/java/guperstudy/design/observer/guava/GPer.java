package guperstudy.design.observer.guava;

import com.google.common.eventbus.EventBus;
import guperstudy.design.observer.gper.Question;

/**
 * @author joe
 * @program: design
 * @description: 咕泡学习者
 * @date 2020-10-07 16:29:51
 */
public class GPer extends EventBus {
    private String name;

    public GPer(String name) {
        this.name = name;
    }

    /**
     * 学习者提出问题
     * @param question
     */
    public void publishQuestion(Question question) {
        System.out.println(name + "提出了一个问题: " + question);
        super.post(question);
    }
}
