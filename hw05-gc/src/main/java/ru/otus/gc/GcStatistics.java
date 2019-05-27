package ru.otus.gc;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.ListenerNotFoundException;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class GcStatistics {
    private final BrenchmarkProcess process;
    private CopyOnWriteArrayList<GarbageCollectionNotificationInfo> notificationInfoList;
    private boolean isOverLoad = false;
    long workedTime = 0;

    public GcStatistics(BrenchmarkProcess process) {
        this.process = process;
        this.notificationInfoList = new CopyOnWriteArrayList<>();


    }

    private void gatherNotificatioInfo() throws ListenerNotFoundException {
        System.out.println("Gathering GC Notification info...");
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            NotificationEmitter emitter = (NotificationEmitter) gcbean;

            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    notificationInfoList.add(info);
                }
            };

            emitter.addNotificationListener(listener, null, null);

            if (isOverLoad) emitter.removeNotificationListener(listener);
        }
    }

    public void start() {
        Scanner console = new Scanner(System.in);

        System.out.print("Hit enter key when ready...");
        String name = console.nextLine();

        try {
            gatherNotificatioInfo();
        } catch (ListenerNotFoundException e) {
            e.printStackTrace();
        }

        long startTime = System.currentTimeMillis();
        try {

            process.run();
        } catch (OutOfMemoryError err) {
            isOverLoad = true;
            workedTime =( System.currentTimeMillis() - startTime);
            System.out.println("Memory overload. Worked time, sec: " + workedTime/1000);
        }

        printNotificationReport();

    }

    private void printNotificationReport() {
        System.out.println("Report:");

        Map<String, List<GarbageCollectionNotificationInfo>> infoByGcName
                = notificationInfoList.stream().collect(Collectors.groupingBy(GarbageCollectionNotificationInfo::getGcName));
        LongSummaryStatistics summaryStatistics = notificationInfoList.stream().collect(Collectors.summarizingLong(value -> value.getGcInfo().getDuration()));
        System.out.println("All Duration (ms): " + summaryStatistics.getSum() + "; Min duration: " + summaryStatistics.getMin() + "; Max duration: " + summaryStatistics.getMax());

        if (workedTime != 0) System.out.println("All duration / Worked Time: " + (float)(summaryStatistics.getSum()/ (float) workedTime));

        System.out.println();
        // By gcName
        infoByGcName.forEach((gcName, nameInfos) -> {
                    System.out.println(gcName + ":\n");
                    Map<Long, LongSummaryStatistics> minStatMap = nameInfos.stream().collect(Collectors.groupingBy(ni -> ni.getGcInfo().getStartTime() / 1000 / 60, Collectors.summarizingLong(value -> value.getGcInfo().getDuration())));
                    // By minutes statistics
                    minStatMap.forEach((min, durationSummary) ->
                            {
                                System.out.println("min: " + min + "; Summary duration (ms):" + durationSummary.getSum() + "; Avg duration (ms): " + durationSummary.getSum() / durationSummary.getCount() + "; gc run count (per min):" + durationSummary.getCount());
                            }

                    );
                    System.out.println("___________________________\n");
                }
        );

    }

}
