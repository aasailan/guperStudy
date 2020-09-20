package guperstudy.design.factory.simplefactory;

import guperstudy.design.factory.products.course.ICourse;
import guperstudy.design.factory.products.course.JavaCourse;
import guperstudy.design.factory.products.course.PythonCourse;

/**
 * @author joe
 * @program: design
 * @description: 简单工厂
 * @date 2020-09-13 17:29:28
 */
public class SimpleFactory {

    private static final String JAVA = "java";

    private static final String PYTHON = "python";

    public static ICourse createCourse(String type) {
        if (JAVA.equals(type)) {
            return new JavaCourse();
        } else if (PYTHON.equals(type)) {
            return new PythonCourse();
        } else {
            return null;
        }
    }
}
