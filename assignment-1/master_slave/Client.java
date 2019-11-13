package master_slave;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
	private InetAddress _host;
	private int _port_number;
	private Socket _socket;
 
	public Client(InetAddress host, int port_number) {
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
			
			System.out.print("Enter your name: ");
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			String name = input.readLine();
			
			System.out.println("\nStart chatting now, type and Enter to send!\n");
						
			ReceivingClient receive = new ReceivingClient(_socket);
			receive.start();
			
			SendingClient send = new SendingClient(_socket, name);
			send.start();
			
		}
		
		catch (UnknownHostException e) {
			System.err.println("I can't find " + _host);
		}
		
		catch (IOException e) {
			System.err.println(e);
		}
	}
	
	private class ReceivingClient extends Thread {
		private Socket _socket;
		
		public ReceivingClient(Socket socket) {
			_socket = socket;
		}
		
		public void run() {
			DataInputStream receive = null;
			try {
				receive = new DataInputStream(_socket.getInputStream());
				while (true) {
					String message = receive.readUTF();
					System.out.println(message);
				}
			}
	        
			catch (IOException e) {
				try {
					System.err.println(e);
					_socket.close();
				}
				
				catch (IOException ex) {
					System.err.println(ex);
				}
			}
			
		}

	}
	
	private class SendingClient extends Thread {
		private Socket _socket;
		private String _name;
		
		public SendingClient(Socket socket, String name) {
			_socket = socket;
			_name = name;
		}

		public void run() {
			BufferedReader input = null;
			DataOutputStream send = null;
			try {
				input = new BufferedReader(new InputStreamReader(System.in));
				send = new DataOutputStream(_socket.getOutputStream());
				while (true) {
					String new_message = input.readLine();
					if (new_message.equalsIgnoreCase("logout")) {
						logout();
						break;
					}
					
					String message = _name + ": " + new_message;
					System.out.println(message);
					send.writeUTF(message);
					send.flush();
				}
			}
			
			catch (IOException e) {
				System.out.println(e);
				logout();
			}
		}
	   
	}
	
	public void logout() {
		try {
			System.out.println("\nLoged out!");
			_socket.close();
		}

		catch (IOException e) {
			System.err.println(e);
			return;
		}
	}

//********************************** Demo **********************************
	public static void main(String args[]) throws IOException {
		
		final int port_number = 5000;
		Client client = new Client(InetAddress.getLocalHost(), port_number);
		client.execute();

	}
}

