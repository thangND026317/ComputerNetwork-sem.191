package peer2peer;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Login {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel PWlabel;
	protected static String username;
	protected static int i = 0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public static void Screen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	

	public void Signin(String name, String password) throws UnknownHostException, IOException {
		if (i == 0) {
			Client._socket = new Socket(InetAddress.getLocalHost().getHostAddress(), 6969);
			Client.outStream = new DataOutputStream(Client._socket.getOutputStream());
			Client.inStream = new DataInputStream(Client._socket.getInputStream());
		}
		System.out.println("In Login");
		Client.outStream.writeUTF("signin@" + name + "@" + password);
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Sign In");
		frame.setBackground(new Color(102, 153, 51));
		frame.getContentPane().setBackground(new Color(255, 204, 255));
		frame.setBounds(100, 100, 512, 349);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(110, 65, 263, 51);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
	
		
		textField_1 = new JTextField();
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == '\n') {
					String a = textField.getText();
					username = a;
					String b = textField_1.getText();
					try {
						Client client = new Client();
						Signin(a,b);
						client.NewScreen();
						frame.dispose();
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		textField_1.setBounds(110, 170, 263, 51);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
	
		
		JLabel UNlabel = new JLabel("User Name");
		UNlabel.setBounds(120, 36, 102, 16);
		frame.getContentPane().add(UNlabel);
		
		
		
		PWlabel = new JLabel("Password");
		PWlabel.setBounds(120, 141, 102, 16);
		frame.getContentPane().add(PWlabel);
		
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String a = textField.getText();
				username = a;
				String b = textField_1.getText();
				try {
					Client client = new Client();
					Signin(a,b);
					client.NewScreen();
					frame.dispose();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(181, 234, 113, 39);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnSignIn = new JButton("Sign Up");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignUp.NewScreen();
				frame.dispose();
			}
		});
		btnSignIn.setBounds(12, 264, 97, 25);
		frame.getContentPane().add(btnSignIn);
	}
}
