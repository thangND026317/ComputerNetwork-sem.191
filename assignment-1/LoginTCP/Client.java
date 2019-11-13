package LoginTCP;

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
			_socket = new Socket(_host, _port_number);		
		}
		
		catch (UnknownHostException e) {
			System.err.println("I can't find " + _host);
		}
		
		catch (IOException e) {
			System.err.println(e);
		}
	}
	
	public int signIn(String username, String password) {
		try {
			DataInputStream inStream = new DataInputStream(_socket.getInputStream());
			DataOutputStream outStream = new DataOutputStream(_socket.getOutputStream());
			
			
			outStream.writeUTF("0@" + username + "@" + password);		
			
			return inStream.readInt();
		} 
		
		catch (IOException e) {
			System.err.println(e);
			return 0;
		}
			
	}
	
	public int signUp(String username, String password) {
		try {
			DataInputStream inStream = new DataInputStream(_socket.getInputStream());
			DataOutputStream outStream = new DataOutputStream(_socket.getOutputStream());
			
			
			outStream.writeUTF(username + "@" + password);		
			
			return inStream.readInt();
		} 
		
		catch (IOException e) {
			System.err.println(e);
			return 0;
		}
			
	}
	

//********************************** Demo **********************************
	public static void main(String args[]) throws IOException {
	
		final int port_number = 5000;
		Client client = new Client(InetAddress.getLocalHost(), port_number);
		client.execute();
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		boolean isLoggedIn = false;
		while (!isLoggedIn) {
			System.out.print("Username: ");
			String username = input.readLine();
		
			System.out.print("Password: ");
			String password = input.readLine();
			
			if (client.signIn(username, password) > 0) isLoggedIn = true;
			if (isLoggedIn) System.out.println("Successfully logged in!");
		}
		
		System.out.println("Out of the loop!");
		
	}
}

