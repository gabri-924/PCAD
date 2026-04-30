public class RWExclusive extends RWBasic{
    @Override
    public synchronized int read() {
        return super.read();
    }

    @Override
    public synchronized void write() {
        super.write();
    }
}