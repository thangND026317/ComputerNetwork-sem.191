//package peer2peer;
//
//import java.awt.Color;
//import java.awt.EventQueue;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.net.InetAddress;
//import java.net.Socket;
//import java.net.UnknownHostException;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JTextField;
//
//public class SignUp {
//
//	private JFrame frame;
//	private JTextField textField;
//	private JTextField textField_1;
//	private JLabel PWlabel;
//	protected static String username;
//
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					SignUp window = new SignUp();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//	
//	public static void NewScreen() {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					SignUp window = new SignUp();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//	/**
//	 * Create the application.
//	 */
//	public SignUp() {
//		initialize();
//	}
//
//	public void Signup(String name, String password) throws UnknownHostException, IOException {
//		Client._socket = new Socket(InetAddress.getLocalHost().getHostAddress(), 5000);
//		Client.outStream = new DataOutputStream(Client._socket.getOutputStream());
//		Client.outStream.writeUTF("signup@" + name + "@" + password);
//	}
//	
//	/**
//	 * Initialize the contents of the frame.
//	 */
//	private void initialize() {
//		frame = new JFrame();
//		frame.setTitle("Sign Up");
//		frame.setBackground(new Color(102, 153, 51));
//		frame.getContentPane().setBackground(new Color(255, 204, 255));
//		frame.setBounds(100, 100, 512, 349);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.getContentPane().setLayout(null);
//		
//		textField = new JTextField();
//		textField.setBounds(110, 65, 263, 51);
//		frame.getContentPane().add(textField);
//		textField.setColumns(10);
//	
//		
//		textField_1 = new JTextField();
//		textField_1.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyTyped(KeyEvent e) {
//				if (e.getKeyChar() == '\n') {
//					String a = textField.getText();
//					username = a;
//					String b = textField_1.getText();
//					if(b.equals("175")) {	
//						try {
//							Client client = new Client();
//							client.NewScreen();
////							Client window = new Client();
////							window.frame.setVisible(true);
//							frame.dispose();
//						} catch (Exception e1) {
//							e1.printStackTrace();
//						}
//					}
//					else {
//						JOptionPane.showMessageDialog(frame, "Invalid Password");
//					}
//				}
//			}
//		});
//		textField_1.setBounds(110, 170, 263, 51);
//		frame.getContentPane().add(textField_1);
//		textField_1.setColumns(10);
//	
//		
//		JLabel UNlabel = new JLabel("User Name");
//		UNlabel.setBounds(120, 36, 102, 16);
//		frame.getContentPane().add(UNlabel);
//		
//		
//		PWlabel = new JLabel("Password");
//		PWlabel.setBounds(120, 141, 102, 16);
//		frame.getContentPane().add(PWlabel);
//		
//		
//		JButton btnNewButton = new JButton("Login");
//		btnNewButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				String a = textField.getText();
//				username = a;
//				String b = textField_1.getText();
//					
//				try {
//					Login l = new Login();
//					Signup(a,b);
//					l.Screen();
//					frame.dispose();
//				} catch (UnknownHostException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}
//		});
//		btnNewButton.setBounds(181, 234, 113, 39);
//		frame.getContentPane().add(btnNewButton);
//	}
//
//}
//
