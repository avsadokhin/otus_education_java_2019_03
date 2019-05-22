package ru.otus.test;

import ru.otus.gc.BrenchmarkProcess;
import ru.otus.gc.GcStatistics;
import ru.otus.gc.OverHeatProcess;

public class GcBrenchMain {
    public static void main(String[] args) {

        BrenchmarkProcess process = new OverHeatProcess();
        GcStatistics gcStatistics = new GcStatistics(process);
        gcStatistics.start();

    }
}
