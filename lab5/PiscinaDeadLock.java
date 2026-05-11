import java.util.concurrent.Semaphore;

public class PiscinaDeadLock{
    public static void main(String[] args){
        int NC = 2;
        int NS = 2;
        int numClienti = 5;

        Semaphore spogliatoi = new Semaphore(NS);
        Semaphore armdietti = new Semaphore(NC);

        for(int i=0; i<numClienti; i++){
            new ClientDeadLock(i, spogliatoi, armdietti).start();
        }
    }
}