package guperstudy.design.proxy.myjdkproxy.proxy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URISyntaxException;

/**
 * @author joe
 * @program: design
 * @description: 自定义类加载器
 * @date 2020-09-20 15:53:38
 */
public class MyClassLoader extends ClassLoader {

    private File classPathFile;
    public MyClassLoader() {
        try {
            String classPath = MyClassLoader.class.getResource("").toURI().getPath();
            this.classPathFile = new File(classPath);
        } catch (Exception e) {
            System.out.println("classLoader 加载资源失败");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        String className = MyClassLoader.class.getPackage().getName() + "." + name;
        if(classPathFile  != null){
            File classFile = new File(classPathFile,name.replaceAll("\\.","/") + ".class");
            if(classFile.exists()){
                FileInputStream in = null;
                ByteArrayOutputStream out = null;
                try{
                    in = new FileInputStream(classFile);
                    out = new ByteArrayOutputStream();
                    byte [] buff = new byte[1024];
                    int len;
                    while ((len = in.read(buff)) != -1){
                        out.write(buff,0,len);
                    }
                    return defineClass(className,out.toByteArray(),0,out.size());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
