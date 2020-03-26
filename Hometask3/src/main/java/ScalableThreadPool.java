import java.util.ArrayList;
import java.util.Queue;

public class ScalableThreadPool implements ThreadPool {
    private final int minThreadCnt;
    private final int maxThreadCnt;
    private int currentThreadCnt;
    private Queue<Runnable> queue;
    private ArrayList<Thread> threads;

    public ScalableThreadPool(int minThreadCnt, int maxThreadCnt, Queue<Runnable> queue) {
        this.minThreadCnt = minThreadCnt;
        this.maxThreadCnt = maxThreadCnt;
        this.currentThreadCnt = 0;
        this.queue = queue;
        this.threads = new ArrayList<>();
    }

    public void start() {
        for (int i = 0; i < minThreadCnt; ++i) {
            Thread thread = new Thread(this::run);
            threads.add(thread);
            currentThreadCnt++;
            thread.start();
        }
    }

    public void execute(Runnable runnable) {
        queue.add(runnable);
        if (!queue.isEmpty()) {
            int cur = currentThreadCnt;
            for (int i = cur; i < maxThreadCnt; ++i) {
                Thread thread = new Thread(this::run);
                threads.add(thread);
                currentThreadCnt++;
                thread.start();
            }
        }
        else {
            for (int i = currentThreadCnt; i >= minThreadCnt; --i) {
                threads.get(i).stop();
                currentThreadCnt--;
            }
        }
    }

    private void run() {
        try {
            while (true) {
                synchronized (this) {
                    if (!queue.isEmpty()) {
                        Runnable runnable = queue.remove();
                        runnable.run();
                    }
                }
            }
        } catch (Exception exc) {
            System.out.println("error");
        }
    }
}
