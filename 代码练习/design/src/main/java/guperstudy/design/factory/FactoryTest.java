package guperstudy.design.factory;

import guperstudy.design.factory.abstractfactory.JavaFactory;
import guperstudy.design.factory.products.course.JavaCourse;
import guperstudy.design.factory.factorymethod.ICourseFactory;
import guperstudy.design.factory.factorymethod.JavaCourseFactory;
import guperstudy.design.factory.products.note.JavaNote;
import guperstudy.design.factory.products.vedio.JavaVedio;
import guperstudy.design.factory.simplefactory.SimpleFactory;

/**
 * @author joe
 * @program: design
 * @description: 工厂测试
 * @date 2020-09-13 17:36:46
 */
public class FactoryTest {
    public static void main(String[] args) {
        testSimpleFactory();
        testFactoryMethod();
        testAbstractFactory();
    }

    private static void testSimpleFactory() {
        JavaCourse javaCourse = (JavaCourse) SimpleFactory.createCourse("java");
        javaCourse.record();
    }

    private static void testFactoryMethod() {
        ICourseFactory factory = JavaCourseFactory.getInstance();
        JavaCourse javaCourse = (JavaCourse) factory.createCourse();
        javaCourse.record();
    }

    private static void testAbstractFactory() {
        JavaFactory javaFactory = JavaFactory.getInstance();
        JavaNote javaNote = (JavaNote) javaFactory.createNote();
        javaNote.edit();
        JavaVedio javaVedio = (JavaVedio) javaFactory.createVedio();
        javaVedio.record();
    }
}
