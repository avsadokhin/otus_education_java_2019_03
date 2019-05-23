package ru.otus.gc;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.ListenerNotFoundException;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;

public class GcStatistics {
    private final BrenchmarkProcess process;
    private List<GarbageCollectionNotificationInfo> notificationInfoList;
    private boolean isOverLoad = false;

    public GcStatistics(BrenchmarkProcess process) {
        this.process = process;
        this.notificationInfoList = new ArrayList<>();


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
            //
            if (isOverLoad) emitter.removeNotificationListener(listener);
        }
    }

    public void start() {
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
            System.out.println("Memory overload. Worked time, sec: " + (System.currentTimeMillis() - startTime) / 1000);
        }

        printNotificationReport();

    }

    private void printNotificationReport() {
        List<GarbageCollectionNotificationInfo> printList = new ArrayList();

        printList.addAll(notificationInfoList);

        //printList.forEach(info -> info.getGcInfo().System.out.println("GcName: " + info.getGcName() + "GcInfo: " + info.getGcInfo() + "GcAction: " + info.getGcAction() + "GcCause: " + info.getGcCause()));

        System.out.println("Отчет");

        Map<String, List<GarbageCollectionNotificationInfo>> infoByGcName
                = printList.stream().collect(Collectors.groupingBy(GarbageCollectionNotificationInfo::getGcName));

        infoByGcName.forEach((s, iByGcName) ->
                {
                    System.out.println(s + ":\n");

                  iByGcName.stream().collect(Collectors.groupingBy(notificationInfo -> notificationInfo.getGcInfo().getDuration(), Collectors.summarizingLong(value -> value.getGcInfo().getDuration())));

                }

        );

        System.out.println("___________________________");

//        printList.stream().collect()

    }

}
