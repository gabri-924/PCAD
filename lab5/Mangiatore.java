import java.util.concurrent.Semaphore;

class Mangiatore extends Thread{
    private int id;
    private Semaphore cioccolatiniDisponibili;
    private Semaphore svegliaPasticcere;
    private Semaphore mutex;
    private static int conteggioCondiviso;

    public Mangiatore(int id, Semaphore cioccolatiniDisponibili, Semaphore svegliaPasticcere, Semaphore mutex){
        this.id = id;
        this.cioccolatiniDisponibili = cioccolatiniDisponibili;
        this.svegliaPasticcere = svegliaPasticcere;
        this.mutex = mutex;
    }

    public static void setConteggio(int valore){
        conteggioCondiviso = valore;
    }

    @Override
    public void run(){
        try{
            while(true){
                cioccolatiniDisponibili.acquire();
                mutex.acquire();
                conteggioCondiviso--;
                System.out.println("Mangiatore " + id + " ha mangiato e ne rimangono " + conteggioCondiviso);

                if(conteggioCondiviso == 0){
                    System.out.println("Era l'ultimo! sveglia il pasticcere");
                    svegliaPasticcere.release();
                }
                mutex.release();
            }
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
