package Threading;

public class OrderedPrintingThread extends Thread {

    private OrderedPrinting obj;
    private String method;

    public OrderedPrintingThread(OrderedPrinting obj, String method) {
        this.method = method;
        this.obj = obj;
    }
        public void run( ) {
            //for printing "First"
            if ("first".equals(method)) {
                try {
                    obj.printFirst();
                } catch (InterruptedException e) {

                }
            }
            //for printing "Second"
            else if ("second".equals(method)) {
                try {
                    obj.printSecond();
                } catch (InterruptedException e) {

                }
            }
            //for printing "Third"
            else if ("third".equals(method)) {
                try {
                    obj.printThird();
                } catch (InterruptedException e) {

                }
            }
        }

}
