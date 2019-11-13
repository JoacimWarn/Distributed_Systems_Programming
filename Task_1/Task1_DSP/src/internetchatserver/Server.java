package internetchatserver;




import java.io.*; 
import java.util.*;
import java.net.*; 

//Java implementation of  Server 

public class Server {
	private static int          port = 4354; //we can choose on port between 0 to 65535.
    private static ServerSocket        socket;
    private ConnectionListener  connectionListener;
    static ArrayList<Client>    array = new ArrayList<>();  // array list to store active clients 
  
 public Server(int port) {
	
        try {
            socket = new ServerSocket(port);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }

        connectionListener = new ConnectionListener(this);
    }
/* Starts the execution of the thread, effectively the run method is called and run in parallel*/

public void start() throws IOException {

        connectionListener.start();
        // obtain input streams
        BufferedReader In = new BufferedReader(new InputStreamReader(System.in));
        String input;
        while (((input = In.readLine()) != null) && connectionListener.isAlive()) {
            if (input.equalsIgnoreCase("logout")) {
                break;
            } 
            else
            {
                for (int i = 0; i < input.length(); i++)
                System.out.print("\b");
                System.out.println("Server: " + input);
                for (Client c : array) {
                    c.send("Server: " + input);
                }
            }

        }
        stop();
        
    }

  public void stop() {

        connectionListener.stop();
        for (Client c : array) {
            c.closeSession();
        }

        System.out.println("Server terminated!");
    }

 public synchronized void addConnection(Connection conn) {
   if(connectionListener.isAlive()) {
        Client c = new Client(conn, array);
        // add this client to active clients list 
        array.add(c);
        c.startSession();
        System.out.println("Anew client is connected");
   }
   
}
   
    
 
 public synchronized void removeConnection(Connection conn) {
	   if(connectionListener.isAlive()!= true) {
	        Client c = new Client(conn, array);
	        // add this client to active clients list 
	        array.remove(c);
	        c.closeSession();
	        System.out.println("The client left");
	   }
	   
	    }
 

 public ServerSocket getSocket() {

        return socket;
    }

 public static void main(String[] args) throws IOException {
        Server s = new Server(port);
        // start the thread. 
        s.start();

    }

}
