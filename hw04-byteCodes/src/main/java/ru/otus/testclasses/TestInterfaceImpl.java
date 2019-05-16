package ru.otus.testclasses;

import ru.otus.proxylog.Log;

public class TestInterfaceImpl implements TestInterface {
    @Log
    public void ownRequest (String param) {
        System.out.println("makeRequest2..." + param);
    }
    @Log
    @Override
    public void makeRequest2(String param) {
        System.out.println("makeRequest2..." + param);

    }
    @Log
    @Override
    public void makeRequest(String param) {
        System.out.println("makeRequest..." + param);

    }
}
