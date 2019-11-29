import java.net.*;
import java.io.*;
import java.util.*;

public class Server{
	
	static Vector<ClientHandler> clientArray = new Vector<>();
	
	public static void main(String[] args) throws IOException{
		
		int numberOfClients = 0;
		
		ServerSocket theServerSocket = new ServerSocket(1337);
		
		Socket clientListener;
		
		//polling for new clients
		while(true){
			
			clientListener = theServerSocket.accept();
			
			System.out.println("New client:" + clientListener);
			
			DataInputStream dis = new DataInputStream(clientListener.getInputStream());
			DataOutputStream dos = new DataOutputStream(clientListener.getOutputStream());
			
			ClientHandler newClientHandler = new ClientHandler(clientListener, numberOfClients, dis, dos);
			
			//creating a new thread for this client
			Thread clientThread = new Thread(newClientHandler);
			
			System.out.println("Adding new client to thread");
			
			//add client thread to array of clients
			clientArray.add(newClientHandler);
			
			//start the client thread
			clientThread.start();
			
			numberOfClients++;
			
		}
		
	}
	
}

class ClientHandler implements Runnable{
	
	private Scanner clientInputKeyboard = new Scanner(System.in);
	private Socket client;
	private int clientId;
	private boolean isAlive;
	
	private DataInputStream dis;
	private DataOutputStream dos;
	
	public ClientHandler(Socket c, int id, DataInputStream dis, DataOutputStream dos){
		
		client = c;
		clientId = id;
		this.dis = dis;
		this.dos = dos;
		isAlive = true;
		
	}
	
	@Override
	public void run(){
		
		String recvMsg;
		String msgToSend;
		
		while(true){
			
			try{
				
				if(!isAlive) break;
				
				
				//recieve message
				recvMsg = dis.readUTF();
				
				System.out.println(recvMsg);
				
				if(recvMsg.equals("EXIT")){
					
					//Let all clients know that the user has disconnected
					for(ClientHandler iterator : Server.clientArray){
						
						if(iterator.isAlive){
							
							iterator.dos.writeUTF("User" + clientId + " has disconnected");
							//iterator.dos.writeUTF("User" + clientId + ":" + msgToSend);
							
						}
						
					}
					
					isAlive = false;
					break;
					
				}
				
				//msgToSend = clientInputKeyboard.nextLine();
				
				
				//iterator through all the clients in the array
				for(ClientHandler iterator : Server.clientArray){
					
					if(iterator.isAlive){
						
						iterator.dos.writeUTF("User" + clientId + ":" + recvMsg);
						//iterator.dos.writeUTF("User" + clientId + ":" + msgToSend);
						
					}
					
				}
				
			}
			catch(Exception e){System.err.println(e);}
			
		}
		
		try{
			
			client.close();
			dis.close();
			dos.close();
			
		}
		catch(Exception e){System.err.println(e);}
		
	}
	
}