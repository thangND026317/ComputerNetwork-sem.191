package LoginTCP;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

public class Server {
	private int _port_number;
	private ServerSocket _server_socket;
	private Socket socket;
	
	public Server(int port_number) {
		_port_number = port_number;
	}
	
	public void turnOn() {
		try {
			_server_socket = new ServerSocket(_port_number);
			System.out.println("Server is On!\n");
			socket = _server_socket.accept();
		} 
		catch (IOException e) {
			System.err.println(e);
		}
		
	}
	
//	public void authorize() {
//		try {
//			DataInputStream inStream = new DataInputStream(socket.getInputStream());
//			DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
//			
//			
//			String message = inStream.readUTF();
//			StringTokenizer st = new StringTokenizer(message, "@");
//			
//			String username = st.nextToken();
//			String password = st.nextToken();
//			
//			Connection c = Database.connect();
//			
//			String query = "SELECT dbo.FN_autho(?, ?) AS result";
//			
//			PreparedStatement ps = c.prepareStatement(query);
//			ps.setString(1, username);
//			ps.setString(2, password);
//			
//			ResultSet r = ps.executeQuery();
//			r.next();
//			
//			int result = r.getInt("result");
//			
//			outStream.writeInt(result);
//			
//			Database.disconect(c);
//			
//			inStream.close();
//			outStream.close();
//			
//		} 
//		
//		catch (SQLException e) {
//			System.out.println(e);
//		}
//		
//		catch (IOException e) {
//			System.err.println(e);
//		}
//		
//	}
	
	public void authorize() {
		try {
			DataInputStream inStream = new DataInputStream(socket.getInputStream());
			DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
			
			
			String message = inStream.readUTF();
			StringTokenizer st = new StringTokenizer(message, "@");
			
			String type = st.nextToken();
			String username = st.nextToken();
			String password = st.nextToken();

			int result = 0;
			
			if (type.equals("1")) result = checkSignUp(username, password);
			result = checkSignIn(username, password);
			
			outStream.writeInt(result);
			
			inStream.close();
			outStream.close();
			
		} 

		catch (IOException e) {
			System.err.println(e);
		}
		
	}
	
	public int checkSignIn(String username, String password) {
		return Users.signIn(username, password);
	}
	
	public int checkSignUp(String username, String password) {
		return Users.signUp(username, password);
	}
	
	public void shutdown() {
		try {
			socket.close();
			_server_socket.close();
			System.out.println("Server is shutdown!\n");
		} 
		catch (IOException e) {
			System.err.println(e);
		}
	}

//********************************** Demo **********************************
	public static void main(String args[]) throws IOException {
	
		final int port_number = 5000;
		Server server = new Server(port_number);
		server.turnOn();
		
		boolean isLoggedIn = false;

		
		System.out.println("Out of the loop!");
		
	}
}
