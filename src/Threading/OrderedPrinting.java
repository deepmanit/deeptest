package Threading;

public class OrderedPrinting {
    int count;

    public OrderedPrinting() {
        count = 1;
    }

    public void printFirst()  throws InterruptedException {
        synchronized (this) {
            System.out.println("printFirst");
            ++count;
            this.notifyAll();
        }

    }

    public void printSecond()  throws InterruptedException {
        synchronized (this) {
            while (count != 2)
                wait();
            System.out.println("printSecond");
            ++count;
            notifyAll();
        }


    }

    public void printThird()  throws InterruptedException {
        synchronized (this) {
            while (count != 3)
                wait();
            System.out.println("printThird");

        }
    }
}
