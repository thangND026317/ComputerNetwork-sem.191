package peer2peer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

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
//			}
	
		}
		
		catch (IOException e) {
			System.err.println(e);
		}
	}

	public boolean sendMessage(String message) {
		try {
			DataOutputStream send = new DataOutputStream(_socket.getOutputStream());
			while (true) {
				//System.out.println(message);
				send.writeUTF(message);
				send.flush();
				return true;
			}
		}
		
		catch (SocketException e) {
			System.out.println(e);
			return false;
		}
		
        catch (IOException e) {
        	System.out.println(e);
        	return false;
        }
		
	}
	
	public String receiveMessage() {
		String message = "";
		try {
			DataInputStream receive = new DataInputStream(_socket.getInputStream());
			while (true) {
				message = receive.readUTF();
				//System.out.println(message);
				return message;
			}
		}
        
		catch (SocketException e) {
			System.out.println(e);
			return message;
		}
		
        catch (IOException e) {
        	System.out.println(e);
        	return message;
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

	public static void main(String args[]) throws IOException {
		
		final int port_number = 5000;
		
		Server server = new Server(port_number);
		//BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		server.execute();
//		
//		System.out.println(server.receiveMessage());
//		
//		server.sendMessage(input.readLine());
//		
//		server.close();
	}
}

