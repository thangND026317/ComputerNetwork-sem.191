package peer2peer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;


public class Server { 
	private Socket _socket;
	private ServerSocket server_socket;
	private int _port_number;
	private SimpleDateFormat _date;
	
	// Hash table for username and password
	private static HashMap<String, String> _users = new HashMap<String, String>();
	
	//Hash table for username and socket
	private static HashMap<String, Socket> _userSocket = new HashMap<String, Socket>();
	
	private static int _random_port = 5678;
	
	public Server(int port_number) { 
		_port_number = port_number;
		_date = new SimpleDateFormat("HH:mm:ss - MMM dd, yyyy");
	}
	
	public void execute() {
		try {
			System.out.println("Server is opening ....\n");
			server_socket = new ServerSocket(_port_number);
			 
			System.out.println("Server is opened on port " + server_socket.getInetAddress());
			System.out.println("Time: " + _date.format(new Date()));
			System.out.println("************************************************");
			
			System.out.println("Waiting for a client ....\n");
			while (true) {
				_socket = server_socket.accept();
				
				System.out.println("A new client's accepted! ");
				System.out.println("Adding this client to client list");
				System.out.println("Client: " + _socket.getInetAddress().toString() + " | " +  _socket.getPort());
				
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
					String[] request = message.split("@");
					String result = "";
					
					// For debugging
					//System.out.println(message);
					
					switch (request[0]) {
						case "friendlist":
							Set<String> keys = _userSocket.keySet();
							String list = String.join("@", keys);
							System.out.println(list);
							sending(list, _socket);
							break;
						case "signup":
							result = signUp(request[1], request[2]);
							sending(result, _socket);
							break;
						case "signin":
							result = signIn(request[1], request[2]);
							sending(result, _socket);
							_userSocket.put(request[1], _socket);
							break;
						case "chatto":
							if (!_users.containsKey(request[1])) sending("[Server]: User does not exists!", _socket);
							if (!_userSocket.containsKey(request[1])) sending("[Server]: User is offline!", _socket);
							
							sending(request[2] + "@"+ _random_port,_userSocket.get(request[1]));
							sending("port@" + _random_port++, _socket);
							break;
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
	
	public void sending(String message, Socket s) {
		DataOutputStream send = null;
		try {
			send = new DataOutputStream(s.getOutputStream());
			send.writeUTF(message);
			send.flush();
		}
		
		catch (Exception e) {
			System.err.println(e);
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
	

//**********************************  FUNCTION FOR DATA ATTRIBUTES **********************************
	public String signUp(String username, String password) {
		if (_users.containsKey(username)) return "[Server]: Username is taken!";
		_users.put(username, password);
		return "[Server]: Signed up successfully!";
	}
	
	public String signIn(String username, String password) {
		if (!_users.containsKey(username)) return "[Server]: Incorrect username!";
		if (!_users.get(username).equals(password)) return "[Server]: Incorrect password!";
		return "[Server]: Signed in successfully!";
	}
	
	public String changeUsername(String username, String new_username) {
		if (username.equals(new_username)) return "[Server]: Can't not be the same as old username!";
		if (!_users.containsKey(new_username)) return "[Server]: Username is taken!";
		_users.put(new_username, _users.get(username));
		_userSocket.put(new_username, _userSocket.get(username));
			
		_users.remove(username);
		_userSocket.remove(username);
		return "[Server]: Successful!";
	}
	
	public boolean changePassword(String username, String new_password) {
		if (_users.containsKey(username) && _users.get(username).equals(new_password)) {
			_users.put(username, new_password);
			//_userSocket.put(username, false);
			return true;
		}
		return false;
	}
//**********************************  END **********************************
	public static void main(String args[]) throws IOException {
		
		final int port_number = 6969;
		System.out.println(InetAddress.getLocalHost());
			
		Server server = new Server(port_number);
			
		server.execute();
	}
}

