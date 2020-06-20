package Threading;

import java.util.Arrays;
import java.util.Collections;

public class H2OMachine {
    Object sync;
    String[] molecule;
    int count;
    public H2OMachine() {
        molecule = new String[3];
        count = 0;
        sync = new Object();
    }

    public void HydrogenAtom() {
        synchronized (sync) {

            // if 2 hydrogen atoms already exist
            while (Collections.frequency(Arrays.asList(molecule), "H") == 2) {
                try {
                    sync.wait();
                }
                catch (InterruptedException e)
                {

                }
            }

            molecule[count] = "H";
            count++;

            // if molecule is full, then exit.
            if(count == 3) {
                for (String element: molecule) {
                    System.out.print(element);
                }
                Arrays.fill(molecule,null);
                count = 0;
            }
            sync.notifyAll();
        }
    }

    public void OxygenAtom() throws InterruptedException {

        synchronized(sync) {

            // if 1 oxygen atom already exists
            while (Collections.frequency(Arrays.asList(molecule), "O") == 1) {
                sync.wait();
            }

            molecule[count] = "O";
            count++;

            // if molecule is full, then exit.
            if (count == 3) {
                for (String element : molecule) {
                    System.out.print(element);
                }
                Arrays.fill(molecule, null);
                count = 0;
            }
            sync.notifyAll();
        }
    }
}
