package peer2peer;

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
		DataInputStream receive;
		boolean isOpen = true;
		try {
//			if (!isOpen) {
//				System.out.println("User is logged off in receiving 1");
//				return;
//			}
			receive = new DataInputStream(_socket.getInputStream());
			while (isOpen) {
				String message = receive.readUTF();
				System.out.println(message);
//				isOpen = _socket.isClosed();
			}
//			System.out.println("User is logged off in receiving 2");
		}
        
		catch (SocketException e) {
//			System.out.println("In receiving 1");
			System.err.println(e);
		}
		
        catch (IOException e) {
//        	System.out.println("In receiving 2");
        	System.err.println(e);
        }

	}

}

