import java.util.concurrent.Semaphore;

class ClientDeadLock extends Thread{
    private int id;
    private Semaphore spogliatoi;
    private Semaphore armadietti;

    public ClientDeadLock(int id, Semaphore spogliatoi, Semaphore armadietti){
        this.id = id;
        this.spogliatoi = spogliatoi;
        this.armadietti = armadietti;
    }

    @Override
    public void run(){
        try{
            System.out.println("Client " + id + " attende lo spogliatoio...");
            spogliatoi.acquire();
            System.out.println("Client " + id + " ha preso lo spogliatoio. Attende l'armadietto");
            armadietti.acquire();
            System.out.println("Client " + id + " si sta cambiando");
            Thread.sleep(500);//simulo il tempo per cambiarsi
            spogliatoi.release();
            System.out.println("Client " + id + " ha lberato lo spogliatoio");
            Thread.sleep(1000);//simulo la nuotata
            spogliatoi.acquire();
            System.out.println("Client " + id + " ha finito di nuotare e ora si cambia"); 
            Thread.sleep(500);//simulo il tempo per cambiarsi
            spogliatoi.release();
            armadietti.release();
            System.out.println("Client " + id + " ha finito e se ne va");
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}


