import java.util.concurrent.Semaphore;

public class Scatola {
    public static void main(String[] args){
        int P = 5;
        Semaphore cioccolatiniDisponibili = new Semaphore(0); //per i mangiatori
        Semaphore svegliaPasticcere = new Semaphore(1); //per il pasticcere (pongo a 1 perchè deve riempire subito la scatola)
        Semaphore mutex = new Semaphore(1); //per la variabile del conteggio
        Mangiatore.setConteggio(5);
        
        Pasticcere pasticcere = new Pasticcere(cioccolatiniDisponibili, svegliaPasticcere, P);
        pasticcere.start();

        for(int i=0; i<=3; i++){
            new Mangiatore(i, cioccolatiniDisponibili, svegliaPasticcere, mutex).start();
        }

    }
}
