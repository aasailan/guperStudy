package guperstudy.design.strategy;

import guperstudy.design.strategy.entity.AliPay;
import guperstudy.design.strategy.entity.Payment;
import guperstudy.design.strategy.entity.WechatPay;

import java.util.HashMap;
import java.util.Map;

/**
 * @author joe
 * @program: design
 * @description: 支付策略类
 * @date 2020-09-20 15:05:13
 */
public class PayStrategy {
    /**
     * 支付策略容器
     */
    private static final Map<String, Payment> PAY_STRATEGY_MAP = new HashMap<String, Payment>() {
        {
            put(PayStrategyKey.ALI_PAY, new AliPay());
            put(PayStrategyKey.WECHAT_PAY, new WechatPay());
        }
    };
    /**
     * 支付策略名称
     */
    public interface PayStrategyKey {
        String ALI_PAY = "aliPay";
        String WECHAT_PAY = "wechatPay";
    }

    /**
     * 根据策略名称获取具体的支付策略
     * @param name 支付策略名称
     * @return
     */
    public static Payment getPayStrategy(String name) {
        Payment payment = PAY_STRATEGY_MAP.get(name);
        if (payment == null) {
            System.out.println("没有相关支付策略，请检查策略名称");
        }
        return payment;
    }
}
