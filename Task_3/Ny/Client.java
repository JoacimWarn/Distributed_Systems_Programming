import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client{
	
	static boolean isAlive = true;
	
	public static void main(String[] args) throws UnknownHostException, IOException{
		
		Scanner clientInput = new Scanner(System.in);
		
		Socket clientSocket = new Socket("127.0.0.1", 1337);
		
		DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
		DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
		
		//ObjectInputStream inStream = new ObjectInputStream(clientSocket.getInputStream());
		//ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
		
		//Send message thread
		Thread sendMessage = new Thread(new Runnable(){
			
			@Override
			public void run(){
				
				while(isAlive){
					
					String msgToSend = clientInput.nextLine();
					
						 if(msgToSend.equals("exit")) isAlive = false;
					else if(msgToSend.equals("sync")){
						
						
						
					}
					
					try{
						
						dos.writeUTF(msgToSend);
						
					}
					catch(Exception e){System.err.println(e);}
					
				}
				
			}
			
		});
		
		/*
		//Recieve message thread
		Thread recieveMessage = new Thread(new Runnable(){
			
			@Override
			public void run(){
				
				while(isAlive){
					
					try{
						
						String msgToRecieve = dis.readUTF();
						System.out.println(msgToRecieve);
						
					}
					catch(Exception e){System.err.println(e);}
					
				}
				
			}
			
		});
		
		recieveMessage.start();
		*/
		sendMessage.start();
		
	}
	
}