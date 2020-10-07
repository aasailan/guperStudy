package guperstudy.design.observer.event;

/**
 * @author joe
 * @program: design
 * @description: 事件监听者
 * @date 2020-10-07 12:56:58
 */
public interface Observer {
    /**
     * 事件回调函数
     * @param event 事件对象
     */
    void onEvent(Event event);
}
