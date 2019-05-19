package ru.otus.proxylog;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ProxyLogger {

    private ProxyLogger() {
    }

    public static Object build( Object impl) {
        Set<Method> methodsAll = Set.of(impl.getClass().getDeclaredMethods());
        Set<Method> methodsLog = methodsAll.stream().filter(method -> method.isAnnotationPresent(Log.class)).collect(Collectors.toSet());

        ProxyLoggerInvocationHandler handler = new ProxyLoggerInvocationHandler(impl, methodsLog, getMethodsLogSignature(methodsLog));

        return Proxy.newProxyInstance(impl.getClass().getClassLoader(), impl.getClass().getInterfaces(), handler);
    }

    private static Set<String> getMethodsLogSignature(Set<Method> methods) {
        Set<String> retSet = new HashSet<>();

        methods.stream().collect(Collectors.toSet()).forEach(method -> retSet.add(getMethodsLogSignature(method)));
        return retSet;
    }

    private static String getMethodsLogSignature(Method method) {
        return method.getName() + ":" + Arrays.stream(method.getParameterTypes()).map(Class::toString).collect(Collectors.joining("-"));
    }

    private static class ProxyLoggerInvocationHandler implements InvocationHandler {
        private final Object impl;
        private final Set<String> logMethodsSignature;

        private ProxyLoggerInvocationHandler(Object impl, Set<Method> logMethods, Set<String> logMethodsSignature) {
            this.impl = impl;
            this.logMethodsSignature = logMethodsSignature;
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if (logMethodsSignature.contains(getMethodsLogSignature(method))) {
                System.out.println("Running Log before method: " + method.getName()+", Params: " + method.getParameters().length);
            }

            Object invokeObj;
            try {
                long startTime = System.currentTimeMillis();
                invokeObj = method.invoke(impl, args);
                System.out.println("Method execution time sec: " + getElapsedFromStartTime(startTime) + "\n");
            } catch (Exception ex) {

                throw ex;
            }

            return invokeObj;
        }

        private double getElapsedFromStartTime(long startTime) {
            long endTime = System.currentTimeMillis();
            return (double) (endTime - startTime) / (1000);
        }
    }

}
