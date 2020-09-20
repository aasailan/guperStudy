package guperstudy.design.factory.factorymethod;

import guperstudy.design.factory.products.course.ICourse;
import guperstudy.design.factory.products.course.JavaCourse;

/**
 * @author joe
 * @program: design
 * @description: java课程工厂
 * @date 2020-09-13 17:47:17
 */
public class JavaCourseFactory implements ICourseFactory {

    private static final JavaCourseFactory FACTORY = new JavaCourseFactory();

    public static JavaCourseFactory getInstance() {
        return FACTORY;
    }

    public ICourse createCourse() {
        return new JavaCourse();
    }
}
