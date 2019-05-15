package ru.otus.main;

import ru.otus.jtunit.engine.JTUnitRequest;
import ru.otus.jtunit.engine.JTUnitRequest.JTRequestBuilder;
import ru.otus.jtunit.engine.JTUnitProcessImpl;
import ru.otus.jtunit.engine.JTUnitProcess;
import ru.otus.main.testclasses.TestJTUnit1;
import ru.otus.main.testclasses.TestJTUnit2;

import static ru.otus.jtunit.engine.JTUnitRequest.selectClass;

public class JTExecute {
    public static void main(String[] args) {
        JTRequestBuilder requestBuilder = JTUnitRequest.makeRequest().setClassList(selectClass(TestJTUnit1.class), selectClass(TestJTUnit2.class));
        JTUnitRequest request = requestBuilder.build();

        JTUnitProcess process = new JTUnitProcessImpl();
        process.start(request);


    }
}
