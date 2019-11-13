import java.net.*;
import java.io.*;

public class Server{
	
	private ServerSocket socket;
	private final int port = 1337;
	private static int clientId;
	
	//Initialize new ServerSocket
	public void init(){
		
		try{
			
			socket = new ServerSocket(port);
			clientId = 0;
			
			while(true){
				//Polling for new clients to access
				new ClientThread(socket.accept(), clientId).start();
				clientId++;
				
			}
			
		}
		catch(Exception e){System.err.println(e);}
		
	}
	
	public void terminate(){
		
		try{
			
			socket.close();
			
		}
		catch(Exception e){System.err.println(e);}
		
	}
	
	
	
	private class ClientThread extends Thread{
		
		private Socket client;
		private BufferedReader input;
		private PrintWriter output;
		private int clientId;
		
		public ClientThread(Socket client, int clientId){
			
			this.client = client;
			this.clientId = clientId;
			
		}
		
		public void run(){
			//System.out.println("hej");
			try{output = new PrintWriter(client.getOutputStream(), true);}
			catch(Exception e){System.err.println(e);}
			
			try{input = new BufferedReader(new InputStreamReader(client.getInputStream()));}
			catch(Exception e){System.err.println(e);}
			
			String clientInput = "";
			
			while(true){
				
				
				
				try{clientInput = input.readLine();}
				catch(Exception e){System.err.println(e);}
				
				//If client loses connection
				//if(client == null) break; <<------ problem
				
				//If client wants to exit manually
				if(clientInput.equals("EXIT")){
					
					//System.out.println("du loggade av");
					break;
					
				}
				
				//System.out.println("User" + clientId + ":" + clientInput);
				
				output.println("User" + clientId + ":" + clientInput);
				//output = "User" + clientId + " " + clientInput;
				
			}
			
			//System.out.println("du loggade av");
			
			output.println("User" + clientId + " disconnected");
			
			//System.out.println("du loggade av");
			
			try{
				
				input.close();
				output.close();
				client.close();
				
			}
			catch(Exception e){System.err.println(e);}
			
			//System.out.println("User" + clientId + " disconnected");
			
		}
		
	}
	
	public static void main(String[] args){
		
		Server newServer = new Server();
		newServer.init();
		
		
	}
	
}