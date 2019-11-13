package internetchatserver;



//Java implementation for multible threaded chat client 




import java.util.ArrayList; 



/*
* This is  server side, that is the handler for the connected clients
* Through the Runnable interface or run method encapsulates code that will run
in parallel, we use here runnable class
*/
public class Client implements Runnable {

    public static final long time = 7;
    private Connection  connection;
    private boolean     alive;
    private Thread      t;
    static ArrayList<Client> array ; 

public Client(Connection connection, ArrayList<Client> array) {
        this.connection = connection;
        Client.array = array;
        alive = false;
    }

    //start method to start thread
    
public synchronized void startSession() {

        if (alive)
        alive = true;
        t = new Thread(this);
        t.start();

    }


public synchronized void closeSession() {

        if (!alive)
        alive = false;

        try {
            connection.close();
            t.join();    //Waits for the thread to finish its execution
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
 /* the thread execution code */ 
 public void run() {

        while (connection.isAlive()) {

            String in = connection.read();
            if (in != null) {
                System.out.println(in);
                for (Client c : array) {
                    c.send(in);
                }
            } else {
                try {
//Makes the currently executing thread sleep for milliseconds.
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public void send(String msg) {

        connection.write(msg + "\n");
        connection.flush();
    }

}

