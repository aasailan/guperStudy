package guperstudy.design.observer.gper;

/**
 * @author joe
 * @program: design
 * @description: GPer观察者模式测试
 * @date 2020-10-07 11:34:52
 */
public class GPerMainTest {
    public static void main(String[] args) {
        GPer gPer = new GPer();
        Teacher tom = new Teacher("tom");
        // 注册observer
        gPer.addObserver(tom);

        Question question = new Question("qiao", "观察者模式和发布订阅者模式是否有区别？");
        // 发布事件
        gPer.publishQuestion(question);
    }
}
