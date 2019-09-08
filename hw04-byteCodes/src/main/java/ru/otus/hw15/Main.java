package ru.otus.hw15;

import ru.otus.hw15.proxylog.ProxyLogger;
import ru.otus.hw15.testclasses.TestInterface;
import ru.otus.hw15.testclasses.TestInterfaceImpl;

import java.nio.CharBuffer;

public class Main {
    public static void main(String[] args) {
        try {
            TestInterface testObj = ProxyLogger.build(TestInterface.class, TestInterfaceImpl.class);

            testObj.makeRequest("makeRequest with 2 params",1);
            testObj.makeRequest2("makeRequest2 with 1 param");

            Readable testObj2 = ProxyLogger.build(Readable.class, TestInterfaceImpl.class);
            String charBuffer = "A";
            testObj2.read(CharBuffer.wrap(charBuffer));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
