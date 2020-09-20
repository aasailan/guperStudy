package guperstudy.design.strategy.entity;

/**
 * @author joe
 * @program: design
 * @description: 微信支付
 * @date 2020-09-20 15:03:04
 */
public class WechatPay implements Payment {
    public String getPayType() {
        return "微信支付";
    }

    public boolean pay(Order order) {
        System.out.println("支付类型: " + getPayType());
        System.out.println("订单信息为：" + order);
        System.out.println("支付完成");
        return true;
    }
}
