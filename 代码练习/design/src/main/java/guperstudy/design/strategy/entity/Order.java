package guperstudy.design.strategy.entity;

import lombok.Getter;

/**
 * @author joe
 * @program: design
 * @description: 订单类
 * @date 2020-09-20 14:54:05
 */
@Getter
public class Order {
    /**
     * 订单id
     */
    private String id;
    /**
     * 订单描述
     */
    private String desc;
    /**
     * 订单金额
     */
    private String amount;

    public Order(String id, String desc, String amount) {
        this.id = id;
        this.desc = desc;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", desc='" + desc + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
