import java.net.*;
import java.io.*;
import java.util.*;

public class Server{
	
	static String[] board = {"Ones", "Twos", "Threes", "Fours", "Fives", "Sixes",
					  "Sum", "Bonus", "Three of a kind", "Four of a kind",
					  "Full house", "Small straight", "Large straight",
					  "Chance", "YAHTZEE"};
	
	static final int NR_OF_GAMES = 5;
	static boolean[] gameRoomIsActive = new boolean[NR_OF_GAMES];
	static int[] numberOfPlayersInRoom = new int[NR_OF_GAMES];
	static int numberOfClients;
	
	static int[] nrOfYes = new int[NR_OF_GAMES];
	static int[] nrOfAnswers = new int[NR_OF_GAMES];
	
	static ArrayList<ClientHandler> clientArray = new ArrayList<>();
	static Vector<GameHandler> gameRooms = new Vector<>();
	
	public static void main(String[] args) throws IOException{
		
		numberOfClients = 0;
		
		ServerSocket theServerSocket = new ServerSocket(1337);
		
		Socket clientListener;
		
		//create a number of game rooms with independent id's
		for(int i = 0; i < NR_OF_GAMES; i++){
			
			gameRoomIsActive[i] = false;
			numberOfPlayersInRoom[i] = 0;
			nrOfYes[i] = 0;
			nrOfAnswers[i] = 0;
			
			/*
			GameHandler newGameHandler = new GameHandler(i);
			Thread gameThread = new Thread(newGameHandler);
			gameRooms.add(newGameHandler);
			gameThread.start();
			*/
		}
		
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

/*
class GameHandler implements Runnable{
	
	private int clientId;
	boolean gameIsActive;
	int gameRoomId;
	
	public GameHandler(int gameRoomId){
		
		this.gameRoomId = gameRoomId;
		gameIsActive = false;
		
	}
	
	@Override
	public void run(){
		
		try{
			
			int nrOfYes;
			int nrOfNo;
			
			while(!gameIsActive){
				
				nrOfNo = 0;
				nrOfYes = 0;
				
				//Polling all players in this lobby to see if they want to start playing
				for(ClientHandler iterator : Server.clientArray){
					
					//System.out.println(iterator.gameRoomId + " " + gameRoomId);
					
					if(iterator.gameRoomId == gameRoomId){
						
						String recvMsg;
						//gameIsActive = true;
						
						//Found a player in lobby, send message to see if want to start
						iterator.dos.writeUTF("Type 1 to start, 0 to wait");
						recvMsg = iterator.dis.readUTF();
						
						if(recvMsg.equals("0")){
							
							nrOfNo++;
							
						}
						if(recvMsg.equals("1")){
							
							nrOfYes++;
							
						}
						
					}
					
				}
				
				if(nrOfNo == 0 && nrOfYes > 0){
					
					gameIsActive = true;
					
				}
				
			}
			
			//gameIsActive = true;
			
			while(gameIsActive){
				
				//All players agreed to start the game
				
				
			}
			
		}
		catch(Exception e){System.err.println(e);}
		
	}
	
}
*/




class ClientHandler implements Runnable{
	
	private Scanner clientInputKeyboard = new Scanner(System.in);
	private Socket client;
	private int clientId;
	private int gameRoomId;
	private int points;
	private boolean isAlive;
	private boolean playerIsInARoom;
	private boolean spectatorMode;
	
	
	
	private DataInputStream dis;
	private DataOutputStream dos;
	
	public ClientHandler(Socket c, int id, DataInputStream dis, DataOutputStream dos){
		
		points = 0;
		gameRoomId = -1;
		client = c;
		clientId = id;
		this.dis = dis;
		this.dos = dos;
		isAlive = true;
		playerIsInARoom = false;
		spectatorMode = false;
		
	}
	
	@Override
	public void run(){
		
		String recvMsg;
		String msgToSend;
		
		while(isAlive){
			
			try{
				
				while(!playerIsInARoom){
					
					dos.writeUTF("List of game rooms");
					
					for(int i = 0; i < Server.NR_OF_GAMES; i++){
						
						if(Server.gameRoomIsActive[i]){
							
							dos.writeUTF("Game:" + (i + 1) + " join as spectator");
							
						}
						else{
							
							dos.writeUTF("Game:" + (i + 1));
							
						}
						
					}
					
					/*
					for(ClientHandler clientIterator : Server.clientArray){
						
						if(!clientIterator.playerIsInARoom){
							
							clientIterator.dos.writeUTF("List of game rooms");
							
							for(int i = 0; i < Server.NR_OF_GAMES; i++){
								
								if(Server.gameRoomIsActive[i]){
									
									clientIterator.dos.writeUTF("Game:" + (i + 1) + " join as spectator");
									
								}
								else{
									
									clientIterator.dos.writeUTF("Game:" + (i + 1));
									
								}
								
							}
							
						}
						
					}
					*/
					
					//msgToSend = clientInputKeyboard.nextLine();
					
					recvMsg = dis.readUTF();
					
					if(recvMsg.equals("exit")){
						
						isAlive = false;
						
					}
					else{
						
						switch(recvMsg){
							
						case "1":
							gameRoomId = 1;
							playerIsInARoom = true;
							Server.numberOfPlayersInRoom[gameRoomId-1]++;
							break;
							
						case "2":
							gameRoomId = 2;
							playerIsInARoom = true;
							Server.numberOfPlayersInRoom[gameRoomId-1]++;
							break;
							
						case "3":
							gameRoomId = 3;
							playerIsInARoom = true;
							Server.numberOfPlayersInRoom[gameRoomId-1]++;
							break;
							
						case "4":
							gameRoomId = 4;
							playerIsInARoom = true;
							Server.numberOfPlayersInRoom[gameRoomId-1]++;
							break;
							
						case "5":
							gameRoomId = 5;
							playerIsInARoom = true;
							Server.numberOfPlayersInRoom[gameRoomId-1]++;
							break;
							
						default:
							
							break;
							
						}
						
						if(Server.gameRoomIsActive[gameRoomId-1]){
							
							spectatorMode = true;
							Server.numberOfPlayersInRoom[gameRoomId-1]--;
							
						}
						
					}
					
				}
				
				//Player has entered a room
				while(!Server.gameRoomIsActive[gameRoomId-1]){
					
					Server.nrOfYes[gameRoomId-1] = 0;
					Server.nrOfAnswers[gameRoomId-1] = Server.numberOfPlayersInRoom[gameRoomId-1];
					
					dos.writeUTF("Type 1 to start, 0 to wait");
					
					String clientMessage;
					
					clientMessage = dis.readUTF();
					
					if(clientMessage.equals("1")){
						
						Server.nrOfYes[gameRoomId-1]++;
						Server.nrOfAnswers[gameRoomId-1]--;
						
					}
					else{
						
						Server.nrOfAnswers[gameRoomId-1]--;
						
					}
					
					//Synchronize waiting for answers for all clients
					Thread.sleep(10000);
					
					/*
					//Polling all users in the lobby
					for(ClientHandler clientIterator : Server.clientArray){
						
						if(clientIterator.gameRoomId == gameRoomId){
							
							clientIterator.dos.writeUTF("Type 1 to start, 0 to wait");
							
							clientMessage = clientIterator.dis.readUTF();
							
							//System.out.println(clientMessage);
							
							if(clientMessage.equals("1")){
								
								//clientIterator.dos.writeUTF("You pressed yes!!!");
								Server.nrOfYes[gameRoomId-1]++;
								Server.nrOfAnswers[gameRoomId-1]--;
								
							}
							else{
								
								Server.nrOfAnswers[gameRoomId-1]--;
								
							}
							
						}
						
					}
					*/
					
					//waiting for all players have answered
					//while(Server.nrOfAnswers[gameRoomId-1] != 0);
					
					//System.out.println("gameRoomIsActive:" + Server.gameRoomIsActive[gameRoomId-1]);
					
					if(Server.nrOfYes[gameRoomId-1] == Server.numberOfPlayersInRoom[gameRoomId-1]){
						
						Server.gameRoomIsActive[gameRoomId-1] = true;
						
					}
					
				}
				
				int nrOfPlayers = Server.numberOfPlayersInRoom[gameRoomId-1];
				dos.writeUTF("Game has started with:" + nrOfPlayers + " players");
				
				Random rand = new Random();
				
				int nrOfDices = 6;
				int[] dices = new int[nrOfDices];
				
				//13 rounds maximum
				
				while(Server.gameRoomIsActive[gameRoomId-1]){
					
					dos.writeUTF("\nRolling the dices\n");
					
					for(int i = 0; i < nrOfDices; i++){
						
						rand = new Random();
						
						dices[i] = rand.nextInt((6 - 1) + 1) + 1;
						
						dos.writeUTF("" + dices[i]);
						
					}
					
					for(int i = 0; i < 13; i++){
						
						dos.writeUTF(Server.board[i] + "\t\t" + dices[2]);
						
					}
					
					Thread.sleep(5000);
					
					//dos.writeUTF("roll the dice");
					
					//Thread.sleep(1000 * nrOfPlayers);
					
				}
				
				/*
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
				*/
				
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