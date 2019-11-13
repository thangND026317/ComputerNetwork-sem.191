package chat1to1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server { 
	private Socket _socket;
	private ServerSocket server_socket;
	private int _port_number;
	
	
//	private static ArrayList<Socket> client_list = new ArrayList<Socket>();
//	private static int counter = 0;
	

	public Server(int port_number) {
		// starts server and waits for a connection 
		_port_number = port_number;
	}
	
	public void execute() {
		try {
			System.out.println("Server is opening ....\n");
			server_socket = new ServerSocket(_port_number);
			 
			System.out.println("Server is opened!");
			System.out.println("Waiting for a client ....\n");
			
			//while (true) {
				
				_socket = server_socket.accept();
				
				System.out.println("A new client's accepted!");
				// System.out.println("Creating a new handler for this client ...."); 
				// System.out.println("Adding this client to active client list");
				
				System.out.println("\nStart chatting now, type and Enter to send!\n");
				
				Receiving receive = new Receiving(_socket);
				receive.start();
				
				Sending send = new Sending(_socket, "Server");
				send.start();
			//}
	
		}
		
		catch (IOException e) {
			System.err.println(e);
		}
	}

	
	public void close() {
		System.out.println("Closing server ....");
		try {
			_socket.close();
			server_socket.close();
			System.out.println("Server is closed!");
		}

		catch (IOException e) {
			System.err.println(e);
		}
	}
	
//************************** Demo *********************************

	public static void main(String args[]) {
		
		final int port_number = 5000;
		
		
		
		Server server = new Server(port_number);
		
		server.execute();
	}
}

