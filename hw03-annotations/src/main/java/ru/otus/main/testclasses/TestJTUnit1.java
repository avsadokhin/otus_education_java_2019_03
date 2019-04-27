package ru.otus.main.testclasses;

import ru.otus.jtunit.annotations.*;

public class TestJTUnit1 {

    TestJTUnit1() {
        System.out.println("Call of the constructor");
    }


    @BeforeAll
    static void beforeAll() {
        System.out.println("BeforeAll");
    }

    @BeforeAll
    static void beforeAll2() {
        System.out.println("BeforeAll2");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("AfterAll");
    }


    @BeforeEach
    void beforeEach3() {
        System.out.println("BeforeEach3");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("BeforeEach");
    }

    @BeforeEach
    void beforeEach2() {
        System.out.println("BeforeEach2");
    }

    @Test
    void testThree() {
        System.out.println("testThree");
    }

    @Test
    void testOne() throws Exception {
        System.out.println("testOne");
        throw new Exception("Exception in method testOne!");


    }

    @Test
    public void testTwo() {
        System.out.println("testTwo");
    }


    @AfterEach
    void afterEach() {
        System.out.println("AfterEach");
    }

    @AfterEach
    void afterEach2() {
        System.out.println("AfterEach2");
    }
}