public class TestThread4 extends Thread {

        private final Hilfszahl1 hilfszahl1;

        public TestThread4(String name, Hilfszahl1 hilfszahl1) {
            super(name);
            this.hilfszahl1 = hilfszahl1;
        }

        public void run() {
            hilfszahl1.addOne();
        }

        public static void main(String[] args) {
            Hilfszahl1 hilfszahl1 = new Hilfszahl1();
            TestThread4 testThread1 =
                    new TestThread4("TestThread 1", hilfszahl1);
            TestThread4 testThread2 =
                    new TestThread4("TestThread 2", hilfszahl1);
            TestThread4 testThread3 =
                    new TestThread4("TestThread 3", hilfszahl1);
            testThread1.start();
            testThread2.start();
            testThread3.start();
        }
    }


