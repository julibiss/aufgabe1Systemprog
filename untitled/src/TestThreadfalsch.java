public class TestThreadfalsch extends Thread{

        private Hilfszahl hilfszahl;

        public TestThreadfalsch (String name, Hilfszahl hilfszahl){
            super(name);
            this.hilfszahl = hilfszahl;
        }
        public void run(){
            System.out.println("Dies ist der Thread " + getName() + " Zahl: " + hilfszahl.getZahl());
            int hilfszahltemp = hilfszahl.getZahl() +1;
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hilfszahl.setZahl(hilfszahltemp);
            System.out.println("Thread wurde um 1 erhöht" + getName() + " Zahl: " + hilfszahl.getZahl());

        }
        public static void main(String[] args) {
            Hilfszahl hilfszahl = new Hilfszahl();
            TestThreadfalsch testThread1 =
                    new TestThreadfalsch("TestThread 1", hilfszahl);
            TestThreadfalsch testThread2 =
                    new TestThreadfalsch("TestThread 2", hilfszahl);
            TestThreadfalsch testThread3 =
                    new TestThreadfalsch("TestThread 3", hilfszahl);
            testThread1.start();
            testThread2.start();
            testThread3.start();
        }
    }

