package guperstudy.design.proxy.myjdkproxy.proxy;
import guperstudy.design.proxy.myjdkproxy.entity.Vehicle;
import java.lang.reflect.*;
public class $Proxy0 implements guperstudy.design.proxy.myjdkproxy.entity.Vehicle{
private MyInvocationHandler h;
public $Proxy0(MyInvocationHandler h) { 
this.h = h;}
public void launch() {
try {
Method m = guperstudy.design.proxy.myjdkproxy.entity.Vehicle.class.getMethod("launch", new Class[]{ });
 this.h.invoke(this, m, new Object[]{});
}catch(Error _ex) { }catch(Throwable e){
throw new UndeclaredThrowableException(e);
}
}
}
