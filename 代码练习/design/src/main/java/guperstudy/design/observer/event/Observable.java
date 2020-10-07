package guperstudy.design.observer.event;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author joe
 * @program: design
 * @description: 被观察者，事件发布者
 * @date 2020-10-07 13:00:47
 */
public class Observable {
    /**
     * 容器需要考虑线程同步
     */
    private final Map<String, List<Observer>> eventsMap = new ConcurrentHashMap<>();

    private boolean changed = false;

    public void setChanged() { changed = true; }

    public void clearChanged() { changed = false; }

    public synchronized void addEventListener(String eventName, Observer ob) {
        List<Observer> obs;
        if (!eventsMap.containsKey(eventName)) {
            obs = new Vector<Observer>();
            eventsMap.put(eventName, obs);
        } else {
            obs = eventsMap.get(eventName);
        }
        if (!obs.contains(ob)) {
            obs.add(ob);
        }
    }

    public synchronized void removeEventListener(String eventName, Observer ob) {
        List<Observer> obs = eventsMap.get(eventName);
        if (obs == null) {
            return;
        }
        obs.remove(ob);
    }

    public void notify(String eventName, Object ...args) {
        synchronized (this) {
            if (!changed) {
                return;
            }
            clearChanged();
        }
        List<Observer> obs = eventsMap.get(eventName);
        if (obs == null) {
            return;
        }
        // 生成事件对象
        Event event = new Event(eventName, args);
        // TODO: 为什么数组对象进行类型转换时，总是会报错？
        // MouseEventListener[] tempObs = (MouseEventListener[]) obs.toArray();
        Object[] tempObs = obs.toArray();
        for (int i = 0; i < tempObs.length; i++) {
            ((Observer)tempObs[i]).onEvent(event);
        }
    }
}
