import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ClientProdEcho{
    public static void main(String[] args){
        Scanner tastiera = new Scanner(System.in);

        try{
            Socket socket = new Socket("localhost",4242);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            pw.println("producer");
            pw.flush();
            String risposta = br.readLine();
            if("okprod".equals(risposta)){
                System.out.println(risposta);
                System.out.print("> ");

                String stringaDaInviare = tastiera.nextLine();

                pw.println(stringaDaInviare);
                pw.flush();

                System.out.println("Stringa inviata: " + stringaDaInviare);

                String conferma = br.readLine();
                if("okins".equals(conferma)){
                    System.out.println(conferma);
                }
            }
            else    
                System.out.println("ERRORE : il server non ha risposto");
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