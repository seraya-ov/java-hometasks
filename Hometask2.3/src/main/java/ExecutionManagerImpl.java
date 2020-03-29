public class ExecutionManagerImpl implements ExecutionManager {

    public Context execute(Runnable callback, Runnable... tasks) {
        Thread[] threads = new Thread[tasks.length];
        createThreads(threads, tasks);
        ContextImpl context = new ContextImpl(threads);
        for (Thread thread: threads) {
            runTask(context, thread);
        }
        while (!context.isFinished()) {
            waitForContextToFinish();
        }
        callback.run();
        return context;
    }

    private void waitForContextToFinish() {}

    private void runTask(ContextImpl context, Thread thread) {
        try {
            thread.run();
        }
        catch (Exception exc) {
            System.out.println(exc);
            context.fail();
            thread.interrupt();
        }
    }

    private void createThreads(Thread[] threads, Runnable[] tasks) {
        int i = 0;
        for (Runnable task: tasks) {
            Thread thread = new Thread(task);
            threads[i++] = thread;
        }
    }
}
