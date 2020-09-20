package guperstudy.design.proxy.myjdkproxy.proxy;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;

/**
 * @author joe
 * @program: design
 * @description: 自定义jdk动态代理
 * @date 2020-09-20 15:34:26
 */
public class MyJDKProxy {

    public static final String ln = "\n";

    public static Object newProxyInstance(MyClassLoader classLoader, Class[] interfaces, MyInvocationHandler userProxy) {
        try {
            // 生成动态代理类的java代码
            String src = createProxySrc(interfaces);
            // 将类写入磁盘
            File file = writeProxySrc(src);
            // 编译java文件
            compileProxy(file);
            // 加载动态代理类并返回动态
            return loadProxy(classLoader, userProxy);
        } catch (Exception e) {
            System.out.println("生成代理类失败");
            e.printStackTrace();
        }
        return null;
    }

    private static String createProxySrc(Class[] interfaces) {
        StringBuffer sb = new StringBuffer();
        // NOTE: 目前版本只对被代理对象的第一个实现接口进行代理
        Class implInterface = interfaces[0];
        // 导入包
        sb.append("package guperstudy.design.proxy.myjdkproxy.proxy;" + ln);
        sb.append("import guperstudy.design.proxy.myjdkproxy.entity.Vehicle;" + ln);
        sb.append("import java.lang.reflect.*;" + ln);
        // 类声明 NOTE: 后续需要改成遍历接口，实现所有接口
        sb.append("public class $Proxy0 implements " + implInterface.getName() + "{" + ln);
        // 用户自定义代理类字段属性
        sb.append("private MyInvocationHandler h;" + ln);
        // 定义构造函数
        sb.append("public $Proxy0(MyInvocationHandler h) { " + ln);
        sb.append("this.h = h;");
        sb.append("}" + ln);
        // 遍历实现接口，重写其中的所有接口方法 NOTE: 后续需要改造成重写所有接口的方法
        for (Method method : implInterface.getMethods()) {

            StringBuffer[] paraminfos = getMethodParamsInfo(method);
            StringBuffer methodParams = paraminfos[0];
            StringBuffer paramClasses = paraminfos[1];
            StringBuffer paramValues = paraminfos[2];

            // 定义方法签名
            sb.append("public " + method.getReturnType().getName() + " " + method.getName() + "(" + methodParams.toString() + ") {" + ln);
            // 定义方法体
            sb.append("try {" + ln);
            // 获取目标接口方法对象
            sb.append("Method m = " + implInterface.getName() + ".class.getMethod(\"" + method.getName() + "\", new Class[]{ " + paramClasses.toString() + "});" + ln);
            // 调用自定义代理接口方法invoke，并传入目标接口方法等相关参数
            sb.append(hasReturnType(method) ? ("return "  + tranReturnCase(method)) : ""  + " this.h.invoke(this, m, new Object[]{" + paramValues.toString() + "});" + ln);
            // catch闭合
            sb.append("}catch(Error _ex) { }");
            sb.append("catch(Throwable e){" + ln);
            sb.append("throw new UndeclaredThrowableException(e);" + ln);
            sb.append("}");
            // 最后返回
            sb.append(hasReturnType(method) ? "return null;" : "").append(ln);
            // 方法体闭合
            sb.append("}" + ln);
        }
        // 闭合类
        sb.append("}" + ln);
        return sb.toString();
    }

    /**
     * 将源码字符串写入磁盘
     * @param code
     * @throws IOException
     */
    private static File writeProxySrc(String code) throws IOException, URISyntaxException {
        // 获取当前jdk代理类的资源路径
        String filePath = MyJDKProxy.class.getResource("").toURI().getPath();
        System.out.println(filePath);
        File f = new File(filePath + "$Proxy0.java");
        FileWriter fw = new FileWriter(f);
        fw.write(code);
        fw.flush();
        fw.close();
        return f;
    }

    private static Object loadProxy(MyClassLoader classLoader, MyInvocationHandler userProxy) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        Class proxyClass =  classLoader.findClass("$Proxy0");
        Constructor c = proxyClass.getConstructor(MyInvocationHandler.class);
        return c.newInstance(userProxy);
    }

    /**
     * 编译java文件
     * @throws IOException
     */
    private static void compileProxy(File file) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager manage = compiler.getStandardFileManager(null,null,null);
        // 编译java文件
        Iterable iterable = manage.getJavaFileObjects(file);

        JavaCompiler.CompilationTask task = compiler.getTask(null,manage,null,null,null,iterable);
        task.call();
        manage.close();
    }

    /**
     * 获取方法返回类型
     * @param method 方法对象
     * @return true 有返回值 false 无返回值
     */
    private static boolean hasReturnType(Method method) {
        Class<?> returnType = method.getReturnType();
        return returnType != void.class;
    }

    /**
     * 根据方法返回类型生成类型转换代码
     * @param method
     * @return
     */
    private static String tranReturnCase(Method method) {
        if (hasReturnType(method)) {
            return "(" + method.getReturnType().getName() + ")";
        }
        return "";
    }

    /**
     * 获取一个方法的参数相关信息
     * @param method 方法对象
     * @return StringBuffer[0] 方法参数签名 StringBuffer[1] 方法参数类型列表 StringBuffer[2]
     */
    private static StringBuffer[] getMethodParamsInfo(Method method) {
        StringBuffer[] result = new StringBuffer[3];
        // 方法参数签名
        StringBuffer paramsSb = new StringBuffer();
        // 方法参数类型列表
        StringBuffer classSb = new StringBuffer();
        // 方法参数值列表
        StringBuffer valueSb = new StringBuffer();
        // 获取方法的参数类型组成的数组
        Class<?>[] paramTypes = method.getParameterTypes();
        method.getParameters();
        for (int i = 0; i < paramTypes.length; i++) {
            Class paramType = paramTypes[i];
            String type = paramType.getName();
            String paramName = paramType.getSimpleName().toLowerCase();

            paramsSb.append(type + " " + paramName);

            classSb.append(paramType.getName() + ".class");

            valueSb.append(paramName);
            // 在参数中间补充逗号
            if (i < paramTypes.length - 1) {
                paramsSb.append(", ");
                classSb.append(", ");
                valueSb.append(", ");
            }
        }
        result[0] = paramsSb;
        result[1] = classSb;
        result[2] = valueSb;
        return result;
    }
}
