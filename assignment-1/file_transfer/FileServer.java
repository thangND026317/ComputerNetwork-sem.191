package file_transfer;

import java.net.*;
import java.io.*;
public class FileServer {
	
//	public boolean sendFile(String directory, String fileName) {
//		try {
//			File file = new File(directory + "\\" + fileName);
//			if (file.exists()) return false;
//			FileInputStream fis = new FileInputStream(file);
//			BufferedInputStream bis = new BufferedInputStream(fis);
//			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
//			
//			//Read File Contents into contents array 
//			byte[] contents;
//			long fileLength = file.length(); 
//			long current = 0;
//
//			long start = System.nanoTime();
//			while(current != fileLength){
//				int size = 2097152; //size = 2 MB
//				if(fileLength - current >= size)
//					current += size;    
//				else{ 
//					size = (int)(fileLength - current); 
//					current = fileLength;
//				} 
//				contents = new byte[size]; 
//				bis.read(contents, 0, size); 
//				dos.write(contents);
//				System.out.println("Sending file ... " + (current*100)/fileLength+"% complete!");
//			}   
//
//			dos.flush();
//			bis.close();
//			System.out.println("File sent succesfully!");
//			return true;
//		} 
//		catch (FileNotFoundException e) {
//			System.out.println(e);
//		}
//		catch (IOException ex) {
//			System.out.println(ex);
//		}
//	}

	public static void main (String [] args ) throws IOException {
		//Initialize Sockets
		System.out.println(InetAddress.getLocalHost().getHostAddress());
		ServerSocket ssock = new ServerSocket(5000);
		Socket socket = ssock.accept();

		//Specify the file
		String directory = "D:\\Programming\\Eclipse Project\\Computer Network - Sem. 191\\ChatOneToOne\\Server";
		String fileName = "Tobias Fate - FEED TO WIN.mp4";
		File file = new File(directory + "\\" + fileName);

		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);


		//Get socket's output stream
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

		//Read File Contents into contents array 
		byte[] contents;
		long fileLength = file.length(); 
		long current = 0;

		//long start = System.nanoTime();
		int size = 2097152; //size = 2 MB
		while (current != fileLength) { 
			if (fileLength - current >= size)
				current += size;    
			else { 
				size = (int)(fileLength - current);
				current = fileLength;
			} 
			contents = new byte[size]; 
			bis.read(contents, 0, size); 
			dos.write(contents);
			System.out.println("Sending file ... " + (current*100)/fileLength+"% complete!");
		}   

		dos.flush();
		bis.close();
		System.out.println("File sent succesfully!");
		
		
		socket.close();
		ssock.close();
	}
}
