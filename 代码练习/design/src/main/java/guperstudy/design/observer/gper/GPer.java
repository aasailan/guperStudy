package guperstudy.design.observer.gper;

import java.util.Observable;

/**
 * @author joe
 * @program: design
 * @description: Gper学习人员，充当被观察者（事件发布者）NOTE: 通过继承Observable实现一个被观察者（事件发布者）
 * @date 2020-10-07 11:18:49
 */
public class GPer extends Observable {

    /**
     * 发布一个问题
     * @param question
     */
    public void publishQuestion(Question question) {
        System.out.println(question.getUserName() + "提出一个问题：" + question.getContent());
        // NOTE: 设置changed变量，在后续notifyObservers会加锁判断changed变量
        setChanged();
        // 通知所有观察者（教师），使用问题作为参数
        notifyObservers(question);
    }
}
