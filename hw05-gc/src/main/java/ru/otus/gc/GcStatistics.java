package ru.otus.gc;

public class GcStatistics {
    private final BrenchmarkProcess process;

    public GcStatistics(BrenchmarkProcess process) {
        this.process = process;

    }

    public void start(){
        process.run();

    }

}
