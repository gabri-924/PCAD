public class Reader implements Runnable{
    private RWBasic rw;
    private int id;

    public Reader(RWBasic rw, int id){
        this.rw = rw;
        this.id = id;
    }

    @Override
    public void run(){
        System.out.println("Reader " + id + " Valore di data: " + rw.read());
    }
}
