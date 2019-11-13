package internetchatserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

//ConnectionListener.java
public class ConnectionListener implements Runnable {
	private static int          port = 4354;
	private Server          server;
	private ServerSocket    socket;
	private boolean         bool;
	private Thread          t;

	public ConnectionListener(Server server) {
		this.server = server;
		this.socket = server.getSocket();
		bool = false;
	}

	public synchronized void start() {

		if ( bool)
		return;
		bool = true;
		t = new Thread(this);
		t.start();
	}

	public synchronized void stop() {

		if (!bool)
		
		return;
		// System.out.print("End of the connection listener on:\n"+ "localhost "+ socket.getLocalSocketAddress());

		bool = false;

		try {
			
			InetAddress ip = InetAddress.getLocalHost(); 
			System.out.println("Listening for connections on:\n" +"localhost "+ ip + " at port: " + port);

			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			// join is method to Wait for the thread to finish its execution, possibly with a timeout
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Closed...!");
	}

	@Override
	public void run() {
		
		try {
			InetAddress ip = InetAddress.getLocalHost(); 
			System.out.println("Listening for connections on:\n" +"localhost "+ ip + " at port: " + port);

			
			while (bool) {
				Socket request = socket.accept();
				Connection connection = new Connection(request);
				server.addConnection(connection);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public boolean isAlive() {

		return bool;
	}

}
