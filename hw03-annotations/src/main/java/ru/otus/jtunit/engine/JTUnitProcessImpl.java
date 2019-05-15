package ru.otus.jtunit.engine;

import ru.otus.jtunit.annotations.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JTUnitProcessImpl implements JTUnitProcess {

    public JTUnitProcessImpl() {
    }

    @Override
    public void start(JTUnitRequest request) {
        for (JTClassSelector classSelector : request.getClassList()) {
            System.out.println("Processing Class: " + classSelector.getClassName() + "...");

            try {
                processClass(classSelector);
            } catch (InstantiationException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            System.out.println("\n");
        }

    }

    private <T> T newInstance(Class<T> clazz) {

        Constructor<T> declaredConstructor = null;
        T inst = null;

        try {
            declaredConstructor = clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (declaredConstructor != null) {
            try {
                declaredConstructor.setAccessible(true);
                inst = declaredConstructor.newInstance();


            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            } finally {
                declaredConstructor.setAccessible(false);
            }
        }
        return inst;
    }

    private void processClass(JTClassSelector classSelector) throws InstantiationException, NoSuchMethodException {
        Class<?> clazz = classSelector.getJavaClass();
        List<Method> methods = Arrays.asList(clazz.getDeclaredMethods());

        List<Method> beforeAllList = methods.stream().filter(method -> method.isAnnotationPresent(BeforeAll.class)).collect(Collectors.toList());
        List<Method> beforeEachList = methods.stream().filter(method -> method.isAnnotationPresent(BeforeEach.class)).collect(Collectors.toList());
        List<Method> testList = methods.stream().filter(method -> method.isAnnotationPresent(Test.class)).collect(Collectors.toList());
        List<Method> afterAllList = methods.stream().filter(method -> method.isAnnotationPresent(AfterAll.class)).collect(Collectors.toList());
        List<Method> afterEachList = methods.stream().filter(method -> method.isAnnotationPresent(AfterEach.class)).collect(Collectors.toList());

        boolean phaseSuccess = true;
        for (Method beforeAll : beforeAllList) {
            phaseSuccess = phaseSuccess && invokeMethod(beforeAll, new Object());
        }

        if (phaseSuccess) {

            for (Method test : testList) {
                phaseSuccess = true;
                Object testInstance = newInstance(clazz);

                for (Method beforeEach : beforeEachList) {
                    phaseSuccess = phaseSuccess && invokeMethod(beforeEach, testInstance);
                }

                if (phaseSuccess) invokeMethod(test, testInstance);

                for (Method afterEach : afterEachList) {
                    invokeMethod(afterEach, testInstance);
                }
            }
        }

        for (Method afterAll : afterAllList) {
            invokeMethod(afterAll, new Object());
        }
    }

    private boolean invokeMethod(Method method, Object instance) {
        boolean isSuccess = true;
        method.setAccessible(true);

        try {
            method.invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            isSuccess = false;
        } finally {
            method.setAccessible(false);
        }

        return isSuccess;
    }


}
