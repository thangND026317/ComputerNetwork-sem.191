package file_transfer;

import java.net.*;
import java.io.*;
public class FileClient{
	
//	public void receiveFile(String directory, String fileName) {
//	try {
//		File file = new File(directory + "\\" + fileName);
//		FileOutputStream fos = new FileOutputStream(directory + "\\" + fileName);
//		BufferedOutputStream bos = new BufferedOutputStream(fos);
//		DataInputStream dis = new DataInputStream(socket.getInputStream());
//		
//		byte[] contents = new byte[2097152]; //size for each packet: 2MB
//		int bytesRead = 0; 
//
//		while((bytesRead = dis.read(contents)) != -1)
//			bos.write(contents, 0, bytesRead); 
//
//		bos.flush();
//		bos.close();
//	}
//	catch (IOException e) {
//		System.out.println(e);
//	}
//}
	
	public static void main (String [] args ) throws IOException {
		//Initialize socket
		Socket socket = new Socket("169.254.170.238", 5000);
		
		String directory = "D:\\Programming\\Eclipse Project\\Computer Network - Sem. 191\\ChatOneToOne\\Client";
		String fileName = "Tobias Fate - FEED TO WIN.mp4";
		FileOutputStream fos = new FileOutputStream(directory + "\\" + fileName);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		DataInputStream dis = new DataInputStream(socket.getInputStream());

		//None of bytes read in one read() call
		int bytesRead = 0;
		byte[] contents = new byte[2097152]; //size for each packet: 2MB
		
		System.out.println("Saving file ...");
		while((bytesRead = dis.read(contents)) != -1)
			bos.write(contents, 0, bytesRead); 

		bos.flush();
		bos.close();

		System.out.println("File saved successfully!");
		
		
		socket.close();
	}
}
