package ru.otus;

import ru.otus.proxylog.ProxyLogger;
import ru.otus.testclasses.TestInterface;
import ru.otus.testclasses.TestInterfaceImpl;
import java.nio.CharBuffer;

public class Main {
    public static void main(String[] args) {
        try {
            Object obj = ProxyLogger.build(new TestInterfaceImpl());

            TestInterface testObj = (TestInterface) obj;

            testObj.makeRequest("makeRequest with 2 params",1);
            testObj.makeRequest2("makeRequest2 with 1 param");

            Readable r = (Readable) obj;
            r.read(CharBuffer.wrap("A"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
