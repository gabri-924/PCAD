public class Renna implements Runnable{
    private final PoloNord polo;

    public Renna(PoloNord polo){
        this.polo = polo;
    }

    @Override
    public void run(){
        try{
            while(true){
                Thread.sleep(500);
                polo.RennaArriva();
            }
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}