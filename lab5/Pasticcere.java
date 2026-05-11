import java.util.concurrent.Semaphore;

class Pasticcere extends Thread{
    Semaphore cioccolatiniDisponibili;
    Semaphore svegliaPasticcere;
    private int P;

    public Pasticcere(Semaphore cioccolatiniDisponibili, Semaphore svegliaPasticcere, int P){
        this.cioccolatiniDisponibili = cioccolatiniDisponibili;
        this.svegliaPasticcere = svegliaPasticcere;
        this.P = P;
    }

    @Override
    public void run(){
        try{
            while(true){
                svegliaPasticcere.acquire();
                System.out.println("[PASSTICCERE] Scatola vuota");
                Thread.sleep(500);
                Mangiatore.setConteggio(P);
                System.out.println("[PASTICCERE] Aggiunti " + P + " cioccolatini");
                cioccolatiniDisponibili.release(P);
            }
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
