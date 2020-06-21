package Threading;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
/*Imagine at the end of a political conference, republicans and
democrats are trying to leave the venue and ordering Uber rides at the same time.
 However, to make sure no fight breaks out in an Uber ride, the software developers at
 Uber come up with an algorithm whereby either an Uber ride can have all democrats or republicans or
 two Democrats and two Republicans. All other combinations can result in a fist-fight
Your task as the Uber developer is to model the ride requestors as threads.
Once an acceptable combination of riders is possible, threads are allowed to proceed to ride.
Each thread invokes the method seated() when selected by the system for the next ride.
When all the threads are seated, any one of the four threads can invoke the method drive()
to inform the driver to start the ride.*/


public class UberSeatingProblem {
    private int republicans = 0;
    private int democrats = 0;

    CyclicBarrier barrier = new CyclicBarrier(4);
    ReentrantLock lock = new ReentrantLock();
    Semaphore demsWaiting = new Semaphore(0);
    Semaphore repubsWaiting = new Semaphore(0);
    void seatDemocrat() throws InterruptedException, BrokenBarrierException {
        boolean rideLeader = false;
        lock.lock();
        democrats++;

        if (democrats == 4) {
            // Seat all the democrats in the Uber ride.
            demsWaiting.release(3);
            democrats -= 4;
            rideLeader = true;
        } else if (democrats == 2 && republicans >= 2) {
            // Seat 2 democrats & 2 republicans
            demsWaiting.release(1);
            repubsWaiting.release(2);
            rideLeader = true;
            democrats -= 2;
            republicans -= 2;
        } else {
            lock.unlock();
            demsWaiting.acquire();
        }

        seated();
        barrier.await();

        if (rideLeader == true) {
            drive();
            lock.unlock();
        }
    }

    void seatRepublican() throws InterruptedException, BrokenBarrierException {
        boolean rideLeader = false;
        lock.lock();

        republicans++;

        if (republicans == 4) {
            // Seat all the republicans in the Uber ride.
            repubsWaiting.release(3);
            rideLeader = true;
            republicans -= 4;
        } else if (republicans == 2 && democrats >= 2) {
            // Seat 2 democrats & 2 republicans
            repubsWaiting.release(1);
            demsWaiting.release(2);
            rideLeader = true;
            republicans -= 2;
            democrats -= 2;
        } else {
            lock.unlock();
            repubsWaiting.acquire();
        }

        seated();
        barrier.await();

        if (rideLeader) {
            drive();
            lock.unlock();
        }
    }

    void seated() {
        System.out.println(Thread.currentThread().getName() + "  seated");
        System.out.flush();
    }

    void drive() {
        System.out.println("Uber Ride on Its wayyyy... with ride leader " + Thread.currentThread().getName());
        System.out.flush();
    }
}
