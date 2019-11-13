public class StartServer{
	
	public static void main(String[] args){
		
		Server ventrilo = new Server();
		ventrilo.init(1337);
		
		//Client client1 = new Client();
		//client1.connectNewClient("127.0.0.1", 1337);
		
	}
	
}