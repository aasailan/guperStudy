package guperstudy.design.factory.products.course;

/**
 * @author joe
 * @program: design
 * @description: Python课程
 * @date 2020-09-13 17:33:31
 */
public class PythonCourse implements ICourse {
    public void record() {
        System.out.println("录制Python课程");
    }
}
