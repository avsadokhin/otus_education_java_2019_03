package ru.otus.gc;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.util.ArrayList;
import java.util.List;

public class GcStatistics {
    private final BrenchmarkProcess process;
    private List<GarbageCollectionNotificationInfo> notificationInfoList;

    public GcStatistics(BrenchmarkProcess process) {
        this.process = process;
        this.notificationInfoList = new ArrayList<>();

    }

    private void gatherNotificatioInfo(){
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
        }
    }

    public void start(){
        gatherNotificatioInfo();
        try {
            process.run();
        }
        catch (OutOfMemoryError err)
        {

        }
        System.out.println("info:" +notificationInfoList.size());
        notificationInfoList.forEach(info -> System.out.println(info));


    }

}
