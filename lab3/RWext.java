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
