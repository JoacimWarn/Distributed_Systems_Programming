package Clientside;

import java.io.IOException;
import java.io.*;
import java.net.Socket;

public class ghaals{
private static Socket socket;

public static void main(String[] args) {

     String host = "localhost";
     int port    = 4354;
    
        try { 
        	socket = new Socket(host, port);
     
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)); 

             Thread input = new Thread(() -> {
             String msg;
             try {
                 while ((msg = in.readLine()) != null) {
                     System.out.println(msg);
                 }
             } catch (Exception e) {
                 e.printStackTrace();
             }
         });
         input.start();

         String userName = "ghaals" ;
         String msg;
         try {
             while ((msg = stdIn.readLine()) != null) {
                 for (int i = 0; i < msg.length(); i++)
                     System.out.print("\b");
                 out.write(userName + ": " + msg + "\n");
                 out.flush();
             }
         } catch (Exception e) {
             e.printStackTrace();
         }

     } catch (IOException e) {
         e.printStackTrace();
     }

 }

}
