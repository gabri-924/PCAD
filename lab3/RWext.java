public class RWext extends RWBasic{
    private int numReaders = 0;
    private boolean writerAttivi = false;

    //Per garantire l'alternanza
    private boolean dataToRead = false;

    private synchronized void startRead(){
        while (writerAttivi) {
            try { 
                wait();
            } catch (InterruptedException e) {}
            
        }
        numReaders++;
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


    private synchronized void startWrite(){
        while (writerAttivi || numReaders > 0 || dataToRead) {
            try { wait(); } catch (InterruptedException e) {}
        }
        writerAttivi = true;
    }

    private synchronized void endWrite(){
        writerAttivi = false;
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
