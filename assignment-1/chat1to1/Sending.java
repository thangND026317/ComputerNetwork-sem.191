package chat1to1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class Sending extends Thread {
	private Socket _socket;
	private String _name;
	
	public Sending(Socket socket, String name) {
		_socket = socket;
		_name = name;
	}

	public void run() {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		try {
			DataOutputStream send = new DataOutputStream(_socket.getOutputStream());
			while (true) {
				String message = _name + ": " + input.readLine();
				System.out.println(message);
				send.writeUTF(message);
				send.flush();
			}
		}
		
		catch (SocketException e) {
			System.err.println(e);
		}
		
        catch (IOException e) {
        	System.err.println(e);
        }
				
	}
   
}