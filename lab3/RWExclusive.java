class RWexclusive extends RWBasic{
    @Override
    public synchronized int read() {
        return super.read();
    }

    // Sincronizziamo la scrittura
    @Override
    public synchronized void write() {
        super.write();
    }
}
class ReaderEx implements Runnable {
    private RWexclusive rw;
    private int id;
    public ReaderEx(RWexclusive rw, int id) { this.rw = rw; this.id = id; }
    
    @Override
    public void run() {
        int val = rw.read();
        System.out.println("Reader " + id + " ha letto il valore di data: " + val);
    }
}

class WriterEx implements Runnable {
    private RWexclusive rw;
    private int id;
    public WriterEx(RWexclusive rw, int id) { this.rw = rw; this.id = id; }

    @Override
    public void run() {
        rw.write();
        System.out.println("Il valore di data è stato incrementato dal writer " + id);
    }
}

// 4. Il programma principale
public class RWExclusive {
    public static void main(String[] args) {
        RWexclusive rw = new RWexclusive();
        int NThread = 50;
        Thread[] readers = new Thread[NThread];
        Thread[] writers = new Thread[NThread];

        for (int i = 0; i < NThread; i++) {
            writers[i] = new Thread(new WriterEx(rw, i));
            readers[i] = new Thread(new ReaderEx(rw, i));
            writers[i].start();
            readers[i].start();
        }

        for (int i = 0; i < NThread; i++) {
            try {
                writers[i].join();
                readers[i].join();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }

        System.out.println("Valore finale atteso: " + NThread);
        System.out.println("Valore finale reale: " + rw.read());
    }
}