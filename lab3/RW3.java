public class RW3 extends RWBasic{
    private int numReaders = 0;
    private boolean writerAttivi = false;

    private synchronized void startRead() throws InterruptedException{
        while(writerAttivi){
            wait();
        }
        numReaders++;
    }
    private synchronized void endRead(){
        numReaders--;
        if(numReaders == 0)
            notifyAll();
    }

    @Override
    public int read(){
        try{
            startRead();
            int val = super.read();
            Thread.sleep(50);
            endRead();
            return val;
        }
        catch(InterruptedException e){
            return -1;
        }
    }

    private synchronized void startWrite() throws InterruptedException{
        while(writerAttivi || numReaders > 0){
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
            endWrite();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}