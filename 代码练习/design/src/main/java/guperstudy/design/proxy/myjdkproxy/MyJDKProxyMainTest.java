package guperstudy.design.proxy.myjdkproxy;

import guperstudy.design.proxy.myjdkproxy.entity.Car;
import guperstudy.design.proxy.myjdkproxy.entity.Trucks;
import guperstudy.design.proxy.myjdkproxy.entity.Vehicle;
import guperstudy.design.proxy.myjdkproxy.entity.VehicleProxy;

/**
 * @author joe
 * @program: design
 * @description: 自定义jdk代理测试类
 * @date 2020-09-20 15:56:10
 */
public class MyJDKProxyMainTest {
    public static void main(String[] args) {
        // VehicleProxy.getInstance(new Car());
        // 代理汽车
        Vehicle vehicle = (Vehicle) VehicleProxy.getInstance(new Car());
        vehicle.launch();

        // 代理货车
        Vehicle vehicle2 = (Vehicle) VehicleProxy.getInstance(new Trucks());
        vehicle2.launch();
    }
}
