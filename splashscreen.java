package mao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class splashscreen extends JFrame {

	private JPanel contentPane;
	JLabel background = new JLabel("");
	JLabel mao = new JLabel("");

	JLabel epicgames = new JLabel("");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					splashscreen frame = new splashscreen();
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
	 * @throws UnsupportedAudioFileException 
	 * @throws LineUnavailableException 
	 */
	public splashscreen() throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
		
		File soundFile = new File("audio/splashscreen.wav");
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);              
		Clip clip = AudioSystem.getClip();
		clip.open(audioIn);
		
		ActionListener delay = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clip.start();
			}	
		};
		Timer songtimer = new Timer(500, delay);
		songtimer.setRepeats(false);
		songtimer.start();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, width, height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		setBackground(Color.BLACK);

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		MediaTracker tracker = new MediaTracker(this);

		mao.setBounds((int)(width*0.1), 10, width, height);

		Image maoimg = toolkit.getImage("images/run.gif");
		tracker.addImage(maoimg, 0);
		tracker.waitForAll();
		maoimg = maoimg.getScaledInstance(width/2, (int)(height*0.75), Image.SCALE_SMOOTH);
		ImageIcon maoicon = new ImageIcon(maoimg);

		mao.setIcon(new ImageIcon("images/runright.gif"));		
		contentPane.add(mao);
		mao.setVisible(false);

		Image epicgamesimg = toolkit.getImage("images/epic-games.jpg");
		tracker.addImage(epicgamesimg, 0);
		tracker.waitForAll();
		epicgamesimg = epicgamesimg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon epicgamesicon = new ImageIcon(epicgamesimg);

		epicgames.setIcon(epicgamesicon);
		epicgames.setBounds(0, 0, 500, height);
		contentPane.add(epicgames);
		epicgames.setVisible(false);

		Image blackimg = toolkit.getImage("images/black.png");
		tracker.addImage(blackimg, 0);
		tracker.waitForAll();
		blackimg = blackimg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon blackicon = new ImageIcon(blackimg);



		Image backgroundimg = toolkit.getImage("images/activision.png");
		tracker.addImage(backgroundimg, 0);
		tracker.waitForAll();
		backgroundimg = backgroundimg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon backgroundicon = new ImageIcon(backgroundimg);


		background.setIcon(backgroundicon);
		background.setBounds(0, 0, width, height);
		contentPane.add(background);
		//moves mao
		ActionListener move = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				mao.setBounds(mao.getX() + 20, mao.getY(), mao.getWidth(), mao.getHeight());
				if(mao.getX() >= epicgames.getX()*3){

					epicgames.setBounds(0,0,mao.getX()+50, epicgames.getHeight());
				}
			}
		};

		Timer maotimer = new Timer(30, move);
		if(mao.getX()> 0) {
			maotimer.setRepeats(true);}
		else {
			maotimer.stop();
		}

		ActionListener wait = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				maotimer.start();

			}
		};

		Timer timer2 = new Timer(2000, wait);
		timer2.setRepeats(false);
		//changes background after 3s
		ActionListener changebackground = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				background.setIcon(blackicon);
				mao.setVisible(true);
				epicgames.setVisible(true);
				timer2.start();
			}
		};
		Timer timer = new Timer(3000 ,changebackground);
		timer.setRepeats(false);
		timer.start();

		Thread.sleep(0);
		//goes to next screen when music is done
		ActionListener next = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dispose();
				titlescreen.main(null);
				
			}
		};
		System.out.println(clip.getMicrosecondLength());
		Timer nextscreen = new Timer((int) (clip.getMicrosecondLength()/1000)+400,next);
		nextscreen.setRepeats(false);
		nextscreen.start();
	}



}
