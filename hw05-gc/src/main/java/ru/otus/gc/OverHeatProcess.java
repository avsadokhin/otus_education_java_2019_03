package ru.otus.gc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OverHeatProcess implements BrenchmarkProcess {
    List<Object> list = new ArrayList<>();
    int size = 100000;//9000;

    @Override
    public void run() throws OutOfMemoryError {
        while (true) {
            for (int i = 0; i < size; i++) list.add(new Object());

            list.subList(0, size/2).clear();


            try {
                Thread.sleep(350);
            } catch (InterruptedException e) {

            }


        }
    }

}
