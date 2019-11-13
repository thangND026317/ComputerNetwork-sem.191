package peer2peer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {
	private String _host;
	private int _port_number;
	private Socket _socket;
	//private 
 
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
			
//			Receiving receive = new Receiving(_socket);
//			receive.start();
			
		}
		
		catch (UnknownHostException e) {
			System.err.println("I can't find " + _host);
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
	
	public int signIn(String username, String password) {
		try {
			DataInputStream inStream = new DataInputStream(_socket.getInputStream());
			DataOutputStream outStream = new DataOutputStream(_socket.getOutputStream());
			
			
			outStream.writeUTF("signIn@" + username + "@" + password);		
			
			return inStream.readInt();
		} 
		
		catch (IOException e) {
			System.out.println(e);
			return 0;
		}
			
	}
	
	public int signUp(String username, String password) {
		try {
			DataInputStream inStream = new DataInputStream(_socket.getInputStream());
			DataOutputStream outStream = new DataOutputStream(_socket.getOutputStream());
			
			
			outStream.writeUTF("signUp@" + username + "@" + password);		
			
			return inStream.readInt();
		} 
		
		catch (IOException e) {
			System.out.println(e);
			return 0;
		}
			
	}
	
	public void logout() {
		try {
			System.out.println("\n\nLogged off!");
			_socket.close();
		}

		catch (IOException e) {
			System.out.println(e);
			return;
		}
	}
	
	//TODO: send file
	
	
	//TODO: receive file

//************************** Demo *********************************
	public static void main(String args[]) throws IOException {
		
		final int port_number = 5000;
		Client client = new Client(InetAddress.getLocalHost().getHostAddress(), port_number);
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		client.execute();
		
		client.sendMessage(input.readLine());
		
		System.out.println(client.receiveMessage());
		
		//client.logout();

	}
}

