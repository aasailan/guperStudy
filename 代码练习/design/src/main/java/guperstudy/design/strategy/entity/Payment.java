package guperstudy.design.strategy.entity;

/**
 * @author joe
 * @program: design
 * @description: 支付接口
 * @date 2020-09-20 14:59:02
 */
public interface Payment {
    /**
     * 获取支付方式名称
     * @return
     */
    public String getPayType();

    /**
     * 执行支付动作
     * @param order 支付订单
     * @return true 支付成功； false 支付失败
     */
    public boolean pay(Order order);
}
