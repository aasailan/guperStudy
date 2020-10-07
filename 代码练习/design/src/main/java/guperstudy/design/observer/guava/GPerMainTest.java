package guperstudy.design.observer.guava;

import guperstudy.design.observer.gper.Question;

/**
 * @author joe
 * @program: design
 * @description: GPer测试类
 * @date 2020-10-07 16:38:16
 */
public class GPerMainTest {
    public static void main(String[] args) {
        GPer gPer = new GPer("qiao");

        Teacher teacher = new Teacher("Tom");

        gPer.register(teacher);

        gPer.publishQuestion(new Question("qiao", "观察者模式和发布订阅者模式有什么区别？"));

        // Teacher joe = new Teacher("Joe");
        // // 多线程测试
        // for (int i = 0; i < 10; i++) {
        //     Thread thread = new Thread() {
        //         @Override
        //         public void run() {
        //             gPer.register(joe);
        //             System.out.println("添加完joe老师监听");
        //             gPer.publishQuestion(new Question("qiao", "多线程触发事件怎么测试？"));
        //         }
        //     };
        //     thread.start();
        // }
    }
}
