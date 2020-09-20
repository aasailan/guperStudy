package guperstudy.design.factory.abstractfactory;

import guperstudy.design.factory.products.note.INote;
import guperstudy.design.factory.products.note.PythonNote;
import guperstudy.design.factory.products.vedio.IVedio;
import guperstudy.design.factory.products.vedio.PythonVedio;

/**
 * @author joe
 * @program: design
 * @description: python系列产品工厂
 * @date 2020-09-13 18:06:36
 */
public class PythonFactory implements IFactory {
    private static final PythonFactory FACTORY = new PythonFactory();

    private PythonFactory() {}

    public static PythonFactory getInstance() {
        return FACTORY;
    }

    public INote createNote() {
        return new PythonNote();
    }

    public IVedio createVedio() {
        return new PythonVedio();
    }
}
