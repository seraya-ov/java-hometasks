import java.util.concurrent.Callable;

public class Task<T> {
    private final Callable<? extends T> callable;
    private volatile boolean finished;
    private volatile T result;
    private RuntimeException e;

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
        this.finished = false;
    }

    public T get() {
        try {
            if (finished) return result;
            if (e != null) throw e;
            synchronized (this) {
                if (finished) return result;
                if (e != null) throw e;
                result = callable.call();
                finished = true;
                return result;
            }
        } catch (Exception e) {
            throw this.e = new RuntimeException(e);
        }
    }
}
