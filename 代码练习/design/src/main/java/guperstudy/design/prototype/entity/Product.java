package guperstudy.design.prototype.entity;

import lombok.Getter;

import java.io.*;
import java.util.Date;

/**
 * @author joe
 * @program: design
 * @description: 逻辑
 * @date 2020-09-20 11:33:07
 */
@Getter
public class Product implements Serializable {
    /**
     * 产品名称
     */
    private String name;
    /**
     * 产品创建时间
     */
    private Date createTime = new Date();
    /**
     * 产品外观描述
     */
    private ProductAppearance appearance;

    public Product(String name, ProductAppearance appearance) {
        this.name = name;
        this.appearance = appearance;
    }

    /**
     * 对外暴露clone接口，此接口为自实现clone，无需实现cloneable接口
     * @return
     */
    public Object cloneProduct() {
        return this.deepClone();
    }

    /**
     * 通过将对象序列化再反序列化的方式
     * @return
     */
    private Object deepClone() {
        Product cloneProduct = null;
        try {
            // 序列化对象
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);

            // 读取对象
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
            cloneProduct = (Product) ois.readObject();
            // 更新clone对象的创建时间
            cloneProduct.createTime = new Date();
        } catch (Exception e) {
            System.out.println("clone对象失败");
            e.printStackTrace();
        }
        return cloneProduct;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", createTime=" + createTime +
                ", appearance=" + appearance +
                '}';
    }
}
