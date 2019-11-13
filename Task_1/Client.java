import java.net.*;
import java.io.*;

public class Client{
	
	private Socket client;
	private PrintWriter output;
	private BufferedReader inputKeyboard;
	private BufferedReader inputServer;
	private final int port = 1337;
	
	public void connectNewClient(String address){
		
		try{
			
			client = new Socket(address, port);
			output = new PrintWriter(client.getOutputStream(), true);
			inputKeyboard = new BufferedReader(new InputStreamReader(System.in));
			inputServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
		}
		catch(Exception e){System.err.println(e);}
		
		String clientInput = "";
		String serverInput = "";
		
		while(true){
			
			try{
				
				clientInput = inputKeyboard.readLine();
				//serverInput = inputServer.readLine();
				//System.out.println(serverInput);
				
			}
			catch(Exception e){System.err.println(e);}
			
			//Sending message to server
			output.println(clientInput);
			
			if(clientInput.equals("EXIT")) break;
			
			try{serverInput = inputServer.readLine();}
			catch(Exception e){System.err.println(e);}
			
			System.out.println(serverInput);
			
			//System.out.println();
			
			//System.out.println(inputServer);
			
		}
		
		
		
		try{serverInput = inputServer.readLine();}
		catch(Exception e){System.err.println(e);}
		
		System.out.println(serverInput);
		
		try{
			
			client.close();
			output.close();
			inputKeyboard.close();
			
		}
		catch(Exception e){System.err.println(e);}
		
	}
	
	public static void main(String[] args){
		
		Client newClient = new Client();
		newClient.connectNewClient("127.0.0.1");
		
	}
	
}