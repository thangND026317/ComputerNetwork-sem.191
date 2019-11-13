package peer2peer;

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
		DataOutputStream send;
		boolean isOpen = true;
		try {
//			if (!isOpen) {
//				System.out.println("User is logged off in sending 1");
//				return;
//			}
			send = new DataOutputStream(_socket.getOutputStream());
			while (isOpen) {
				String message = _name + ": " + input.readLine();
				System.out.println(message);
				send.writeUTF(message);
				send.flush();
//				isOpen = _socket.isClosed();
			}
//			System.out.println("User is logged off in sending 2");
		}
		
		catch (SocketException e) {
//			System.out.println("In sending 1");
			System.err.println(e);
		}
		
        catch (IOException e) {
//        	System.out.println("In sending 2");
        	System.err.println(e);
        }
				
	}
   
}