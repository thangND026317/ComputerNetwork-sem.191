package file_transfer;

import java.net.*;
import java.io.*;
public class FileServer {
  public static void main (String [] args ) throws IOException {
	//Initialize Sockets
	  System.out.println(InetAddress.getLocalHost().getHostAddress());
      ServerSocket ssock = new ServerSocket(5000);
      Socket socket = ssock.accept();
      
      //The InetAddress specification
      
      
      //Specify the file
      File file = new File("Server\\Ryo (supercell) feat. Hatsune Miku - Odds and Ends _ Drum.mp4");
      FileInputStream fis = new FileInputStream(file);
      BufferedInputStream bis = new BufferedInputStream(fis); 
        
      //Get socket's output stream
      OutputStream os = socket.getOutputStream();
              
      //Read File Contents into contents array 
      byte[] contents;
      long fileLength = file.length(); 
      long current = 0;
       
      long start = System.nanoTime();
      while(current != fileLength){ 
          int size = 2097152; //size = 2 MB
          if(fileLength - current >= size)
              current += size;    
          else{ 
              size = (int)(fileLength - current); 
              current = fileLength;
          } 
          contents = new byte[size]; 
          bis.read(contents, 0, size); 
          os.write(contents);
          System.out.println("Sending file ... " + (current*100)/fileLength+"% complete!");
      }   
      
      os.flush(); 
      //File transfer done. Close the socket connection!
      socket.close();
      ssock.close();
      System.out.println("File sent succesfully!");
    }
}
