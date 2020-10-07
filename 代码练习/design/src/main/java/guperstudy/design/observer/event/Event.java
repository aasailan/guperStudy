package guperstudy.design.observer.event;

import lombok.Getter;
import lombok.Setter;

/**
 * @author joe
 * @program: design
 * @description: 事件类
 * @date 2020-10-07 12:53:48
 */
@Setter
@Getter
public class Event {
    private Object[] params;
    /**
     * 事件名称
     */
    private String eventName;
    /**
     * 事件产生时间
     */
    private long time;

    public Event(String eventName) {
        new Event(eventName, null);
    }

    public Event(String eventName, Object[] params) {
        this.eventName = eventName;
        this.params = params;
        time = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Event{" +
                ", eventName='" + eventName + '\'' +
                ", time=" + time +
                '}';
    }
}
