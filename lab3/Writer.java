public class Writer implements Runnable{
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
        System.out.flush();
    }
}