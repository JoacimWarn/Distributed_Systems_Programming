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
			
			ObjectInputStream inStream = new ObjectInputStream(clientListener.getInputStream());
			ObjectOutputStream outStream = new ObjectOutputStream(clientListener.getOutputStream());
			
			ClientHandler newClientHandler = new ClientHandler(clientListener, numberOfClients, dis, dos, inStream, outStream);
			
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

class ClientHandler implements Runnable, Serializable{
	
	private Scanner clientInputKeyboard = new Scanner(System.in);
	private Socket client;
	private long clientId;
	private boolean isAlive;
	
	private DataInputStream dis;
	private DataOutputStream dos;
	
	private ObjectOutputStream outStream;
	private ObjectInputStream inStream;
	
	public ClientHandler(Socket c, long id, DataInputStream dis, DataOutputStream dos, ObjectInputStream inStream, ObjectOutputStream outStream){
		
		client = c;
		clientId = id;
		
		this.dis = dis;
		this.dos = dos;
		
		this.inStream = inStream;
		this.outStream = outStream;
		
		isAlive = true;
		
	}
	
	@Override
	public void run(){
		
		String recvMsg;
		//String msgToSend;
		//F:/Distributed_Systems_Programming/Task_3/serverFiles/
		
		//While client is active, keep the socket established
		while(isAlive){
			
			//The clients serilizable folders
			
			//The server's client folder
			//File serverClientFolders = new File("F:/Distributed_Systems_Programming/Task_3/serverFiles/");
			//File[] listOfFiles = serverClientFolders.listFiles();
			
			try{
				
				//recieve message
				recvMsg = dis.readUTF();
				
					 if(recvMsg.equals("exit")) isAlive = false;
				else if(recvMsg.equals("sync")){
					
					System.out.println("time to sync");
					
					/*
					//locate server's client folder and then send back .dat file with content
					File serverClientFolders = new File("C:/Distributed_Systems_Programming/Task_3/serverFiles/");
					File[] listOfFiles = serverClientFolders.listFiles();
					
					File[] serverFolders = listOfFiles;
					
					for(File file : serverFolders){
						
						System.out.println(file.getName());
						
					}
					*/
					
				}
				
			}
			catch(Exception e){System.err.println(e);}
			
			/*
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
			*/
			
		}
		
		try{
			
			client.close();
			dis.close();
			dos.close();
			inStream.close();
			outStream.close();
			
		}
		catch(Exception e){System.err.println(e);}
		
	}
	
}