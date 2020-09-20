package guperstudy.design.factory.factorymethod;

import guperstudy.design.factory.products.course.ICourse;

/**
 * @author joe
 * @program: design
 * @description: 课程工厂接口
 * @date 2020-09-13 17:46:56
 */
public interface ICourseFactory {
    ICourse createCourse();
}
