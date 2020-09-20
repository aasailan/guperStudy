package guperstudy.design.strategy.entity;

/**
 * @author joe
 * @program: design
 * @description: 支付宝支付
 * @date 2020-09-20 15:01:21
 */
public class AliPay implements Payment {
    public String getPayType() {
        return "支付宝支付";
    }

    public boolean pay(Order order) {
        System.out.println("支付类型: " + getPayType());
        System.out.println("订单信息为：" + order);
        System.out.println("支付完成");
        return true;
    }
}
