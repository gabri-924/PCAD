import java.net.*;
import java.io.*;

public class ServiceEcho2 implements Runnable{
    public Socket socket;
    private CodaFIFO2 fifo;

    public ServiceEcho2(Socket socket, CodaFIFO2 fifo){
        this.socket = socket;
        this.fifo = fifo;
    }
    
    public void run(){
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            String tipoClient = br.readLine();
            
            if ("producer".equals(tipoClient)) {
                pw.println("okprod");
                String messaggio = br.readLine();
                if (messaggio != null) {
                    fifo.inserisci(messaggio); // Chiamata al metodo sincronizzato
                    pw.println("okins");
                }
            } 
            else if ("consumer".equals(tipoClient)) {
                pw.println("okcons");
                // Il thread si bloccherà qui dentro se la coda è vuota 
                // grazie al wait() nel metodo preleva
                String estratto = fifo.preleva(); 
                pw.println(estratto);
            }
            socket.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}