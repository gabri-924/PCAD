public class Main{
    public static void main(String[] args){
        PoloNord polo = new PoloNord();

        Thread babbo = new Thread(new BabboNatale(polo));
        babbo.start();

        for(int i=0; i<9; i++){
            new Thread(new Renna(polo)).start();
        }

        for (int i = 1; i <= 6; i++) {
            new Thread(new Elfo(polo)).start();
        }

    }
}