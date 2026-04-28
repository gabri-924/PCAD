public class MainEs3 {
    public static void main(String[] args){
        RW3 rw = new RW3();
        int numThread = 50;
        Thread[] r = new Thread[numThread];
        Thread[] w = new Thread[numThread];

        // Avvio dei thread
        for (int i = 0; i < numThread; i++) {
            r[i] = new Thread(new Reader(rw, i));
            w[i] = new Thread(new Writer(rw, i));
            w[i].start();
            r[i].start();
        }

        // Attesa fine (Join)
        for (int i = 0; i < numThread; i++) {
            try {
                w[i].join();
                r[i].join();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }

        System.out.println("Valore finale atteso: " + numThread);
        System.out.println("Valore finale reale: " + rw.read()); 
    }
}
