package guperstudy.design.observer.event;

/**
 * @author joe
 * @program: design
 * @description: 鼠标事件，可以添加监听
 * @date 2020-10-07 15:43:40
 */
public class Mouse extends Observable {

    public interface EventName {
        String CLICK = "click";
        String OVER = "over";
    }

    /**
     * 触发点击事件
     * @param args 点击事件参数
     */
    public void click(Object ...args) {
        System.out.println("用户点击");
        this.notify(EventName.CLICK, new Event(EventName.CLICK, args));
    }

    /**
     * 触发悬停事件
     * @param args 悬停事件参数
     */
    public void over(Object ...args) {
        System.out.println("用户悬停");
        this.notify(EventName.OVER, new Event(EventName.OVER, args));
    }
}
