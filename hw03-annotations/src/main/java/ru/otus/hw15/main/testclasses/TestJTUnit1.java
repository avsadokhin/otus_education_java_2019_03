package ru.otus.hw15.main.testclasses;

import ru.otus.hw15.jtunit.annotations.*;
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
    static void beforeAll2() throws Exception {
        System.out.println("BeforeAll2");
        //     throw new Exception("Exception in method beforeAll2!");

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
    void beforeEach() throws Exception {
        System.out.println("BeforeEach");
              throw new Exception("Exception in method beforeEach!");
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