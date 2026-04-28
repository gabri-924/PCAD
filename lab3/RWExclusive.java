public class RWExclusive extends RWBasic{
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