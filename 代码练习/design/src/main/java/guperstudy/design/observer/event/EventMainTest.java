package guperstudy.design.observer.event;

/**
 * @author joe
 * @program: design
 * @description: 事件测试
 * @date 2020-10-07 15:42:08
 */
public class EventMainTest {
    public static void main(String[] args) {
        Mouse mouse = new Mouse();
        // 设置Click监听
        mouse.addEventListener(Mouse.EventName.CLICK, new MouseEventListener());
        // 设置鼠标悬停监听
        mouse.addEventListener(Mouse.EventName.OVER, new MouseEventListener());

        // 触发点击事件
        mouse.setChanged();
        mouse.notify(Mouse.EventName.CLICK);
        // 触发悬停事件
        mouse.setChanged();
        mouse.notify(Mouse.EventName.OVER, new String[] { "param1", "param2" });
    }
}
