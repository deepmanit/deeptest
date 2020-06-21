package Threading;

public class ThreadSafeSingleton {
    private static volatile ThreadSafeSingleton instance;
    public static ThreadSafeSingleton getInstance() {
        if (instance == null) {
            synchronized (ThreadSafeSingleton.class) {
                if (instance == null) {
                    instance = new ThreadSafeSingleton();
                }
            }
        }
        return instance;
    }
    public void dosomework() {
        System.out.println("dosomework !");
    }

    private ThreadSafeSingleton()
    {

    }
}
