package guperstudy.design.factory.abstractfactory;

import guperstudy.design.factory.products.note.INote;
import guperstudy.design.factory.products.note.JavaNote;
import guperstudy.design.factory.products.vedio.IVedio;
import guperstudy.design.factory.products.vedio.JavaVedio;

/**
 * @author joe
 * @program: design
 * @description: java系列产品工厂
 * @date 2020-09-13 18:04:44
 */
public class JavaFactory implements IFactory {

    private static final JavaFactory FACTORY = new JavaFactory();

    private JavaFactory() {}

    public static JavaFactory getInstance() {
        return FACTORY;
    }

    public INote createNote() {
        return new JavaNote();
    }

    public IVedio createVedio() {
        return new JavaVedio();
    }
}
