public class ExecutionManagerImpl implements ExecutionManager {

    public Context execute(Runnable callback, Runnable... tasks) {
        Thread[] threads = new Thread[tasks.length];
        ContextImpl context = new ContextImpl(threads);
        createThreads(threads, tasks, context);
        for (Thread thread : threads) {
            thread.start();
        }
        while (!context.isFinished()) {
            waitForContextToFinish();
        }
        callback.run();
        return context;
    }

    private void waitForContextToFinish() { }

    private void createThreads(Thread[] threads, Runnable[] tasks, ContextImpl context) {
        int i = 0;
        for (final Runnable task : tasks) {
            Runnable synchronizedTask = () -> {
                synchronized (task) {
                    try {
                        task.run();
                    } catch (Exception exc) {
                        System.out.println(exc);
                        context.fail();
                        Thread.currentThread().interrupt();
                    }
                }
            };
            Thread thread = new Thread(synchronizedTask);
            threads[i++] = thread;
        }
    }
}
