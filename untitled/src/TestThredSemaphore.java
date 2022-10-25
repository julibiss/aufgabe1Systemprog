public class TestThreadSemaphor extends Thread {
    private final Hilfszahl hilfszahl;
    private Semaphor semaphor;

    public TestThreadSemaphor(String name, Hilfszahl hilfszahl, Semaphor semaphor) {
        super(name);
        this.hilfszahl = hilfszahl;
        this.semaphor = semaphor;
    }

    public void run() {
        semaphor.p();
        System.out.println("Dies ist der Thread " + getName() + " Zahl: " + hilfszahl.getZahl());
        int hilfszahltemp = hilfszahl.getZahl() + 1;
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hilfszahl.setZahl(hilfszahltemp);
        System.out.println("Thread wurde um 1 erhöht" + getName() + " Zahl: " + hilfszahl.getZahl());
        semaphor.v();

    }

    public static void main(String[] args) {
        Hilfszahl hilfszahl = new Hilfszahl();
        Semaphor semaphor = new Semaphor(1);
        TestThreadSemaphor testThread1 =
                new TestThreadSemaphor("TestThread 1", hilfszahl, semaphor);
        TestThreadSemaphor testThread2 =
                new TestThreadSemaphor("TestThread 2", hilfszahl, semaphor);
        TestThreadSemaphor testThread3 =
                new TestThreadSemaphor("TestThread 3", hilfszahl, semaphor);
        testThread1.start();
        testThread2.start();
        testThread3.start();
    }
}