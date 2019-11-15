package peer2peer;

import java.awt.EventQueue;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Transfer {

	private JFrame frame;
	private JTextField directory;
	private JTextField FileName;
	protected static Socket _socket;
	protected static DataOutputStream dout;
	protected static DataInputStream din;

	/**
	 * Launch the application.
	 */
	public static void NewScreen(Socket s,DataInputStream in,DataOutputStream out) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					dout = out;
					din = in;
					_socket = s;
					Transfer window = new Transfer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void ReceiveFile(String a,Socket s) {
		Thread t = new Thread() {
			public void run() {
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(a);
				} catch (FileNotFoundException e) {
					System.out.println("Error 1");
				}
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				DataInputStream dis = null;
				
				try {
					dis = new DataInputStream(s.getInputStream());
				} catch (IOException e) {
					System.out.println("Error 2");
				}

				int bytesRead = 0;
				byte[] contents = new byte[2097152];
				
				System.out.println("Saving file ...");
				try {
					while((bytesRead = dis.read(contents)) != -1)
						bos.write(contents, 0, bytesRead);
				} catch (IOException e) {
					System.out.println("Error 3");
				} 

//				try {
//					bos.flush();
//					bos.close();
//				} catch (IOException e) {
//					System.out.println("Error 4");
//				}
//				try {
//					dis.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					System.out.println("Error 5");
//				}
			}
		};
		t.start();
	}
	public void SendFile(String a,Socket s) {
	
		Thread t = new Thread() {
			public void run() {
				File file = new File(a);
				FileInputStream fis = null;
				
				try {
					fis = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("Error1");
				}
				BufferedInputStream bis = new BufferedInputStream(fis);
				//Get socket's output stream
				DataOutputStream dos = null;
				try {
					dos = new DataOutputStream(s.getOutputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Error2");
				}

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
					try {
						bis.read(contents, 0, size);
						dos.write(contents);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					System.out.println("Sending file ... " + (current*100)/fileLength+"% complete!");
				}   
				
//				try {
//					bis.close();
//					dos.flush();
//					dos.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				System.out.println("File sent succesfully!");
			}
		};

				t.start();
	}

	/**
	 * Create the application.
	 */
	public Transfer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle(Login.username);
		frame.setBounds(100, 100, 450, 322);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		directory = new JTextField();
		directory.setBounds(12, 62, 408, 39);
		frame.getContentPane().add(directory);
		directory.setColumns(10);
		
		JButton sendBtn = new JButton("Send");
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dout.writeUTF("@SendFile@");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String a = directory.getText() + "\\" + FileName.getText();
				System.out.println(a);
				SendFile(a,_socket);
			}
		});
		sendBtn.setBounds(55, 221, 124, 25);
		frame.getContentPane().add(sendBtn);
		
		FileName = new JTextField();
		FileName.setColumns(10);
		FileName.setBounds(12, 150, 408, 39);
		frame.getContentPane().add(FileName);
		
		JLabel lblFileName = new JLabel("File Name");
		lblFileName.setBounds(183, 121, 85, 16);
		frame.getContentPane().add(lblFileName);
		
		JLabel lblDirectory = new JLabel("Directory");
		lblDirectory.setBounds(183, 33, 85, 16);
		frame.getContentPane().add(lblDirectory);
		
		JButton receiveBtn = new JButton("Receive");
		receiveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String a = directory.getText() + "\\" + FileName.getText();
//				System.out.println("F:\\University\\App\\GoldenDict\\E3-15-HANDOVER_20180503181713.pdf");
				ReceiveFile(a,_socket);
			}
		});
		
		receiveBtn.setBounds(238, 221, 124, 25);
		frame.getContentPane().add(receiveBtn);
	}
}
