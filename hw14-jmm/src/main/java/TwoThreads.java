import java.util.concurrent.TimeUnit;

public class TwoThreads {
    private static String turn;
    private final int MIN_COUNT = 1;
    private final int MAX_COUNT = 10;


    public static void main(String[] args) {
        TwoThreads twoThreads = new TwoThreads();
        twoThreads.go();
    }

    private void go() {
        Thread thread1 = new Thread(this::run);
        Thread thread2 = new Thread(this::run);
        thread1.setName("1");
        thread2.setName("2");

        thread1.start();
        thread2.start();
    }

    private void run() {
        while (true) {
            for (int i = MIN_COUNT; i <= MAX_COUNT; i++) {
                makeCount(i);
            }
            for (int i = MAX_COUNT - 1; i > MIN_COUNT; i--) {
                makeCount(i);
            }
        }
    }

    private void makeCount(int cnt) {
        while (!playTurn(cnt)) ;

    }

    private synchronized boolean playTurn(int i) {
        if (!Thread.currentThread().getName().equals(turn)) {
            turn = Thread.currentThread().getName();
            System.out.println("Tread " + turn + ": " + i);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }


}

