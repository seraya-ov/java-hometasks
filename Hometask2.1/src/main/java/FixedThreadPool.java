import java.util.Queue;

public class FixedThreadPool implements ThreadPool {
    private final Integer threadCnt;
    private Queue<Runnable> queue;

    public FixedThreadPool(Integer threadCnt, Queue<Runnable> queue) {
        this.threadCnt = threadCnt;
        this.queue = queue;
    }

    public void start() {
        for (int i = 0; i < threadCnt; ++i) {
            Thread thread = new Thread(this::run);
            thread.start();
        }
    }

    public void execute(Runnable runnable) {
        queue.add(runnable);
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
