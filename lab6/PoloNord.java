public class PoloNord{
    private int renneAlPolo = 0;
    private int elfiInAttesa = 0;
    private boolean babboInConsegna = false;

    public synchronized void BabboDorme() throws InterruptedException{
        while(renneAlPolo < 9 && elfiInAttesa < 3){
            wait();
        }
        System.out.println("Babbo si è svegliato");
    }

    public synchronized void BabboLavora() throws InterruptedException{
        if(renneAlPolo == 9){
            System.out.println("Le renne sono pronte");
            babboInConsegna = true;

            Thread.sleep(2000);
            System.out.println("I regali sono stati consegnati");
            renneAlPolo = 0;
            babboInConsegna = false;
            notifyAll();
        }
        else if(elfiInAttesa == 3){
            System.out.println("Ci sono tre elfi con problemi");
            Thread.sleep(1000);
            System.out.println("Problemi risolti");
            elfiInAttesa = 0;
            notifyAll();
        }
    }

    public synchronized void RennaArriva() throws InterruptedException{
        renneAlPolo++;
        System.out.println("E' arrivata una renna (" + renneAlPolo +")");

        if(renneAlPolo == 9)
            notifyAll();
        
        while(renneAlPolo > 0){
            wait();
        }
    }

    public synchronized void ElfoChiedeAiuto() throws InterruptedException{
        while(elfiInAttesa >= 3 || babboInConsegna){
            wait();
        }

        elfiInAttesa++;
        System.out.println("Elfi in attesa: " + elfiInAttesa + "/3");

        if(elfiInAttesa == 3)
            notifyAll();

        while(elfiInAttesa > 0){
            wait();
        }
    }
}