package guperstudy.design.proxy.myjdkproxy.entity;

/**
 * @author joe
 * @program: design
 * @description: 汽车（被代理类）
 * @date 2020-09-20 15:25:16
 */
public class Car implements Vehicle {

    private String name = "Car";

    public String getName() {
        return name;
    }

    @Override
    public void launch() {
        System.out.println("Car is launching");
    }
}
