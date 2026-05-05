import java.util.LinkedList;

public class CodaFIFO {
    private LinkedList<String> buffer = new LinkedList<>();

    // Metodo per il produttore
    public synchronized void inserisci(String s) {
        buffer.addLast(s);
        // Sveglia i consumatori che stavano aspettando una stringa
        notifyAll(); 
    }

    // Metodo per il consumatore
    public synchronized String preleva() throws InterruptedException {
        // Se la coda è vuota, il thread si mette in pausa (wait)
        while (buffer.isEmpty()) {
            wait();
        }
        return buffer.removeFirst();
    }
}