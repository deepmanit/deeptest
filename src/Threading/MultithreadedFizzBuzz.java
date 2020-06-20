package Threading;
/*For an input integer n, the program should output a
string containing the words fizz, buzz and fizzbuzz representing certain numbers. For example,
for n = 15, the output should be: 1, 2, fizz, 4, buzz, fizz, 7, 8, fizz, buzz, 11, fizz, 13, 14, fizzbuzz.*/


public class MultithreadedFizzBuzz {
    private int n;
    private int num = 1;

    public MultithreadedFizzBuzz(int n) {
    }
    public synchronized void fizzbuzz() throws InterruptedException {
        while (num <= n) {
            if (num % 15 == 0) {
                System.out.println("FizzBuzz");
                num++;
                notifyAll();
            } else {
                wait();
            }
        }
    }
    public synchronized void fizz() throws InterruptedException {
        while (num <= n) {
            if (num % 3 == 0 && num % 5 != 0) {
                System.out.println("fizz");
                num++;
                notifyAll();
            }
            else {
                wait();
            }
        }
    }

    public synchronized void buzz() throws InterruptedException {
        while (num <= n) {
            if (num % 3 != 0 && num % 5 == 0) {
                System.out.println("Buzz");
                num++;
                notifyAll();
            } else {
                wait();
            }
        }
    }
    public synchronized void number() throws InterruptedException {
        while (num <= n) {
            if (num % 3 != 0 && num % 5 != 0) {
                System.out.println(num);
                num++;
                notifyAll();
            } else {
                wait();
            }
        }
    }
}
