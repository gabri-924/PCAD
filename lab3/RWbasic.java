class RWBasic{
    protected int data = 0;

    public int read(){
        return data;
    }
    public void write(){
        try {
            int tmp = data;
            // Lo sleep rende l'errore sistematico forzando i thread a sovrapporsi
            Thread.sleep(10); 
            tmp = tmp + 1;
            data = tmp;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Reader implements Runnable{
    private RWBasic rw;
    private int id;

    public Reader(RWBasic rw, int id){
        this.rw = rw;
        this.id = id;
    }

    @Override
    public void run(){
        int val = rw.read();
        System.out.println("Reader " + id + "Valore di data: " + val);
        
    }
}

class Writer implements Runnable{
    private RWBasic rw;
    private int id;

    public Writer(RWBasic rw, int id){
        this.rw = rw;
        this.id = id;
    }

    @Override
    public void run(){
        rw.write();
        System.out.println("Il valore di data è stato incrementato dal writer " + id);
    }
}

public class RWbasic{
    public static void main(String[] args){
        RWBasic rw = new RWBasic();
        int Nthread = 50;
        Thread[] readers = new Thread[Nthread];
        Thread[] writers = new Thread[Nthread];

        for(int i=0; i<Nthread; i++){
            readers[i] = new Thread(new Reader(rw, i));
            writers[i] = new Thread(new Writer(rw, i));
            writers[i].start();
            readers[i].start();
        }

        for (int i = 0; i < Nthread; i++) {
            try {
                writers[i].join();
                readers[i].join();
            } catch (InterruptedException e) {
                System.err.println("Errore durante il join: " + e.getMessage());
            }
        }

        System.out.println("Valore finale atteso: " + Nthread);
        System.out.println("Valore finale reale: " + rw.read());
    }


}