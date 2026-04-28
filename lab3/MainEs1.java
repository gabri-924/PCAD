public class MainEs1 {
    public static void main(String[] args){
        RWBasic rw = new RWBasic();
        int numThread = 50;
        Thread[] readers = new Thread[numThread];
        Thread[] writers = new Thread[numThread];

        for(int i=0; i<numThread; i++){
            readers[i] = new Thread(new Reader(rw, i));
            writers[i] = new Thread(new Writer(rw, i));
            writers[i].start();
            readers[i].start();
        }

        for (int i = 0; i < numThread; i++) {
            try {
                writers[i].join();
                readers[i].join();
            } catch (InterruptedException e) {
                System.err.println("Errore durante il join: " + e.getMessage());
            }
        }

        System.out.println("Valore finale atteso: " + numThread);
        System.out.println("Valore finale reale: " + rw.read());
    }
}
