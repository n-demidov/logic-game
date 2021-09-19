package edu.test.dumps1;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class MainTestProgram {

    private static final int THREAD_COUNT = 10;
    private static final int TASKS_COUNT = 10;
    private static final long WORK_MINUTES = 60L;

    private static class MyClass{
        private byte[] bytes = new byte[100_000];
    }

    private static final ConcurrentMap<Integer, MyClass> concurrentMap = new ConcurrentHashMap<>();

    private static class MyTask implements Callable<Boolean> {
        @Override
        public Boolean call() throws Exception {
            long startTime = System.currentTimeMillis();
            // Каждый поток работает только WORK_MINUTES минут с момента старта.
            while (System.currentTimeMillis() < startTime + 1000L * 60L* WORK_MINUTES) {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
                concurrentMap.put(
                        ThreadLocalRandom.current().nextInt(
                                Integer.MIN_VALUE, Integer.MAX_VALUE),
                        new MyClass());

            }
            return true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        Future<Boolean>[] futures = new Future[TASKS_COUNT];
        System.out.println("Scheduling threads...");
        for (int n = 0; n < TASKS_COUNT; n++) {
            futures[n] = executorService.submit(new MyTask());
        }
        System.out.println("Awaiting termination...");
        executorService.awaitTermination(WORK_MINUTES * 2L, TimeUnit.MINUTES);
        System.out.println("Finished.");
    }

}
