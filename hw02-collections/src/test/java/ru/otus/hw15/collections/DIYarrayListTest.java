package ru.otus.hw15.collections;


import org.junit.jupiter.api.Assertions;
import ru.otus.collections.DIYarrayList;

import javax.xml.crypto.dom.DOMCryptoContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Stream;

import static java.util.Comparator.*;
import static org.junit.jupiter.api.Assertions.*;

class DIYarrayListTest {
    int size = 30;
    DIYarrayList<String> srcStringDIYarrayList;
    DIYarrayList<String> dstStringDIYArrayList;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        Stream<String> stream = Stream.generate(() ->
                Integer.toString((int) (Math.random() * 10 + 1))).limit(size);

        srcStringDIYarrayList = new DIYarrayList<>();
        dstStringDIYArrayList = new DIYarrayList<>();
        DIYarrayList.addAll(srcStringDIYarrayList, stream.toArray(size -> new String[size]));
        DIYarrayList.addAll(dstStringDIYArrayList, new String[size]);


    }

    @org.junit.jupiter.api.Test
    void addAll() {
        Assertions.assertEquals(srcStringDIYarrayList.size(), size);
    }

    @org.junit.jupiter.api.Test
    void copy() {
        System.out.println(srcStringDIYarrayList);
        System.out.println(dstStringDIYArrayList);

        Collections.copy(dstStringDIYArrayList, srcStringDIYarrayList);
        System.out.println(dstStringDIYArrayList);

        Assertions.assertLinesMatch(srcStringDIYarrayList, dstStringDIYArrayList);
    }

    @org.junit.jupiter.api.Test
    void sort() {
        srcStringDIYarrayList.sort(Comparator.reverseOrder());
        System.out.println(srcStringDIYarrayList);
    }
}