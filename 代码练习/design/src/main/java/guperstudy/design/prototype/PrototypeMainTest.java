package guperstudy.design.prototype;

import guperstudy.design.prototype.entity.Product;
import guperstudy.design.prototype.entity.ProductAppearance;

/**
 * @author joe
 * @program: design
 * @description: 原型测试
 * @date 2020-09-20 11:52:07
 */
public class PrototypeMainTest {
    public static void main(String[] args) {
        ProductAppearance appearance = new ProductAppearance("M", "Red");
        Product productA = new Product("产品A", appearance);
        Product productB = (Product) productA.cloneProduct();
        System.out.println("产品A => " + productA);
        System.out.println("产品B => " + productB);
        System.out.println("两个产品对比 => " + (productA == productB) );
        System.out.println("两个产品的外观对比 => " + (productA.getAppearance() == productB.getAppearance()));
    }
}
