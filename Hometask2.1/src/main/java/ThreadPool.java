public interface ThreadPool {
    void start() throws InterruptedException;

    void execute(Runnable runnable);
}

