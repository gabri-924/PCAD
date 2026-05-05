import java.net.*;
import java.io.*;

public class ClientConsEcho{
    public static void main(String[] args){
        try{
            Socket socket = new Socket("localhost",4242);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            pw.println("consumer");
            pw.flush();
            String risposta = br.readLine();
            if("okcons".equals(risposta)){
                System.out.println(risposta);
                String stringaRicevuta = br.readLine();
                System.out.println("Stringa ricevuta: " + stringaRicevuta);
            }
            else    
                System.out.println("ERRORE : ricevuto: " + risposta);
            
            pw.close();
            br.close();
            socket.close();
        }
        catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }
}