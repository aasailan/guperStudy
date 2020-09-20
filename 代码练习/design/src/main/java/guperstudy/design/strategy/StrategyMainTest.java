package guperstudy.design.strategy;

import guperstudy.design.strategy.entity.Order;
import guperstudy.design.strategy.entity.Payment;

/**
 * @author joe
 * @program: design
 * @description: 策略类测试
 * @date 2020-09-20 15:04:42
 */
public class StrategyMainTest {
    public static void main(String[] args) {
        Order order = new Order("1", "测试订单", "100");

        Payment payment = PayStrategy.getPayStrategy(PayStrategy.PayStrategyKey.ALI_PAY);
        payment.pay(order);
    }
}
