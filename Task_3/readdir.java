import java.io.*;
import java.net.*;

public class readdir{
	
	private static final long clientId = 7829136421241571165L;
	
	public static void main(String[] args) throws IOException{
		
		File folder = new File("F:/Distributed_Systems_Programming/Task_3/clientFiles/");
		File[] listOfFiles = folder.listFiles();
		
		File[] localFolders = listOfFiles;
		
		FileOutputStream localFileOut = new FileOutputStream("localFiles.dat");
		ObjectOutputStream output = new ObjectOutputStream(localFileOut);
		
		output.writeObject("clientId:" + clientId + "\n");
		
		for(File local : localFolders){
			
			output.writeObject("\n" + "filename:" + local.getName());
			
			String filnamn = local.getName();
			
			for(int i = 0; i < filnamn.length(); i++){
				
				System.out.print(filnamn.charAt(i));
				
			}
			
			System.out.println();
			
		}
		
		output.close();
		
		System.out.println("hej");
		
	}
	
}