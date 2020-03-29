public class ContextImpl implements Context{
    private final Thread[] threads;
    private int failed;

    public ContextImpl(Thread[] threads) {
        this.threads = threads;
        this.failed = 0;
    }

    public void fail() {
        failed++;
    }

    public int getCompletedTaskCount() {
        return threads.length - failed;
    }

    public int getFailedTaskCount() {
        return failed;
    }

    public int getInterruptedTaskCount() {
        int cnt = 0;
        for (Thread thread: threads) {
            if (thread.isInterrupted()) {
                cnt++;
            }
        }
        return cnt;
    }

    public void interrupt() {
        for (Thread thread: threads) {
            thread.interrupt();
        }
    }

    public boolean isFinished() {
        for (Thread thread: threads) {
            if (thread.isAlive()) {
                return false;
            }
        }
        return true;
    }
}
