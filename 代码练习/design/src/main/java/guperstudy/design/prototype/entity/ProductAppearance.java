package guperstudy.design.prototype.entity;

import lombok.Getter;

import java.io.Serializable;

/**
 * @author joe
 * @program: design
 * @description: 产品外观 注意此对象也需要实现序列化接口
 * @date 2020-09-20 11:49:56
 */
@Getter
public class ProductAppearance implements Serializable {
    /**
     * 大小
     */
    private String size;
    /**
     * 颜色
     */
    private String color;

    public ProductAppearance(String size, String color) {
        this.size = size;
        this.color = color;
    }

    @Override
    public String toString() {
        return "ProductAppearance{" +
                "size='" + size + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
