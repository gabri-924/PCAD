class RWext extends RW{
    private boolean valoreLetto = true;

    @Override
    public int read(){
        int val = super.read();
        synchronized(this){
            valoreLetto = true;
            notifyAll();
        }
        return val;
    }

    @Override
    public void write(){
        synchronized(this) {
            // BLOCCANTE: Non scrivere il nuovo valore finché il vecchio non è letto
            while (!valoreLetto) {
                try { wait(); } catch (InterruptedException e) {}
            }
            
            // Prima di scrivere, resettiamo il flag
            // (Il valore che sto per scrivere non è ancora stato letto)
            valoreLetto = false;
        }
        
        super.write(); // Scrittura effettiva
        
        synchronized(this) {
            System.out.println("[Writer] ha scritto il valore: " + this.data);
            notifyAll(); // Sveglia i lettori che aspettano il nuovo dato
        }
    }
}
class ReaderExt implements Runnable {
    private RWext resource;
    private int id;
    public ReaderExt(RWext resource, int id) { this.resource = resource; this.id = id; }
    
    @Override
    public void run() {
        int v = resource.read();
        System.out.println("Reader " + id + " ha letto il valore: " + v);
    }
}

class WriterExt implements Runnable {
    private RWext resource;
    private int id;
    public WriterExt(RWext resource, int id) { this.resource = resource; this.id = id; }

    @Override
    public void run() {
        resource.write();
    }
}

public class RWext4{
    public static void main(String[] args){
        RWext shared = new RWext();
        int n = 50;
        
        Thread[] r = new Thread[n];
        Thread[] w = new Thread[n];

        // Creazione thread
        for (int i = 0; i < n; i++) {
            r[i] = new Thread(new ReaderExt(shared, i));
            w[i] = new Thread(new WriterExt(shared, i));
        }

        // Avvio thread
        // Nota: se avviassi tutti i writer prima dei lettori, 
        // vedresti i writer bloccarsi uno dopo l'altro.
        for (int i = 0; i < n; i++) {
            w[i].start();
            try{Thread.sleep(10);}catch(InterruptedException e){}
            r[i].start();
            try{Thread.sleep(10);}catch(InterruptedException e){}
        }

        // Join
        for (int i = 0; i < n; i++) {
            try {
                w[i].join();
                r[i].join();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }

        System.out.println("\n--- TEST COMPLETATO ---");
        System.out.println("Valore finale: " + shared.read());
    }
}
