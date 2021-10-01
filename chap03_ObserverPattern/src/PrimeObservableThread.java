public class PrimeObservableThread extends MainWindow implements Runnable {
    private static final int SLEEP_TIME = 300;

    private int primeNumber;
    private int numCount;
    private boolean first = true;
    private boolean stopRunning = false;

    public PrimeObservableThread() {}

    public void stopRunning() {
        stopRunning = true;
    }

    @SuppressWarnings("BusyWait")
    private void generatePrimeNumber() {
        while (!stopRunning) {
            if (first) {
                first = false;
                primeNumber = 2;
                System.out.println(primeNumber);
                numCount = 1;
            } else {
                numCount += 2;
                if (isPrimeNumber(numCount)) {
                    primeNumber = numCount;
                    notifyWindow(primeNumber+"");
                    System.out.println(primeNumber);
                }
            }
            try {
                Thread.sleep(SLEEP_TIME);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isPrimeNumber(int n) {
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void run() {
        generatePrimeNumber();
    }
}
