package mao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.awt.Color;

public class login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel msg;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login frame = new login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public login() throws InterruptedException, IOException {
		setTitle("Zengmo simulator 2");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/head.png"));
		setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((int)(width*0.3), (int)(height*0.3), 500, 350);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		MediaTracker tracker = new MediaTracker(this);
		//displays msg
		msg = new JLabel("");
		msg.setForeground(new Color(220, 20, 60));
		msg.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		msg.setBounds(157, 281, 293, 30);
		contentPane.add(msg);

		Image maoimg = toolkit.getImage("images/sprite1.png");
		tracker.addImage(maoimg, 0);
		tracker.waitForAll();
		maoimg = maoimg.getScaledInstance(250,250, Image.SCALE_SMOOTH);
		ImageIcon maoicon = new ImageIcon(maoimg);

		JLabel lblEnterUsernameAnd = new JLabel("Enter username and password");
		lblEnterUsernameAnd.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		lblEnterUsernameAnd.setBounds(192, 85, 226, 32);
		contentPane.add(lblEnterUsernameAnd);

		JLabel mao = new JLabel("");
		mao.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
		mao.setIcon(maoicon);
		mao.setBounds(-40,50,241,300);
		contentPane.add(mao);

		Image titleimg = toolkit.getImage("images/title.png");
		tracker.addImage(titleimg, 0);
		tracker.waitForAll();
		titleimg = titleimg.getScaledInstance((int)(getWidth()*0.8),getHeight()/8, Image.SCALE_SMOOTH);
		ImageIcon titleicon = new ImageIcon(titleimg);

		JLabel title = new JLabel("");
		title.setIcon(titleicon);
		title.setBounds(50, 11, 480, 67);
		contentPane.add(title);
		//username textfield
		textField = new JTextField();
		textField.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
		textField.setBounds(150, 128, 300, 40);
		contentPane.add(textField);
		textField.setColumns(10);
		//password textfield
		passwordField = new JPasswordField();
		passwordField.setBounds(150, 179, 300, 40);
		contentPane.add(passwordField);

		Image createaccountimg = toolkit.getImage("images/createaccount.png");
		tracker.addImage(createaccountimg, 0);
		tracker.waitForAll();
		createaccountimg = createaccountimg.getScaledInstance(getWidth()/5, getHeight()/10, Image.SCALE_SMOOTH);
		ImageIcon createaccounticon = new ImageIcon(createaccountimg);

		Image createaccountwhiteimg = toolkit.getImage("images/createaccountwhite.png");
		tracker.addImage(createaccountwhiteimg, 0);
		tracker.waitForAll();
		createaccountwhiteimg = createaccountwhiteimg.getScaledInstance(getWidth()/5, getHeight()/10, Image.SCALE_SMOOTH);
		ImageIcon createaccountwhiteicon = new ImageIcon(createaccountwhiteimg);
		
		JLabel createaccount = new JLabel("");
		createaccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				SoundClipTest.main("audio/hover.wav");
				createaccount.setIcon(createaccountwhiteicon);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				createaccount.setIcon(createaccounticon);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				SoundClipTest.main("audio/click.wav");
				File f = new File("src/userdata/" + textField.getText() + ".txt");
				//checks if user is meeting username and password requirements
				if(textField.getText().equals("") || String.valueOf(passwordField.getPassword()).equals(""))
				{
					msg.setText("enter a username and password");
				}
				else if(textField.getText().contains(" "))
				{
					msg.setText("username cannot contain spaces");
				}
				else if(f.exists())
				{
					msg.setText("account already exists");
				}
				else
				{
					msg.setText("succesfully created account");
					String account = textField.getText();
					createAccount(account);
				}
			}
		});

		createaccount.setBounds(320, 230, 300, 40);
		createaccount.setIcon(createaccounticon);
		contentPane.add(createaccount);

		Image loginimg = toolkit.getImage("images/login.png");
		tracker.addImage(loginimg, 0);
		tracker.waitForAll();
		loginimg = loginimg.getScaledInstance(getWidth()/5, getHeight()/10, Image.SCALE_SMOOTH);
		ImageIcon loginicon = new ImageIcon(loginimg);

		Image loginwhiteimg = toolkit.getImage("images/loginwhite.png");
		tracker.addImage(loginwhiteimg, 0);
		tracker.waitForAll();
		loginwhiteimg = loginwhiteimg.getScaledInstance(getWidth()/5, getHeight()/10, Image.SCALE_SMOOTH);
		ImageIcon loginwhiteicon = new ImageIcon(loginwhiteimg);

		JLabel login = new JLabel("");
		login.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				SoundClipTest.main("audio/hover.wav");
				login.setIcon(loginwhiteicon);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				login.setIcon(loginicon);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				SoundClipTest.main("audio/click.wav");
				login();
			}
		});
		login.setBounds(180, 230, 300, 40);
		login.setIcon(loginicon);
		contentPane.add(login);

		JLabel clouds = new JLabel("");
		clouds.setIcon(new ImageIcon("images/clouds.gif"));
		clouds.setBounds(0, 0, width, height);
		contentPane.add(clouds);

	}
	
	public void login() {
		if (textField.getText().equals("") || passwordField.getPassword().equals(""))
		{
			msg.setText("enter a username and password");
		}
		else
		{
			try
			{	//reads file where account data is stored
				System.out.println(String.valueOf(passwordField.getPassword()));
				Scanner in = new Scanner (new File ("src/userdata/" + textField.getText() + ".txt"));
				if(textField.getText().equals(in.next()) && String.valueOf(passwordField.getPassword()).equals(in.next()))
				{
					List<String> lines = Arrays.asList(textField.getText());
					Path file = Paths.get("src/userdata/currentaccount.txt");
					try {
						Files.write(file, lines, Charset.forName("UTF-8"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					dispose();
					//if username and pass are = to the one in the file it starts the game
					splashscreen.main(null);
				}
				else
				{
					in.reset();		
					msg.setText("Invalid login credentials");
				}
				in.close();
			}
			catch(Exception e)
			{
				msg.setText("Account does not exist");
			}
		}
	}
	//creates account with username and password that the user entered. Creates a new txt file storing user data
	public void createAccount(String account) {
		String username = textField.getText();
		String pass = String.valueOf(passwordField.getPassword());
		//username, pass, score, volume
		List<String> lines = Arrays.asList(username, pass, "0", "100");
		Path file = Paths.get("src/userdata/" + account + ".txt");
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			addAccount();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	//adds account to txt file of list of accounts
	public void addAccount() throws IOException {

		Scanner in = new Scanner (new File ("src/userdata/accounts.txt"));
		String add = null; 
		String line = in.nextLine();
		if(line.equals(" "))
		{
			add = textField.getText();
		}
		else
		{
			add = line + textField.getText();
		}
		List<String> lines = Arrays.asList(add + " ");
		Path file = Paths.get("src/userdata/accounts.txt");
		Files.write(file, lines, Charset.forName("UTF-8"));
	}
}
