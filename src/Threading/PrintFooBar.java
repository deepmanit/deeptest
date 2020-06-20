package Threading;

public class PrintFooBar {
    private int n ;
    private int flag = 0;
    PrintFooBar(int n)
    {
        this.n = n;
    }
    public void PrintFoo() throws InterruptedException {
        for (int i = 1; i <= n;  i++){
            synchronized (this) {
                while (flag == 1)
                    wait();

                flag = 1;
                System.out.print("Foo");
                notifyAll();
            }
        }
    }

    public void PrintBar() throws InterruptedException{

        for (int i = 1; i <= n; i++) {
            synchronized (this) {
                while (flag ==0)
                    wait();

                flag = 0;
                System.out.print("Bar");
                notifyAll();
            }

        }
    }
}
