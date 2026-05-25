public class Elfo implements Runnable{
    private final PoloNord polo;

    public Elfo(PoloNord polo){
        this.polo = polo;
    }

    @Override
    public void run(){
        try{
            while(true){
                Thread.sleep(500);
                polo.ElfoChiedeAiuto();
            }
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}