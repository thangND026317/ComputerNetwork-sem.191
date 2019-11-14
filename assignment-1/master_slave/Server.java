package master_slave;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Server { 
	private Socket _socket;
	private ServerSocket server_socket;
	private int _port_number;
	private SimpleDateFormat _date;
	
	private static ArrayList<Socket> client_list = new ArrayList<Socket>();
	
	public Server(int port_number) { 
		_port_number = port_number;
	}
	
	public void execute() {
		try {
			System.out.println("Server is opening ....\n");
			server_socket = new ServerSocket(_port_number);
			 
			System.out.println("Server is opened: " + server_socket);
			System.out.println(_date.format(new Date()));
			System.out.println("**********************************************");
			
			SendingServer send = new SendingServer();
			send.start();
			
			System.out.println("Waiting for a client ....\n");
			while (true) {
				_socket = server_socket.accept();
				
				System.out.println("A new client's accepted!" + _socket.getInetAddress() + _socket.getPort());
				System.out.println("Adding this client to client list");
				
				client_list.add(_socket);
				
				ReceivingServer receive = new ReceivingServer(_socket);
				receive.start();
			}
	
		}
		
		catch (IOException e) {
			System.err.println(e);
		}
	}

	private class ReceivingServer extends Thread {
		private Socket _socket;
		
		public ReceivingServer(Socket socket) {
			_socket = socket;
		}
		
		public void run() {
			DataInputStream receive = null;
			try {
				receive = new DataInputStream(_socket.getInputStream());
				while (true) {
					String message = receive.readUTF();
					System.out.println(message);
					
//					if (message.substring(message.indexOf(": ") + 2).equalsIgnoreCase("logout")) {
//						receive.close();
//						client_list.remove(_socket);
//						_socket.close();
//						continue;
//					}
					
					for (Socket socket : client_list) {
						if (socket.getPort() == _socket.getPort()) continue;
						DataOutputStream send = new DataOutputStream(socket.getOutputStream());
						send.writeUTF(message);
						send.flush();
					}
				}
			}
	        
			catch (Exception e) {
				try {
					System.out.println(e);
					receive.close();
					_socket.close();
				}
				catch (IOException ex) {
					System.out.println("Server Closed!");
		        }
			}
			
		}

	}
	
	private class SendingServer extends Thread {

		public void run() {
			BufferedReader input = null;
			DataOutputStream send = null;
			try {
				input = new BufferedReader(new InputStreamReader(System.in));
				while (true) {
					String message = "Server: " + input.readLine();
					System.out.println(message);
					for (Socket socket : client_list) {
						send = new DataOutputStream(socket.getOutputStream());
						send.writeBytes(message);
						send.flush();
					}
				}
			}
			
			catch (Exception e) {
				closeServer();
			}
					
		}
	   
	}
	public void closeServer() {
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
	
//********************************** Demo **********************************

	public static void main(String args[]) throws UnknownHostException {
		
		final int port_number = 6969;
		
		System.out.println(InetAddress.getLocalHost());
		
		Server server = new Server(port_number);
		
		server.execute();
	}
}

