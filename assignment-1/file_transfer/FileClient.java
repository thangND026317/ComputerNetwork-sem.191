package file_transfer;

import java.net.*;
import java.io.*;
public class FileClient{
  public static void main (String [] args ) throws IOException {
	  //Initialize socket
      Socket socket = new Socket("169.254.170.238", 5000);
      byte[] contents = new byte[2097152];
      
      //Initialize the FileOutputStream to the output file's full path.
      FileOutputStream fos = new FileOutputStream("Client\\Ryo (supercell) feat. Hatsune Miku - Odds and Ends _ Drum.mp4");
      BufferedOutputStream bos = new BufferedOutputStream(fos);
      InputStream is = socket.getInputStream();
      
      //No of bytes read in one read() call
      int bytesRead = 0; 
      
      while((bytesRead=is.read(contents))!=-1)
          bos.write(contents, 0, bytesRead); 
      
      bos.flush(); 
      socket.close();
      
      System.out.println("File saved successfully!");
  }
}
