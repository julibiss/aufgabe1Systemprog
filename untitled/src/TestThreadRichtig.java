public class TestThreadRichtig extends Thread{
    private final Hilfszahl hilfszahl;

    public TestThreadRichtig(String name, Hilfszahl hilfszahl) {
        super(name);
        this.hilfszahl = hilfszahl;
    }

    public void run() {
        synchronized (hilfszahl) {
            System.out.println("Dies ist der Thread " + getName() + " Zahl: " + hilfszahl.getZahl());
            int hilfszahltemp = hilfszahl.getZahl() + 1;
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hilfszahl.setZahl(hilfszahltemp);
            System.out.println("Thread wurde um 1 erhöht" + getName() + " Zahl: " + hilfszahl.getZahl());
        }

    }

    public static void main(String[] args) {
        Hilfszahl hilfszahl = new Hilfszahl();
        TestThreadRichtig testThread1 =
                new TestThreadRichtig("TestThread 1", hilfszahl);
        TestThreadRichtig testThread2 =
                new TestThreadRichtig("TestThread 2", hilfszahl);
        TestThreadRichtig testThread3 =
                new TestThreadRichtig(  "TestThread 3", hilfszahl);
        testThread1.start();
        testThread2.start();
        testThread3.start();
    }
}

