package ru.otus.main;

import ru.otus.jtunit.engine.JTUnitRequest;
import ru.otus.jtunit.engine.JTUnitRequest.JTRequestBuilder;
import ru.otus.jtunit.engine.JTUnitStartImpl;
import ru.otus.jtunit.engine.JTUnitStarter;

import static ru.otus.jtunit.engine.JTUnitRequest.selectClass;

public class JTExecute {
    public static void main(String[] args) {
        JTRequestBuilder requestBuilder = JTUnitRequest.makeRequest().setClassList(selectClass(TestJTUnit1.class), selectClass(TestJTUnit2.class));
        JTUnitRequest request = requestBuilder.build();

        System.out.println(request.getClassList());
        request.getClassList().forEach(jtClassSelector -> System.out.println(jtClassSelector.getClassName()));

        JTUnitStarter starter = new JTUnitStartImpl();
        starter.start(request);


    }
}
