package Threading;

public class PrintNumberSeries {

    private  boolean isZero = true;
    private boolean isOdd = false;
    private boolean isEven= false;
    private int n;
    public PrintNumberSeries(int n) {
        this.n = n;
    }

    public void PrintZero() {
        for (int i = 1; i <= n; ++i) {
            synchronized (this) {
                while (isZero == false) {
                    try {
                        this.wait();
                    } catch (Exception e) {

                    }
                }
              if(i%2 != 0)
              {
                  isOdd = true;
              }
              else
              {
                  isEven = true;
              }
            isZero = false;
            System.out.println(0);
            notifyAll();
            }
        }

    }
    public void PrintOdd() {
        for (int i = 1; i <= n; i = i+2) {
            synchronized (this) {
                while (isZero == true || isEven == true) {
                    try {
                        this.wait();
                    } catch (Exception e) {

                    }
                }
                isOdd = false;
                isZero = true;
                System.out.println(i);
                notifyAll();
            }
        }
    }
    public void PrintEven() {
        for (int i = 2; i <= n; i = i+2) {
            synchronized (this) {
                while (isZero == true || isOdd == true) {
                    try {
                        this.wait();
                    } catch (Exception e) {

                    }
                }
                isZero = true;
                isEven = false;
                System.out.println(i);
                notifyAll();
            }
        }
    }
}
