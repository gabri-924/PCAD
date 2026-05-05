import java.util.LinkedList;

public class CodaFIFO2 {
    private LinkedList<String> buffer = new LinkedList<>();
    private final int maxCapacità;

    public CodaFIFO2(int maxCapacità){
        this.maxCapacità = maxCapacità;
    }

    // Metodo per il produttore
    public synchronized void inserisci(String s) throws InterruptedException{
        while(buffer.size() == maxCapacità){
            System.out.println("Coda piena. Attendi...");
            wait();
        }
        buffer.addLast(s);
        // Sveglia i consumatori che stavano aspettando una stringa
        notifyAll(); 
    }

    // Metodo per il consumatore
    public synchronized String preleva() throws InterruptedException {
        // Se la coda è vuota, il thread si mette in pausa (wait)
        while (buffer.isEmpty()) {
            System.out.println("Coda vuota. Attendi...");
            wait();
        }
        String s = buffer.removeFirst();
        notifyAll();
        return s;
    }
}