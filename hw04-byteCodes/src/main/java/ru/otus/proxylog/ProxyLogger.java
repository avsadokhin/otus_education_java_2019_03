package ru.otus.proxylog;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ProxyLogger {

    private ProxyLogger() {
    }

    public static  Object build(Class iface, Object impl)  {
        Set<Method> methods = Set.of(impl.getClass().getDeclaredMethods());
        Set<Method> methodsLog = methods.stream().filter(method -> method.isAnnotationPresent(Log.class)).collect(Collectors.toSet());

        ProxyLoggerInvocationHandler handler = new ProxyLoggerInvocationHandler(impl, methodsLog);

        return Proxy.newProxyInstance(impl.getClass().getClassLoader(), new Class[]{iface}, handler);
    }

    private static class ProxyLoggerInvocationHandler implements InvocationHandler {
        private final Object impl;
        private final Set<Method> logMethods;

        private ProxyLoggerInvocationHandler(Object impl, Set<Method> logMethods) {
            this.impl = impl;
            this.logMethods = logMethods;
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
          /*  Method m = impl.getClass().getMethod(method.getName(), method.getParameterTypes());
            System.out.println(method.getName());
            System.out.println(m.getName());*/

          //Так сравнивать нельзя. Разобраться здесь..
          if (logMethods.contains(method)) {
                System.out.println(Arrays.stream(args).toString());
            }

            Object invokeObj = null;
            try {invokeObj = method.invoke(impl, args);}
            catch(Exception ex){

                throw ex;
            }
            return invokeObj;
        }
    }

}
