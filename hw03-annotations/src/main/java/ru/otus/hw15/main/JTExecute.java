package ru.otus.hw15.main;

import ru.otus.hw15.jtunit.engine.JTUnitProcess;
import ru.otus.hw15.jtunit.engine.JTUnitProcessImpl;
import ru.otus.hw15.jtunit.engine.JTUnitRequest;
import ru.otus.hw15.main.testclasses.TestJTUnit1;
import ru.otus.hw15.main.testclasses.TestJTUnit2;

public class JTExecute {
    public static void main(String[] args) {
        JTUnitRequest.JTRequestBuilder requestBuilder = JTUnitRequest.makeRequest().setClassList(JTUnitRequest.selectClass(TestJTUnit1.class), JTUnitRequest.selectClass(TestJTUnit2.class));
        JTUnitRequest request = requestBuilder.build();

        JTUnitProcess process = new JTUnitProcessImpl();
        process.start(request);


    }
}
