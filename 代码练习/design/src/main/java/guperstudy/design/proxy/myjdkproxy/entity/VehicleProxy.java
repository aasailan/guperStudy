package guperstudy.design.proxy.myjdkproxy.entity;

import guperstudy.design.proxy.myjdkproxy.proxy.MyClassLoader;
import guperstudy.design.proxy.myjdkproxy.proxy.MyInvocationHandler;
import guperstudy.design.proxy.myjdkproxy.proxy.MyJDKProxy;

import java.lang.reflect.Method;

/**
 * @author joe
 * @program: design
 * @description: 交通工具代理类
 * @date 2020-09-20 15:36:53
 */
public class VehicleProxy implements MyInvocationHandler {

    private Object target;

    private VehicleProxy(Object target) {
        this.target = target;
    }

    /**
     * 动态生成一个代理类
     * @param target
     * @return
     */
    public static Object getInstance(Object target) {
        VehicleProxy vehicleProxy = new VehicleProxy(target);
        Class clazz = target.getClass();
        return MyJDKProxy.newProxyInstance(new MyClassLoader(), clazz.getInterfaces(), vehicleProxy);
    }

    /**
     * 代理类拦截方法
     * @param proxy
     * @param method 当前被调用方法
     * @param args 被调用方法入参
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object obj = method.invoke(target, args);
        after();
        return obj;
    }

    private void before() {
        System.out.println("启动前准备...");
    }

    private void after() {
        System.out.println("启动后准备...");
    }
}
