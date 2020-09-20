package guperstudy.design.factory.factorymethod;

import guperstudy.design.factory.products.course.ICourse;
import guperstudy.design.factory.products.course.PythonCourse;

/**
 * @author joe
 * @program: design
 * @description: python课程工厂
 * @date 2020-09-13 17:48:35
 */
public class PythonCourseFactory implements ICourseFactory {

    private static final PythonCourseFactory FACTORY = new PythonCourseFactory();

    public static PythonCourseFactory getInstance() {
        return FACTORY;
    }

    public ICourse createCourse() {
        return new PythonCourse();
    }
}
