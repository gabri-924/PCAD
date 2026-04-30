public class RWBasic{
    private int data = 0;

    public int read(){
        return data;
    }

    public void write(){
        int tmp = data;
        try {
            Thread.sleep(10); 
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tmp = tmp + 1;
        data = tmp;
    }
}