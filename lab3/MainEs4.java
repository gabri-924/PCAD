public class MainEs4{
    public static void main(String[] args){
        RWext shared = new RWext();
        int n = 50;
        
        Thread[] r = new Thread[n];
        Thread[] w = new Thread[n];

        for (int i = 0; i < n; i++) {
            r[i] = new Thread(new Reader(shared, i));
            w[i] = new Thread(new Writer(shared, i));
        }

        for (int i = 0; i < n; i++) {
            r[i].start();
            try{Thread.sleep(15);}catch(InterruptedException e){}
            w[i].start();
            try{Thread.sleep(15);}catch(InterruptedException e){}
        }

        for (int i = 0; i < n; i++) {
            try {
                r[i].join();
                w[i].join();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }

        System.out.println("\n--- TEST COMPLETATO ---");
        System.out.println("Valore finale: " + shared.read());
    }
}