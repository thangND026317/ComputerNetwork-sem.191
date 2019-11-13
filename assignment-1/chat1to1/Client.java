package chat1to1;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private String _host;
	private int _port_number;
	private Socket _socket;
 
	public Client(String host, int port_number) {
		_host = host;
		_port_number = port_number;
	}
	
	
	public void execute() {
		try {
			System.out.println("Connecting to server ....");
			
			_socket = new Socket(_host, _port_number);
			
			System.out.println("Connected to "
					+ _socket.getInetAddress() +" on port "
					+ _socket.getPort() + " from port "
					+ _socket.getLocalPort() + " of "
					+ _socket.getLocalAddress());
					
			System.out.println("\nStart chatting now, type and Enter to send!\n");
			
			Receiving receive = new Receiving(_socket);
			receive.start();
			
			Sending send = new Sending(_socket, "Client");
			send.start();
			
		}
		
		catch (UnknownHostException e) {
			System.err.println("I can't find " + _host);
		}
		
		catch (IOException e) {
			System.err.println(e);
		}
	}
	
	
	public void logout() {
		try {
			System.out.println("\n\nEnd chatting!");
			_socket.close();
		}

		catch (IOException e) {
			System.err.println(e);
			return;
		}
	}

//************************** Demo *********************************
	public static void main(String args[]) throws IOException {
		
		final int port_number = 5000;
		Client client = new Client(InetAddress.getLocalHost().getHostAddress(), port_number);
		client.execute();

	}
}

