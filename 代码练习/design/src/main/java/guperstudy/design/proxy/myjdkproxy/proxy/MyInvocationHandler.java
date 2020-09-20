package guperstudy.design.proxy.myjdkproxy.proxy;

import java.lang.reflect.Method;

/**
 * @author joe
 * @program: design
 * @description: 自定义代理类抽象接口
 * @date 2020-09-20 15:38:04
 */
public interface MyInvocationHandler {
    /**
     * 自定义代理类实现代理接口，用户通过实现此方法实现自定义动态代理的方法拦截
     * @param proxy
     * @param method 当前被调用方法
     * @param args 被调用方法入参
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable;
}
