package ru.otus.hw15.testclasses;

import ru.otus.hw15.proxylog.Log;

import java.io.IOException;
import java.nio.CharBuffer;

public class TestInterfaceImpl implements TestInterface, Readable {
    @Override
    @Log
    public int read(CharBuffer cb) throws IOException {
        System.out.println("Readable..." + cb);
        return 0;
    }

    @Log
    public void ownRequest (String param) {
        System.out.println("makeRequest2..." + param);
    }
    @Log
    @Override
    public void makeRequest2(String param) {
        System.out.println("makeRequest2..." + param);
        try {
            Thread.sleep(1001);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    @Log
    @Override
    public void makeRequest(String param1, Integer param2) {
        System.out.println("makeRequest..." + param1);

    }
}
