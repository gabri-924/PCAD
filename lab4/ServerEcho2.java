import java.net.*;
public class ServerEcho2{
    public static void main(String[] args){
        CodaFIFO2 fifo = new CodaFIFO2(5);

        try(ServerSocket server=new ServerSocket(4242)){
            System.out.println("Server pronto...");
            while(true){
                Socket socket=server.accept();
                new Thread(new ServiceEcho2(socket, fifo)).start();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
} 
    
    
