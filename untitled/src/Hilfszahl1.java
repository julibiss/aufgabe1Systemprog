public class Hilfszahl1 {
    private int zahl;

    public Hilfszahl1() {
        this.zahl = 0;
    }

    public int getZahl() {
        return zahl;
    }

    public void setZahl(int zahl) {
        this.zahl = zahl;
    }

    public synchronized void addOne(){
        System.out.println("Dies ist der Thread " + Thread.currentThread().getName() + " Zahl: " + zahl);
        this.zahl = this.zahl + 1;
        System.out.println("Thread wurde um 1 erhöht" + Thread.currentThread().getName() + " Zahl: " + zahl);

    }
}
