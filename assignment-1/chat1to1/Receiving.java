package chat1to1;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class Receiving extends Thread {
	private Socket _socket;
	
	public Receiving(Socket socket) {
		_socket = socket;
	}
	
	public void run() {
		try {
			DataInputStream receive = new DataInputStream(_socket.getInputStream());
			while (true) {
				String message = receive.readUTF();
				System.out.println(message);
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

