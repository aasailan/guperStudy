package guperstudy.design.proxy.myjdkproxy.entity;

/**
 * @author joe
 * @program: design
 * @description: 货车
 * @date 2020-09-20 15:30:41
 */
public class Trucks implements Vehicle {

    private String name = "Trucks";

    public String getName() {
        return name;
    }

    @Override
    public void launch() {
        System.out.println("Trucks is launching");
    }
}
