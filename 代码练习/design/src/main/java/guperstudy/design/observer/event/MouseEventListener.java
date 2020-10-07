package guperstudy.design.observer.event;

/**
 * @author joe
 * @program: design
 * @description: 鼠标事件监听器
 * @date 2020-10-07 15:55:06
 */
public class MouseEventListener implements Observer {
    @Override
    public void onEvent(Event event) {
        System.out.println("接收到鼠标事件：" + event);
        if (event.getParams() != null) {
            String paramsStr = "";
            Object[] params = event.getParams();
            for (int i = 0; i < params.length; i++) {
                paramsStr = paramsStr + params[i] + " ";
            }
            System.out.println("接收到事件参数为：" + paramsStr);
        }
    }
}
