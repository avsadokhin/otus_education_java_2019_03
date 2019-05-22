package ru.otus.gc;

import java.util.ArrayList;
import java.util.List;

public class OverHeatProcess implements BrenchmarkProcess {


    @Override
    public void run() throws OutOfMemoryError {
        int cnt = 0;
        int size = 2500000;

        while (true) {
            cnt++;
            List<String> stringList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                stringList.add("A");

            }
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {

            }

            for (int i = 0; i < size/4; i++) {
                stringList.remove(i);


            }
         /*   for (int i = 0; i < size / 3; i++)
                stringList.remove(i);*/


        }
    }

}
