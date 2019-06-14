package ru.otus.gc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OverHeatProcess implements BrenchmarkProcess {
    List<Object> list = new ArrayList<>();
    int size = 10000;//1000000;;

    @Override
    public void run() throws OutOfMemoryError {
        while (true) {
            for (int i = 0; i < size; i++) list.add(new Object());

            list.subList(0, size/2).clear();


            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {

            }


        }
    }

    @Override
    public int getCnt() {
        return list.size();
    }
}
