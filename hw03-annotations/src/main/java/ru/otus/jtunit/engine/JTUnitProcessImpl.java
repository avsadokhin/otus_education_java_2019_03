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
                processor(classSelector);
            } catch (InstantiationException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            System.out.println("\n");
        }

    }

    private void processor(JTClassSelector classSelector) throws InstantiationException, NoSuchMethodException {
        Class<?> clazz = classSelector.getJavaClass();
        List<Method> methods = Arrays.asList(clazz.getDeclaredMethods());

        List<Method> beforeAllList = methods.stream().filter(method -> method.isAnnotationPresent(BeforeAll.class)).collect(Collectors.toList());
        List<Method> beforeEachList = methods.stream().filter(method -> method.isAnnotationPresent(BeforeEach.class)).collect(Collectors.toList());
        List<Method> testList = methods.stream().filter(method -> method.isAnnotationPresent(Test.class)).collect(Collectors.toList());
        List<Method> afterAllList = methods.stream().filter(method -> method.isAnnotationPresent(AfterAll.class)).collect(Collectors.toList());
        List<Method> afterEachList = methods.stream().filter(method -> method.isAnnotationPresent(AfterEach.class)).collect(Collectors.toList());

        final Constructor<?> declaredConstructor = clazz.getDeclaredConstructor();
        Object instance = null;

        for (Method beforeAll : beforeAllList) {
            invokeMethod(beforeAll, instance);
        }

        if (declaredConstructor != null) {
            try {
                declaredConstructor.setAccessible(true);
                instance = declaredConstructor.newInstance();
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            } finally {
                declaredConstructor.setAccessible(false);
            }
        }


        for (Method test : testList) {
            for (Method beforeEach : beforeEachList) {
                invokeMethod(beforeEach, instance);
            }
            invokeMethod(test, instance);

            for (Method afterEach : afterEachList) {
                invokeMethod(afterEach, instance);
            }
        }

        for (Method afterAll : afterAllList) {
            invokeMethod(afterAll, instance);
        }
    }

    private void invokeMethod(Method metod, Object instance) {

        metod.setAccessible(true);
        try {
            metod.invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            metod.setAccessible(false);
        }

    }

}
