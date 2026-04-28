public class RWext extends RWBasic{
    private int numReaders = 0;
    private boolean writerAttivi = false;

    //Per garantire l'alternanza
    private boolean dataToRead = false;

    //METODI DI LETTURA DEL PORCO BASTARDO CHE E' MEZZANOTTE E QUARANTA

    private synchronized void startRead(){
        while (writerAttivi) {
            try { 
                wait();
            } catch (InterruptedException e) {}
            numReaders++;
        }
    }

    private synchronized void endRead(){
        numReaders--;

        //un lettore ha letto
        dataToRead = false;

        //sveglia gli scrittori se erano bloccati perchè il dato andava letto
        if (numReaders == 0 || !dataToRead) {
            notifyAll();
        }
    }

    @Override
    public int read() {
        startRead();
        int valore = super.read();
        endRead();
        return valore;
    }

    //METODI DI SCRITTURA DEL PORCO 2.0 CHE E' L'UNA

    private synchronized void startWrite(){
        while (writerAttivi || numReaders > 0 || dataToRead) {
            try { wait(); } catch (InterruptedException e) {}
        }
        writerAttivi = true;
    }

    private synchronized void endWrite(){
        writerAttivi = false;

        //per farlo leggere a quegli stronzi dei reader
        dataToRead = true;

        notifyAll();
    }
    
    @Override
    public void write() {
        startWrite();
        super.write();
        endWrite();
    }
}
    /* 
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
 */

