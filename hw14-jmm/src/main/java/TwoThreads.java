import java.util.concurrent.TimeUnit;

public class TwoThreads {
    private String msg;
    private static String turn;


    public TwoThreads() {

    }

    public void go() {
        Thread thread1 = new Thread(this::run);
        Thread thread2 = new Thread(this::run);
        thread1.setName("1");

        thread2.setName("2");
        thread1.start();
        thread2.start();
    }

    public void run() {
        boolean isCounted;

        for (int i = 1; i <= 10; i++) {
            isCounted = false;
            while (true) {

                isCounted = playTurn(i);
                if (isCounted) break;
            }

        }
    }

    public synchronized boolean playTurn(int i) {
        if (!Thread.currentThread().getName().equals(turn)) {
            turn = Thread.currentThread().getName();
            System.out.println("Tread " + turn + ": " + i);
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        TwoThreads twoThreads = new TwoThreads();
        twoThreads.go();


    }
}
