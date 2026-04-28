public class MainEs4{
    public static void main(String[] args){
        RWext shared = new RWext();
        int n = 50;
        
        Thread[] r = new Thread[n];
        Thread[] w = new Thread[n];

        // Creazione thread
        for (int i = 0; i < n; i++) {
            r[i] = new Thread(new Reader(shared, i));
            w[i] = new Thread(new Writer(shared, i));
        }

        // Avvio thread
        // Nota: se avviassi tutti i writer prima dei lettori, 
        // vedresti i writer bloccarsi uno dopo l'altro.
        for (int i = 0; i < n; i++) {
            w[i].start();
            try{Thread.sleep(10);}catch(InterruptedException e){}
            r[i].start();
            try{Thread.sleep(10);}catch(InterruptedException e){}
        }

        // Join
        for (int i = 0; i < n; i++) {
            try {
                w[i].join();
                r[i].join();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }

        System.out.println("\n--- TEST COMPLETATO ---");
        System.out.println("Valore finale: " + shared.read());
    }
}