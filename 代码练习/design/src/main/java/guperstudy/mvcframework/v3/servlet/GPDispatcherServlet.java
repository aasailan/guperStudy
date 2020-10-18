package guperstudy.mvcframework.v3.servlet;

import guperstudy.mvcframework.annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author joe
 * @program: design
 * @description: 自定义DispatcherServlet
 * @date 2020-10-18 10:12:35
 */
public class GPDispatcherServlet extends HttpServlet {

    /**
     * 配置文件配置对象
     */
    private final Properties contextConfig = new Properties();

    /**
     * 存储扫描包路径底下的所有class文件名
     */
    private final List<String> classNames = new ArrayList<>();

    /**
     * 简易ioc容器，用来存储bean实例 以类名为key，bean实例为value
     */
    private final Map<String, Object> ioc = new HashMap<>();
    /**
     * 存储所有的HandlerMapping
     */
    private final List<Handler> handlerMappings = new ArrayList<>();

    /**
     * 将url、controller实例，处理方法实例封装在一个对象中
     */
    private class Handler{
        /**
         * url正则对象
         */
        private Pattern pattern;
        /**
         * 处理目标url的bean对象
         */
        private Object handler;
        /**
         * 处理目标url的bean方法
         */
        private Method handlerMethod;
        /**
         * bean方法的 参数名(由GPRequestParam注解指定的request数据参数名) - 参数下标 映射
         */
        private Map<String, Integer> paramIndexMapping = new HashMap<>();

        Handler(Pattern pattern, Object handler, Method handlerMethod) {
            this.pattern = pattern;
            this.handler = handler;
            this.handlerMethod = handlerMethod;
            this.initParamIndexMapping(handlerMethod);
        }

        public Pattern getPattern() {
            return pattern;
        }

        public Method getHandlerMethod() {
            return handlerMethod;
        }

        public Object getHandler() {
            return handler;
        }

        public Map<String, Integer> getParamIndexMapping() {
            return paramIndexMapping;
        }

        private void initParamIndexMapping(Method method) {
            // 提取方法中添加了注解的参数 NOTE: 一个方法有多个参数，一个参数有多个注解
            Annotation[][] paramAnnotations = method.getParameterAnnotations();
            // 遍历参数注解
            for (int paramIndex = 0; paramIndex < paramAnnotations.length; paramIndex++) {
                for (Annotation annotation: paramAnnotations[paramIndex]) {
                    if (annotation instanceof GPRequestParam) {
                        String key = ((GPRequestParam) annotation).value();
                        if (!"".equals(key.trim())) {
                            paramIndexMapping.put(key, paramIndex);
                        }
                    }
                }
            }
            // 提取方法中的request和response参数，这两个参数优先级比注解高，所以需要做覆盖处理
            Class<?>[] paramTypes = method.getParameterTypes();
            for (int paramIndex = 0; paramIndex < paramTypes.length; paramIndex++) {
                Class<?> paramType = paramTypes[paramIndex];
                if (paramType == HttpServletRequest.class || paramType == HttpServletResponse.class) {
                    paramIndexMapping.put(paramType.getName(), paramIndex);
                }
            }
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 1、加载配置文件  contextConfigLocation参数配置在web.xml中
        doLoadConfig(config.getInitParameter("contextConfigLocation"));

        // 2、扫描指定目录下的class文件
        doScanPackage(contextConfig.getProperty("scanPackage"));

        // 3、初始化扫描类，放入IOC容器
        doInstance();

        // 4、完成依赖注入
        doAutowired();

        // 5、初始化HandlerMapping
        initHandlerMapping();

        System.out.println("GPDispatcherServlet init success");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            doDisPatch(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doDisPatch(HttpServletRequest req, HttpServletResponse resp) {

        try {
            Handler handler = getHandler(req);
            // 对于未找到handler进行处理的请求，返回404
            if (handler == null) {
                resp.setStatus(404);
                resp.getWriter().write("404 not found");
                return;
            }
            // 调用handler得到结果，写入resp
            // 将目标方法返回的值写入response的实现还比较粗糙。
            // 在真实的spring源码中，是通过modelAndView统一目标方法的返回，并通过一系列接口将modelAndView中的数据转换到response。
            Object result = invokeHandler(req, resp, handler);
            if (result == null || result instanceof Void) {
                return;
            }
            resp.getWriter().write(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object invokeHandler(HttpServletRequest req, HttpServletResponse resp, Handler handler) throws InvocationTargetException, IllegalAccessException {
        Method handerMethod = handler.getHandlerMethod();
        Map<String, Integer> paramOrderMap = handler.getParamIndexMapping();
        // 解析 GPRequestParam 注解 填充参数
        Class<?>[] paramTypes = handerMethod.getParameterTypes();
        Object[] params = new Object[paramTypes.length];
        Map<String, String[]> requestData = req.getParameterMap();
        // 遍历请求提交数据，先填充GPRequestParam注解指定的请求参数
        for (Map.Entry<String, String[]> dataEntry : requestData.entrySet()) {
            Integer index = paramOrderMap.get(dataEntry.getKey());
            if (index == null) {
                continue;
            }
            // NOTE: 同名的参数多个value组成的数组，转换为用 逗号 分割的字符串
            String value = Arrays.toString(dataEntry.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
            params[index] = convertParam(paramTypes[index], value);
        }
        // 填充方法参数中的request、response
        for (int paramIndex = 0; paramIndex < paramTypes.length; paramIndex++) {
            Class<?> paramType = paramTypes[paramIndex];
            if (paramType == HttpServletRequest.class) {
                params[paramIndex] = req;
            } else if (paramType == HttpServletResponse.class) {
                params[paramIndex] = resp;
            }
        }
        // 方法调用
        return handerMethod.invoke(handler.getHandler(), params);
    }

    private Object convertParam(Class<?> paramType, String value) {
        if (Integer.class == paramType) {
            return Integer.valueOf(value);
        }
        // TODO: 这里还需要考虑诸如 double等等其他类型。可以使用策略模式
        return value;
    }

    /**
     * 根据请求的url路径找到对应的handler实例
     * @param req 请求对象
     * @return handler
     */
    private Handler getHandler(HttpServletRequest req) {
        // https://www.cnblogs.com/xyzq/p/6093063.html
        String contextUrl = req.getContextPath();
        String url = req.getRequestURI();
        // 针对除去contextPath的请求url进行匹配，这样可以与 contextPath解耦
        url = url.replace(contextUrl, "").replaceAll("/+", "/");

        for (Handler handler : handlerMappings) {
            Matcher matcher = handler.getPattern().matcher(url);
            if (!matcher.matches()) { continue; }
            return handler;
        }
        return null;
    }

    /**
     * 加载application.properties文件
     * @param configLocation application.properties文件所在路径
     */
    private void doLoadConfig(String configLocation) {
        // 直接从类路径下找到Spring主配置文件所在的路径
        // 并且将其读取出来放到Properties对象中
        // 相对于scanPackage=com.gupaoedu.demo 从文件中保存到了内存中
        // TODO: 有空需要了解下 getResourceAsStream 方法，以及什么时候考虑使用这个方法
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(configLocation);) {
            contextConfig.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 扫描指定目录下的所有class文件，将class名称存入List
     * @param scanPackage 指定扫描目录
     */
    private void doScanPackage(String scanPackage) {
        // scanPackage = com.mvc.demo
        // 存储的是包路径 //转换为文件路径，实际上就是把.替换为/就 OK了
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.","/"));
        // 递归遍历包路径底下的class文件
        // 将url对应路径的文件夹封装成一个file对象
        assert url != null;
        File classPath = new File(url.getFile());
        for (File file : Objects.requireNonNull(classPath.listFiles())) {
            if (file.isDirectory()) {
                // 如果子文件是文件夹，则意味着是一个包，添加包名后，使用递归继续进行扫描
                doScanPackage(scanPackage + "." + file.getName());
            } else {
                // 如果文件不是class文件，则不做处理
                if (!file.getName().endsWith(".class")) { continue; }
                // 存储所有class文件的文件名，同时也是类名
                String className = file.getName().replace(".class", "").trim();
                // 拼接包名
                className = scanPackage + "." + className;
                classNames.add(className);
            }
        }
    }

    /**
     * 遍历指定目录的class，针对拥有bean注解的class进行初始化，并且存入ioc容器
     */
    private void doInstance() {
        try {
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);
                // 实际spring还存在许多其他注解能标记bean，并且不同bean的初始化模式用户可以配置。
                if (clazz.isAnnotationPresent(GPController.class)) {
                    // class存在 Controller注解，需要初始化，注册成为bean
                    Object controller = clazz.newInstance();
                    // TODO: 这个地方是否存在问题，应该用class的全名，而不是getSimpleName。避免有bean的类名相同
                    // TODO： 或者说spring中本就不允许controller的类名相同
                    // Spring默认类名首字母小写
                    String beanName = toLowerFirstCase(clazz.getSimpleName());
                    ioc.put(beanName, controller);
                } else if (clazz.isAnnotationPresent(GPService.class)) {
                    // class存在 Service注解，需要初始化，注册成为bean
                    Object service = clazz.newInstance();

                    // NOTE: 处理Service注解自定义bean名称
                    GPService serviceAnnotation = clazz.getAnnotation(GPService.class);
                    String beanName = serviceAnnotation.value();
                    if ("".equals(beanName.trim())) {
                        // 用service的类名作为bean名称（首字母小写）
                        beanName = toLowerFirstCase(clazz.getSimpleName());
                    }
                    ioc.put(beanName, service);
                    // service的全名作为key
                    ioc.put(clazz.getName(), service);
                    // NOTE: service可能会按照接口类型实现注入，所以需要将service实现的接口类型也注册成bean的类型。
                    // TODO: 如果类型注入是根据service的父类类型注入的怎么办？
                    for (Class<?> interfaceClass : clazz.getInterfaces()) {
                        String interfaceBeanName = interfaceClass.getName();
                        if (ioc.containsKey(interfaceBeanName)) {
                            throw new Exception(interfaceBeanName + "is exists!");
                        }
                        // NOTE: 这里使用接口的全名路径，是为了能够使用类型注解
                        ioc.put(interfaceBeanName, service);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 遍历ioc中的bean实例的field字段，对所有存在 autowired 注解的字段进行依赖注入
     */
    private void doAutowired() {
        try {
            // 遍历每一个bean的所有属性，检查是否存在 @autowired等注入性质的注解
            for (Map.Entry<String, Object> beanEntry : ioc.entrySet()) {
                Object bean = beanEntry.getValue();
                // NOTE: 为什么Field要通过class获取，field明明是跟类实例息息相关的
                // java对Field和Method的封装处理非常类似。都是以类为基础进行获取，只有在运行方法或者设置field的时候
                // 才会设置目标对象，与目标对象绑定在一起
                for (Field field : bean.getClass().getDeclaredFields()) {
                    // 判断field是否存在@autowired
                    if (!field.isAnnotationPresent(GPAutowired.class)) {
                        continue;
                    }
                    // 获取 @autowired注解期望注入的bean名称
                    String beanName = field.getAnnotation(GPAutowired.class).value();
                    if ("".equals(beanName)) {
                        // 如果注解没有指定bean名称，则需要通过field的类型来进行注入
                        beanName = field.getType().getName();
                    }
                    Object fieldBean = ioc.get(beanName);
                    if (fieldBean == null) {
                        throw new Exception(beanName + " is not exists!");
                    }
                    // 通过反射设置field可访问
                    field.setAccessible(true);
                    field.set(bean, fieldBean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 遍历ioc中的bean实例，对所有存在controller注解的bean解析其中的GPRequestMapping注解。
     * 创建url - handler的映射关系并存入handlerMappings
     */
    private void initHandlerMapping() {
        for (Map.Entry<String, Object> beanEntry : ioc.entrySet()) {
            Object bean = beanEntry.getValue();
            Class beanClazz = bean.getClass();
            if (!beanClazz.isAnnotationPresent(GPController.class)) {
                continue;
            }
            // 针对 controller bean进行处理
            String baseUrl = "";
            if (beanClazz.isAnnotationPresent(GPRequestMapping.class)) {
                // 获取 controller 上的GPRequestMapping注解定义的baseUrl
                baseUrl = ((GPRequestMapping)beanClazz.getAnnotation(GPRequestMapping.class)).value();
            }
            // 遍历所有有 GPRequestMapping 注解的方法，并进行登记
            for (Method beanMethod : beanClazz.getMethods()) {
                if (!beanMethod.isAnnotationPresent(GPRequestMapping.class)) { continue; }
                String url = "/" + baseUrl + beanMethod.getAnnotation(GPRequestMapping.class).value();
                // 处理url中可能存在的连续重复 //
                url = url.replaceAll("/+", "/");
                // TODO: 有空需要了解一下pattern与正则有什么关系
                Pattern pattern = Pattern.compile(url);
                handlerMappings.add(new Handler(pattern, bean, beanMethod));
                System.out.println("mapping " + url + "," + beanMethod);
            }
        }
    }

    private String toLowerFirstCase(String name) {
        char [] chars = name.toCharArray();
        chars[0] += 32;
        return new String(chars);
    }
}
