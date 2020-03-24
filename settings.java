package mao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;

import javax.swing.event.*;


import javax.sound.sampled.FloatControl;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSlider;
import java.awt.Font;

public class settings extends JFrame {

	private JPanel contentPane;
	public static JSlider slider;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					settings frame = new settings();
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
	public settings() throws InterruptedException, FileNotFoundException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		MediaTracker tracker = new MediaTracker(this);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();
		
		Scanner x = new Scanner(new File("src/userdata/currentaccount.txt"));
		Scanner in = new Scanner(new File("src/userdata/" + x.nextLine() + ".txt"));
		in.nextLine();
		in.nextLine();
		in.nextLine();
		//gets volume from fourth line of txt file
		double startval = Double.parseDouble(in.nextLine());
		//slider adjusts volume
		slider = new JSlider(JSlider.HORIZONTAL, 0 , 100, (int)startval);
		slider.setFont(new Font("Anime Ace 2.0 BB", Font.BOLD, 30));
		slider.setBounds((int)(width*0.1), (int)(height*0.3), (int)(width/3), (int)(height*0.2));
		slider.setOpaque(false);
		slider.setMajorTickSpacing(2);
		contentPane.add(slider);
		
		label = new JLabel("VOLUME: " + (Integer.toString((int)startval)));
		label.setFont(new Font("Anime Ace 2.0 BB", Font.BOLD, 30));
		label.setBounds((int)(width*0.2), (int)(slider.getHeight()*1.5), (int)(width/5), (int)(height/10));
		contentPane.add(label);

		event e = new event();
		slider.addChangeListener(e);

		Image settingsimg = toolkit.getImage("images/settings.png");
		tracker.addImage(settingsimg, 0);
		tracker.waitForAll();
		settingsimg = settingsimg.getScaledInstance((int)(width/3.5), (int)(height/10), Image.SCALE_SMOOTH);
		ImageIcon settingsicon = new ImageIcon(settingsimg);

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
					titlescreen.main(null);
			}
		});
		back.setBounds((int)(width*0.9), (int)(height*0.9), width/9, height/12);
		contentPane.add(back);
		
		JLabel settings = new JLabel("");
		settings.setIcon(settingsicon);
		settings.setBounds((int)(width/2.5), (int)(height*-0.4), width, height);
		contentPane.add(settings);

		Image cloudsimg = toolkit.getImage("images/clouds.gif");
		tracker.addImage(cloudsimg, 0);
		tracker.waitForAll();
		cloudsimg = cloudsimg.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		ImageIcon cloudsicon = new ImageIcon(cloudsimg);

		JLabel clouds = new JLabel("");
		clouds.setIcon(cloudsicon);
		clouds.setBounds(0, 0, width, height);
		contentPane.add(clouds);	
	}

	class event implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			SoundClipTest.main("audio/hover.wav");
			double value = slider.getValue();
			if(value > 99) {
				label.setText("Volume: 100");
			}
			else {
			label.setText("Volume: " + (int)value);
			}
			try
			{
			Scanner a = new Scanner(new File("src/userdata/currentaccount.txt"));
			String account = a.nextLine();
			Scanner in = new Scanner(new File("src/userdata/" + account + ".txt"));
			String username = in.nextLine();
			String pass = in.nextLine();
			String score = in.nextLine();
			String val = Double.toString(value);
			//writes volume to userfile
				List<String> lines = Arrays.asList(username, pass, score, val);
				Path file = Paths.get("src/userdata/" + account + ".txt");
				try {
					Files.write(file, lines, Charset.forName("UTF-8"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			} 
			catch (FileNotFoundException x) 
			{
				System.out.println("file not found");
			}

		}
	}
}

