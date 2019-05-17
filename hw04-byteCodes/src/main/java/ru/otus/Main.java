package ru.otus;

import ru.otus.proxylog.ProxyLogger;
import ru.otus.testclasses.TestInterface;
import ru.otus.testclasses.TestInterfaceImpl;

public class Main {
    public static void main(String[] args) {
        try {
            Object obj = ProxyLogger.build(TestInterface.class, new TestInterfaceImpl());

            TestInterface testObj = (TestInterface) obj;
            testObj.makeRequest("makeRequest with 2 pararms",1);
            testObj.makeRequest2("makeRequest2 with 1 param");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
