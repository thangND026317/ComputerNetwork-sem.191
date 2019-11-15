package peer2peer;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client extends Thread {

	protected JFrame frame;
	private JTextField TextIn;
	private static JTextArea ChatArea;

//Client
	protected static Socket _socket;
	protected static Socket _socket2;
	protected static ServerSocket _ssocket;
	protected static DataInputStream inStream;
	protected static DataOutputStream outStream;
	protected static DataOutputStream dout;
	protected static DataInputStream din;
	/**
	 * Launch the application.
	 *
	 */
	public void NewScreen() {
		Thread t = new Thread(){
			public void run() {
				try {
					String receive = inStream.readUTF();
					Client window = new Client();
					window.frame.setVisible(true);
					window.ChatArea.append(receive + "\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
			t.start();
		}
	public String Read(DataInputStream in) throws IOException {
		String a = in.readUTF();
		if(a.equals("@SendFile@"))
			return "A file is waiting for receiving";
		else
			return a;
	}
	public void Write(DataOutputStream out, String message) throws IOException {
		out.writeUTF(Login.username + ": " + message);
	}
	
	
//Receive Message
	public void Start(Socket s,DataInputStream in) {
		System.out.println("User: " + Login.username);
		try {
			String message = "";
			while(true) {
				try {
				message = Read(in);
				}catch (Exception e3) {
					System.out.println("\n\nLogged off!");
				}
				ChatArea.append(message + "\n");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	public void Request(int port) throws IOException {
		System.out.println("In Request");
		_ssocket = new ServerSocket(port);
		Socket s = _ssocket.accept();
		ChatArea.append("[Client Accepted] \n");
		dout = new DataOutputStream(s.getOutputStream());
		din = new DataInputStream(s.getInputStream());
		Thread t = new Thread(){
			public void run() {
				try {
					Start(s,din);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}
	
	/**
	 * Create the application.
	 * @throws UnknownHostException 
	 */
	public Client() throws UnknownHostException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		String[] user = {"A","B"};
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 204, 255));
		frame.setBounds(100, 100, 773, 633);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Chat Room " + Login.username);
		frame.setResizable(false);

//Input Message
		TextIn = new JTextField();
		TextIn.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == '\n') {
					String message = TextIn.getText();
					if (TextIn.getText().equals("")){
						return;
					}
					try {
						Write(dout,message); //send message
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ChatArea.append(Login.username + ": " + message + "\n");
					TextIn.setText("");
				}
			}
		});
		TextIn.setFont(new Font("Arial", Font.PLAIN, 14));
		TextIn.setBounds(12, 551, 371, 34);
		frame.getContentPane().add(TextIn);
		TextIn.setColumns(10);
		
//Send Button		
		JButton SendBtn= new JButton("Send");
		SendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent b) {
					String  message = TextIn.getText();
					if (TextIn.getText().equals("")){
						return;
					}
					try {
						Write(dout,message);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ChatArea.append(Login.username + ": " + message + "\n");
					TextIn.setText("");
			}
		});
		SendBtn.setBounds(395, 550, 127, 34);
		frame.getContentPane().add(SendBtn);
		
		

//Send File Button
		JButton btnFile = new JButton("File");
		btnFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Transfer.NewScreen(_socket2,din,dout);
			}
		});
		btnFile.setBounds(534, 560, 97, 25);
		frame.getContentPane().add(btnFile);

//Back Button		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					_socket.close();
					inStream.close();
					outStream.close();	
				}catch (IOException e1) {
					System.out.println("Not Closed");
				}
			}
		});
		btnNewButton.setBounds(646, 545, 97, 39);
		frame.getContentPane().add(btnNewButton);

//Display Message Box
		ChatArea = new JTextArea();
		ChatArea.setEditable(false);
		ChatArea.setFont(new Font("Arial", Font.PLAIN, 12));
		ChatArea.setBounds(12, 13, 510, 525);
		
//Scroll in Chat Area
		JScrollPane scroll = new JScrollPane(ChatArea);
		scroll.setBounds(12, 13, 510, 525);
		frame.getContentPane().add(scroll);

//Friend List
		JList<String> List = new JList<String>();
		List.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JList list = (JList)e.getSource();
		        if (e.getClickCount() == 2) {
		        	
		        	int index = list.locationToIndex(e.getPoint());
		        	Object o = list.getModel().getElementAt(index);
		        	String receive = "";
		        	try {
		        		ChatArea.append("[Wait for connect] \n");
		        		outStream.writeUTF("chatto@" + o + "@" + InetAddress.getLocalHost().getHostAddress());
		        		receive = inStream.readUTF();
		        		System.out.println("Receive: " + receive);
		        	} catch (IOException e1) {
		        		// TODO Auto-generated catch block
		        		System.out.println("Error Here");
		        	}
		        	String[] re = receive.split("@");
		        	try {
		        		Request(Integer.parseInt(re[1]));
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						System.out.println("Error Here 1");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.out.println("Error Here 2");
					}
		        }
			}
		});
		List.setBounds(534, 14, 214, 442);
		frame.getContentPane().add(List);
		
//Refresh Button
		JButton RefreshBtn = new JButton("Refresh");
		RefreshBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					Thread t = new Thread() {
						public void run() {
							//while(true) {
							try {
								outStream.writeUTF("friendlist@ @ ");
								String receive = inStream.readUTF();
								System.out.println(receive);
								String[] re = receive.split("@");
								DefaultListModel<String> DLM = new DefaultListModel<String>();
								for(int i = 0; i < re.length; i++) {
									DLM.addElement(re[i]);
								}
								List.setModel(DLM);
							} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							}
							}
						//}
					};
					t.start();
			}
		});
		RefreshBtn.setBounds(646, 469, 97, 25);
		frame.getContentPane().add(RefreshBtn);	

//Connect to the request client
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread() {
					public void run() {
						String receive = "";
						try {
							receive = inStream.readUTF();
							System.out.println("Receive" + receive);
							String[] re = receive.split("@");
							_socket2 = new Socket(re[0],Integer.parseInt(re[1]));
							System.out.println("Create Socket");
							dout = new DataOutputStream(_socket2.getOutputStream());
							din = new DataInputStream(_socket2.getInputStream());
							Start(_socket2,din);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							System.out.print("Error Connect With Other Client");
						}	
					}
				};
				t.start();
			}
		});
		btnConnect.setBounds(534, 522, 97, 25);
		frame.getContentPane().add(btnConnect);
		
	}
}
