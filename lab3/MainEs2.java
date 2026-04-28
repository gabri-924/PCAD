public class MainEs2 {
    public static void main(String[] args) {
        RWExclusive rw = new RWExclusive();
        int numThread = 50;
        Thread[] readers = new Thread[numThread];
        Thread[] writers = new Thread[numThread];

        for (int i = 0; i < numThread; i++) {
            writers[i] = new Thread(new Writer(rw, i));
            readers[i] = new Thread(new Reader(rw, i));
            writers[i].start();
            readers[i].start();
        }

        for (int i = 0; i < numThread; i++) {
            try {
                writers[i].join();
                readers[i].join();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }

        System.out.println("Valore finale atteso: " + numThread);
        System.out.println("Valore finale reale: " + rw.read());
    }
}
