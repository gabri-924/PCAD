class RW extends RWBasic{
    private int numReader = 0;
    private boolean writerAttivi = false;

    private synchronized void startRead() throws InterruptedException{
        while(writerAttivi){
            wait();
        }
        numReader++;
    }
    private synchronized void endRead(){
        numReader--;
        if(numReader == 0)
            notifyAll();
    }

    @Override
    public int read(){
        try{
            startRead();
            int val = super.read();
            Thread.sleep(50);
            return val;
        }
        catch(InterruptedException e){
            return -1;
        }
        finally{
            endRead();
        }
    }

    private synchronized void startWrite() throws InterruptedException{
        while(writerAttivi || numReader > 0){
            wait();
        }
        writerAttivi = true;
    }

    private synchronized void endWrite(){
        writerAttivi = false;
        notifyAll();
    }

    @Override
    public void write(){
        try{
            startWrite();
            super.write();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        finally{
            endWrite();
        }
    }
}

class ReaderRW implements Runnable {
    private RW rw;
    private int id;

    public ReaderRW(RW rw, int id) { 
        this.rw = rw; 
        this.id = id; 
    }
    
    @Override
    public void run() {
        int val = rw.read();
        System.out.println("Reader " + id + " ha letto: " + val);
    }
}

class WriterRW implements Runnable {
    private RW rw;
    private int id;
    public WriterRW(RW rw, int id) { this.rw = rw; this.id = id; }

    @Override
    public void run() {
        rw.write();
        System.out.println("Writer " + id + " ha incrementato");
    }
}

public class RW3{
    public static void main(String[] args){
        RW rw = new RW();
        int NThread = 50;
        Thread[] r = new Thread[NThread];
        Thread[] w = new Thread[NThread];

        // Avvio dei thread
        for (int i = 0; i < NThread; i++) {
            r[i] = new Thread(new ReaderRW(rw, i));
            w[i] = new Thread(new WriterRW(rw, i));
            w[i].start();
            r[i].start();
        }

        // Attesa fine (Join)
        for (int i = 0; i < NThread; i++) {
            try {
                w[i].join();
                r[i].join();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }

        System.out.println("Valore finale atteso: " + NThread);
        System.out.println("Valore finale reale: " + rw.read()); 
    }
}