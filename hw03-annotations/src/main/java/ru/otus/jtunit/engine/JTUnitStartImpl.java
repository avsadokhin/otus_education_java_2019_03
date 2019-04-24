package ru.otus.jtunit.engine;

import ru.otus.jtunit.annotations.BeforeAll;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JTUnitStartImpl implements JTUnitStarter {

    public JTUnitStartImpl() {
    }

    @Override
    public void start(JTUnitRequest request) {


    }

    private void JTClassProcessor(JTClassSelector classSelector){
        Class<?> clazz = classSelector.getJavaClass();
        List<Method> methods =  Arrays.asList(clazz.getDeclaredMethods());

        List<Method> tests = methods.stream().filter(method -> method.isAnnotationPresent(BeforeAll.class)).collect(Collectors.toList());


    }


}
