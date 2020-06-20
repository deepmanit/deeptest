package Threading;

import java.util.HashSet;
import java.util.Set;

public class ThreadingTest {
    public static void test() throws InterruptedException {
       // test1();
     //   runTestMaxTokenIsTen();
       // runLateThenEarlyCallback();
      //  runTestCountingSemaPhore();
     //   runTestReadWriteLock();
        runTestUnisexBathroom();
    }

    private static void runTestReadWriteLock() throws InterruptedException{
        final ReadWriteLock rwl = new ReadWriteLock();

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    System.out.println("Attempting to acquire write lock in t1: " + System.currentTimeMillis());
                    rwl.acquireWriteLock();
                    System.out.println("write lock acquired t1: " + +System.currentTimeMillis());

                    // Simulates write lock being held indefinitely
                    for (; ; ) {
                        Thread.sleep(500);
                    }

                } catch (InterruptedException ie) {

                }
            }
        });

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    System.out.println("Attempting to acquire write lock in t2: " + System.currentTimeMillis());
                    rwl.acquireWriteLock();
                    System.out.println("write lock acquired t2: " + System.currentTimeMillis());

                } catch (InterruptedException ie) {

                }
            }
        });

        Thread tReader1 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    rwl.acquireReadLock();
                    System.out.println("Read lock acquired: " + System.currentTimeMillis());

                } catch (InterruptedException ie) {

                }
            }
        });

        Thread tReader2 = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("Read lock about to release: " + System.currentTimeMillis());
                rwl.releaseReadLock();
                System.out.println("Read lock released: " + System.currentTimeMillis());
            }
        });

        tReader1.start();
        t1.start();
        Thread.sleep(3000);
        tReader2.start();
        Thread.sleep(1000);
        t2.start();
        tReader1.join();
        tReader2.join();
        t2.join();
    }


    public static void test1() throws InterruptedException {
        BlockingQueue<Integer> q = new BlockingQueue<>(10);
        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    for (int i = 0; i < 50; i++) {
                        q.enqueue(new Integer(i));
                        System.out.println("enqueued " + i);
                    }
                } catch (InterruptedException ie) {

                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 50; i++) {
                        q.enqueue(new Integer(i));
                        System.out.println("enqueued " + i);
                    }
                } catch (InterruptedException ie) {

                }
            }
        });
        Thread t3 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    for (int i = 0; i < 50; i++) {
                        System.out.println("Thread 3 dequeued: " + q.dequeue());
                    }
                } catch (InterruptedException ie) {

                }
            }
        });

        t1.start();
        // t2.start();
        t3.start();
        t1.join();
        t3.join();
        //t2.join();

    }
    public static void runTestMaxTokenIsTen() throws InterruptedException {

        Set<Thread> allThreads = new HashSet<Thread>();
        final TokenBucketFilter tokenBucketFilter = new TokenBucketFilter(5);

        // Sleep for 10 seconds.
        Thread.sleep(10000);

        // Generate 12 threads requesting tokens almost all at once.
        for (int i = 0; i < 12; i++) {

            Thread thread = new Thread(new Runnable() {
                public void run() {
                    try {
                        tokenBucketFilter.getToken();
                    } catch (InterruptedException ie) {
                        System.out.println("We have a problem");
                    }
                }
            });
            thread.setName("Thread_" + (i + 1));
            allThreads.add(thread);
        }

        for (Thread t : allThreads) {
            t.start();
        }

        for (Thread t : allThreads) {
            t.join();
        }
    }
    public static void runLateThenEarlyCallback() throws InterruptedException {
        final DeferredCallbackExecutor deferredCallbackExecutor = new DeferredCallbackExecutor();

        Thread service = new Thread(new Runnable() {
            public void run() {
                try {
                    deferredCallbackExecutor.start();
                } catch (InterruptedException ie) {
                }
            }
        });

        service.start();

        Thread lateThread = new Thread(new Runnable() {
            public void run() {
                DeferredCallbackExecutor.CallBack cb = new DeferredCallbackExecutor.CallBack(8, "Hello this is the callback submitted first");
                deferredCallbackExecutor.registerCallback(cb);
            }
        });
        lateThread.start();

        Thread.sleep(3000);

        Thread earlyThread = new Thread(new Runnable() {
            public void run() {
                DeferredCallbackExecutor.CallBack cb = new DeferredCallbackExecutor.CallBack(1, "Hello this is callback sumbitted second");
                deferredCallbackExecutor.registerCallback(cb);
            }
        });
        earlyThread.start();

        lateThread.join();
        earlyThread.join();
    }

    public static void runTestCountingSemaPhore() throws InterruptedException
    {
        final CountingSemaphore cs = new CountingSemaphore(1);

        Thread t1 = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            for (int i = 0; i < 5; i++) {
                                cs.acquire();
                                System.out.println("Ping " + i);
                            }
                        } catch (InterruptedException ie) {

                        }
                    }
                }
        );

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        cs.release();
                        System.out.println("Pong " + i);
                    } catch (InterruptedException ie) {

                    }
                }
            }
        });

        t2.start();
        t1.start();
        t1.join();
        t2.join();
    }


    public static void runTestUnisexBathroom() throws InterruptedException {

        final UnisexBathroom unisexBathroom = new UnisexBathroom();

        Thread female1 = new Thread(new Runnable() {
            public void run() {
                try {
                    unisexBathroom.femaleUseBathroom("Lisa");
                } catch (InterruptedException ie) {

                }
            }
        });

        Thread male1 = new Thread(new Runnable() {
            public void run() {
                try {
                    unisexBathroom.maleUseBathroom("John");
                } catch (InterruptedException ie) {

                }
            }
        });

        Thread male2 = new Thread(new Runnable() {
            public void run() {
                try {
                    unisexBathroom.maleUseBathroom("Bob");
                } catch (InterruptedException ie) {

                }
            }
        });

        Thread male3 = new Thread(new Runnable() {
            public void run() {
                try {
                    unisexBathroom.maleUseBathroom("Anil");
                } catch (InterruptedException ie) {

                }
            }
        });

        Thread male4 = new Thread(new Runnable() {
            public void run() {
                try {
                    unisexBathroom.maleUseBathroom("Wentao");
                } catch (InterruptedException ie) {

                }
            }
        });

        female1.start();
        male1.start();
        male2.start();
        male3.start();
        male4.start();

        female1.join();
        male1.join();
        male2.join();
        male3.join();
        male4.join();

    }
}


