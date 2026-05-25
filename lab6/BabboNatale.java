public class BabboNatale implements Runnable{
    private final PoloNord polo;

    public BabboNatale(PoloNord polo){
        this.polo = polo;
    }

    @Override
    public void run(){
        try{
            while(true){
                polo.BabboDorme();
                polo.BabboLavora();
                System.out.println("Babbo torna a dormire");
            }
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}