package mao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ScrollPaneConstants;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;

public class highscores extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private static boolean azboolean = false;
	private static boolean scoreboolean = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					highscores frame = new highscores();
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
	 * @throws FileNotFoundException 
	 */
	public highscores() throws InterruptedException, FileNotFoundException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		MediaTracker tracker = new MediaTracker(this);

		//array of accounts
		String[] list = createArray();
		//string of accounts edited to look better
		String users = createString(list);

		JLabel notfoundlbl = new JLabel("user not found");
		notfoundlbl.setForeground(new Color(128, 0, 0));
		notfoundlbl.setFont(new Font("Comic Sans MS", Font.PLAIN, width/80));
		notfoundlbl.setBounds((int)(width*0.45),(int)(height*0.92), width/4, height/10);
		contentPane.add(notfoundlbl);
		notfoundlbl.setVisible(false);

		Image backimg = toolkit.getImage("images/back.png");
		tracker.addImage(backimg, 0);
		tracker.waitForAll();
		backimg = backimg.getScaledInstance((int)(width*0.06), (int)(height*0.06), Image.SCALE_SMOOTH); 
		ImageIcon backicon = new ImageIcon(backimg); 

		Image backwhiteimg = toolkit.getImage("images/backwhite.png");
		tracker.addImage(backwhiteimg, 0);
		tracker.waitForAll();
		backwhiteimg = backwhiteimg.getScaledInstance((int)(width*0.06), (int)(height*0.06), Image.SCALE_SMOOTH); 
		ImageIcon backwhiteicon = new ImageIcon(backwhiteimg);

		JLabel back = new JLabel("");
		back.setIcon(backicon);
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				back.setIcon(backwhiteicon);
				SoundClipTest.main("audio/hover.wav");

			}
			public void mouseExited(MouseEvent arg0) {
				back.setIcon(backicon);
			}
			public void mouseClicked(MouseEvent arg0) {
				dispose();
				SoundClipTest.main("audio/click.wav");
			}
		});
		back.setBounds((int)(width*0.9), (int)(height*0.9), width/9, height/12);
		contentPane.add(back);
		//displays all users and their scores
		JTextArea textarea = new JTextArea();
		textarea.setForeground(Color.WHITE);
		textarea.setFont(new Font("Comic Sans MS", Font.PLAIN, width/30));
		textarea.setText(users);
		textarea.setCaretPosition(0);
		textarea.setEditable(false);
		textarea.setOpaque(false);

		//scroll
		JScrollPane scrollPane = new JScrollPane(textarea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				SoundClipTest.main("audio/hover.wav");
			}
		});
		scrollPane.setBounds(0, (int)(height*0.2), width, (int)(height*0.7));
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		contentPane.add(scrollPane);

		JLabel search = new JLabel("search for user (hit enter):");
		search.setFont(new Font("Comic Sans MS", Font.PLAIN, width/80));
		search.setBounds((int)(width*0.15),(int)(height*0.885), width/4, height/10);
		contentPane.add(search);

		JLabel clear = new JLabel("clear");
		clear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				clear.setForeground(Color.WHITE);
				SoundClipTest.main("audio/hover.wav");

			}
			public void mouseExited(MouseEvent arg0) {
				clear.setForeground(null);
			}
			public void mouseClicked(MouseEvent arg0) {

				SoundClipTest.main("audio/click.wav");
				textField.setText(null);
				textarea.setText(users);
				textarea.setCaretPosition(0);
			}
		});

		clear.setFont(new Font("Comic Sans MS", Font.PLAIN, width/80));
		clear.setBounds((int)(width*0.675),(int)(height*0.885), width/20, height/10);
		contentPane.add(clear);
		//user can enter username to search for
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				SoundClipTest.main("audio/hover.wav");
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					notfoundlbl.setVisible(false);
					boolean notfound = true;
					SoundClipTest.main("audio/click.wav");
					//search algorithm
					for(int i = 0; i < list.length; i++)
					{
						if(textField.getText().equals(list[i]))
						{
							notfound = false;
							try {
								//goes to third line of file to get score
								Scanner in = new Scanner(new File("src/userdata/" + list[i] + ".txt"));
								in.nextLine();
								in.nextLine();
								//checks if name is longer than 10 letters and evenly spaces text out accordingly
								if(list[i].length() >= 10)
								{
									textarea.setText("1." + list[i] + "\t" + in.nextLine());
								}
								else
								{
									textarea.setText("1." + list[i] + "\t\t" + in.nextLine());
								}
							}
							catch(Exception x) 
							{
								x.printStackTrace();
							}
						}
					}
					//lets user know if the username is not found 
					if(notfound == true)
					{
						notfoundlbl.setVisible(true);
					}
				}
			}
		});
		textField.setFont(new Font("Comic Sans MS", Font.PLAIN, width/50));
		textField.setBounds((int)(width*0.35),(int)(height*0.91), (int)(width*0.3), (int)(height*0.05));
		contentPane.add(textField, BorderLayout.SOUTH);

		Image highscoresimg = toolkit.getImage("images/highscores.png");
		tracker.addImage(highscoresimg, 0);
		tracker.waitForAll();
		highscoresimg = highscoresimg.getScaledInstance((int)(width*0.5), (int)(height*0.1), Image.SCALE_SMOOTH);
		ImageIcon highscoresicon = new ImageIcon(highscoresimg);

		JLabel highscores = new JLabel("");
		highscores.setIcon(highscoresicon);
		highscores.setBounds((int)(width*0.3), (int)(height*-0.4), width, height);
		contentPane.add(highscores);

		JLabel score = new JLabel("SCORE");
		score.setForeground(Color.BLACK);
		score.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				score.setForeground(Color.WHITE);
				SoundClipTest.main("audio/hover.wav");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				score.setForeground(Color.BLACK);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				SoundClipTest.main("audio/click.wav");
				textarea.setText(createString(sortScore(list)));
				textarea.setCaretPosition(0);
				if(scoreboolean == true)
				{
					scoreboolean = false;
				}
				else
				{
					scoreboolean = true;
				}
			}
		});
		score.setFont(new Font("Comic Sans MS", Font.PLAIN, width/50));
		score.setBounds((int)(width*0.07), (int)(height*0.2) - height/11, width/10, height/10);
		contentPane.add(score);

		JLabel az = new JLabel("A-Z");
		az.setForeground(Color.BLACK);
		az.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				az.setForeground(Color.WHITE);
				SoundClipTest.main("audio/hover.wav");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				az.setForeground(Color.BLACK);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				SoundClipTest.main("audio/click.wav");
				sortAZ(list);
				textarea.setText(createString(list));
				textarea.setCaretPosition(0);
				if(azboolean == true)
				{
					azboolean = false;
				}
				else
				{
					azboolean = true;
				}
			}
		});
		az.setFont(new Font("Comic Sans MS", Font.PLAIN, width/50));
		az.setBounds((int)(width*0.01), (int)(height*0.2) - height/11, width/10, height/10);
		contentPane.add(az);

		Image blackimg = toolkit.getImage("images/highscoreblack.png");
		tracker.addImage(blackimg, 0);
		tracker.waitForAll();
		blackimg = blackimg.getScaledInstance(width, height, Image.SCALE_FAST);
		ImageIcon blackicon = new ImageIcon(blackimg);

		JLabel black = new JLabel("");
		black.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
		black.setIcon(blackicon);
		black.setBounds(0, (int)(height*0.05), width, height);
		contentPane.add(black);

		JLabel clouds = new JLabel("");
		clouds.setIcon(new ImageIcon("images/clouds.gif"));
		clouds.setBounds(0, 0, width, height);
		contentPane.add(clouds);
	}

	//creates array of account usernames
	static String[] createArray() throws FileNotFoundException
	{
		//scanner gets data from text file that holds list accounts
		Scanner in = new Scanner(new File("src/userdata/accounts.txt"));	
		//declaring array list to add all accounts to
		List<String> list = new ArrayList<String>();
		//adding all accounts to array list
		while(in.hasNext())
		{
			String x = in.next();
			list.add(x);
		}

		String[] x = list.toArray(new String[0]);
		return x;
	}

	//creates string from account usernames
	static String createString(String[] list)
	{
		//declaring string of users
		String users = "";
		//adds number next to name and adds escape sequence
		for(int i = 0; i < list.length; i++)
		{
			try {
				//goes to third line of file to get score
				Scanner in = new Scanner(new File("src/userdata/" + list[i] + ".txt"));
				in.nextLine();
				in.nextLine();
				//checks if name is longer than 10 letters and evenly spaces text out accordingly
				if(list[i].length() >= 10)
				{
					users = users + ((i+1) + ". " + list[i] + "\t" + in.nextLine() + "\n");
				}
				else
				{
					users = users + ((i+1) + ". " + list[i] + "\t\t" + in.nextLine() + "\n");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		//a separate string is being created so the array list can be sorted
		return users;
	}

	//sort alphabetically
	static String[] sortAZ(String[]array)
	{
		for(int i = 0; i < array.length; i++)
		{
			for(int x = 0; x < array.length-1; x++)
			{
				//determines whether to sort from a-z or z-a
				if(azboolean == true)
				{
					//if first letter in string @index[x] is greater string @index[x+1] they are swapped
					if(array[x].charAt(0) < array[x+1].charAt(0))
					{
						String temp = array[x];
						array[x] = array[x+1];
						array[x+1] = temp;
					}
				}
				else
				{
					if(array[x].charAt(0) > array[x+1].charAt(0))
					{
						String temp = array[x];
						array[x] = array[x+1];
						array[x+1] = temp;
					}
				}
			}
		}
		return array;
	}

	static String[] sortScore(String[]array)
	{
		for(int i = 0; i < array.length; i++)
		{
			for(int x = 0; x < array.length-1; x++)
			{
				try 
				{
					//declares a scanner for the first file and goes to the third line
					Scanner a = new Scanner(new File("src/userdata/" + array[x] + ".txt"));
					a.nextLine();
					a.nextLine();
					//declares a scanner for the second file and goes to the third line
					Scanner b = new Scanner(new File("src/userdata/" + array[x+1] + ".txt"));
					b.nextLine();
					b.nextLine();
					//determines whether to sort from highest-lowest or lowest-highest
					if(scoreboolean == true) 
					{
						if(Integer.parseInt(a.nextLine()) > Integer.parseInt(b.nextLine()))
						{
							String temp = array[x];
							array[x] = array[x+1];
							array[x+1] = temp;
						}
					}
					else
					{
						if(Integer.parseInt(a.nextLine()) < Integer.parseInt(b.nextLine()))
						{
							String temp = array[x];
							array[x] = array[x+1];
							array[x+1] = temp;
						}
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return array;
	}
}
