package guperstudy.design.factory.abstractfactory;

import guperstudy.design.factory.products.note.INote;
import guperstudy.design.factory.products.vedio.IVedio;

/**
 * @author joe
 * @program: design
 * @description: 抽象工厂接口
 * @date 2020-09-13 18:03:26
 */
public interface IFactory {
    /**
     * 创建笔记对象
     * @return
     */
    INote createNote();

    /**
     * 创建视频对象
     * @return
     */
    IVedio createVedio();
}
