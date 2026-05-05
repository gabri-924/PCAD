import java.net.*;
public class ServerEcho{
    public static void main(String[] args){
        CodaFIFO fifo = new CodaFIFO();

        try(ServerSocket server=new ServerSocket(4242)){
            System.out.println("Server pronto...");
            while(true){
                Socket socket=server.accept();
                new Thread(new ServiceEcho(socket, fifo)).start();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
} 
    
    
