package mao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import javax.swing.JOptionPane;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

public class home extends JFrame {

	private JPanel contentPane;
	private JLabel background = new JLabel("");
	private int scorenum;

	/**
	 * Launch the application.
	 */
	public static void main(boolean returned) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					home frame = new home(returned);
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
	public home(boolean returned) throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1500, 800);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		//gets volume and score
		Scanner x = new Scanner(new File("src/userdata/currentaccount.txt"));
		Scanner in = new Scanner(new File("src/userdata/" + x.nextLine() + ".txt"));
		in.nextLine();
		in.nextLine();
		scorenum = Integer.parseInt(in.nextLine());
		float volume = Float.parseFloat(in.nextLine())/100;
		in.reset();
		//walk sound file
		File soundFile = new File("audio/walk.wav");
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);              
		Clip clip = AudioSystem.getClip();
		clip.open(audioIn);
		//controls volume
		FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * volume) + gainControl.getMinimum();
		gainControl.setValue(gain);

		if(volume == 0) {
			gainControl.setValue(gainControl.getMinimum());
		}

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		MediaTracker tracker = new MediaTracker(this);

		
		Image backgroundimg = toolkit.getImage("images/home.png");
		tracker.addImage(backgroundimg, 0);
		tracker.waitForAll();
		backgroundimg = backgroundimg.getScaledInstance(width,height, Image.SCALE_SMOOTH);
		ImageIcon backgroundicon = new ImageIcon(backgroundimg);
		
		Image background2img = toolkit.getImage("images/level2.png");
		tracker.addImage(background2img, 0);
		tracker.waitForAll();
		background2img = background2img.getScaledInstance(width,height, Image.SCALE_SMOOTH);
		ImageIcon background2icon = new ImageIcon(background2img);
		
		Image headimg = toolkit.getImage("images/head.png");
		tracker.addImage(headimg, 0);
		tracker.waitForAll();
		headimg = headimg.getScaledInstance((int)(width/22), (int)(height/10), Image.SCALE_SMOOTH);
		ImageIcon headicon = new ImageIcon(headimg);

		JLabel head = new JLabel("");
		head.setIcon(headicon);
		head.setBounds((int)(width*0.01), (int)(height*-0.43), width, height);
		contentPane.add(head);

		Image healthbarimg = toolkit.getImage("images/healthbar.png");
		tracker.addImage(healthbarimg, 0);
		tracker.waitForAll();
		healthbarimg = healthbarimg.getScaledInstance((int)(width/4), (int)(height/8), Image.SCALE_SMOOTH);
		ImageIcon healthbaricon = new ImageIcon(healthbarimg);

		JLabel healthbar = new JLabel("");
		healthbar.setIcon(healthbaricon);
		healthbar.setBounds((int)(width*0.02), (int)(height*-0.43), width, height);
		contentPane.add(healthbar);

		Image hudimg = toolkit.getImage("images/HUD.png");
		tracker.addImage(hudimg, 0);
		tracker.waitForAll();
		hudimg = hudimg.getScaledInstance((int)(width/4), (int)(height/8), Image.SCALE_SMOOTH);
		ImageIcon hudicon = new ImageIcon(hudimg);

		JLabel hud = new JLabel("");
		hud.setIcon(hudicon);
		hud.setBounds((int)(width*0.02), (int)(height*-0.43), width, height);
		contentPane.add(hud);

		JLabel score = new JLabel("score: " + scorenum);
		score.setForeground(Color.WHITE);
		score.setFont(new Font("Comic Sans MS", Font.PLAIN, height/30));
		score.setBounds((int)(width*0.3), (int)(height*-0.43), width, height);
		contentPane.add(score);

		Image attackrightimg = toolkit.getImage("images/attackright.gif");
		tracker.addImage(attackrightimg, 0);
		tracker.waitForAll();
		attackrightimg = attackrightimg.getScaledInstance((int)(width/7.5), height/4, Image.SCALE_DEFAULT);
		ImageIcon attackrighticon = new ImageIcon(attackrightimg);

		Image attackleftimg = toolkit.getImage("images/attack.gif");
		tracker.addImage(attackleftimg, 0);
		tracker.waitForAll();
		attackleftimg = attackleftimg.getScaledInstance((int)(width/7.5), height/4, Image.SCALE_DEFAULT);
		ImageIcon attacklefticon = new ImageIcon(attackleftimg);

		Image interactimg = toolkit.getImage("images/interact1.png");
		tracker.addImage(interactimg, 0);
		tracker.waitForAll();
		interactimg = interactimg.getScaledInstance((int)(width/5), height/10, Image.SCALE_SMOOTH);
		ImageIcon interacticon = new ImageIcon(interactimg);

		JLabel interact = new JLabel("");
		interact.setIcon(interacticon);
		interact.setBounds(0, 0, width/5, (int)(height/10));
		contentPane.add(interact);
		interact.setVisible(false);

		Image runrightimg = toolkit.getImage("images/runright.gif");
		tracker.addImage(runrightimg, 0);
		tracker.waitForAll();
		runrightimg = runrightimg.getScaledInstance((int)(width/7.5), height/4, Image.SCALE_DEFAULT);
		ImageIcon runrighticon = new ImageIcon(runrightimg);

		Image runleftimg = toolkit.getImage("images/run.gif");
		tracker.addImage(runleftimg, 0);
		tracker.waitForAll();
		runleftimg = runleftimg.getScaledInstance((int)(width/7.5), height/4, Image.SCALE_DEFAULT);
		ImageIcon runlefticon = new ImageIcon(runleftimg);

		Image maorightimg = toolkit.getImage("images/spriteflipped.png");
		tracker.addImage(maorightimg, 0);
		tracker.waitForAll();
		maorightimg = maorightimg.getScaledInstance((int)(width/7.5), height/4, Image.SCALE_DEFAULT);
		ImageIcon maorighticon = new ImageIcon(maorightimg);

		Image maoleftimg = toolkit.getImage("images/sprite1.png");
		tracker.addImage(maoleftimg, 0);
		tracker.waitForAll();
		maoleftimg = maoleftimg.getScaledInstance((int)(width/7.5), height/4, Image.SCALE_DEFAULT);
		ImageIcon maolefticon = new ImageIcon(maoleftimg);

		JButton mao = new JButton("");
		mao.setIcon(maolefticon);
		mao.setBorderPainted(false); 
		mao.setContentAreaFilled(false); 
		mao.setFocusPainted(false); 
		mao.setOpaque(false);
		//if returned from last screen
		if(returned == false)
		{
			mao.setBounds((int)(width*0.6),(int)(height*0.7),(int)(width/10), height/4);
		}
		else
		{
			mao.setBounds((int)(width*0.05),(int)(height*0.575),(int)(width/10), height/4);
			mao.setIcon(maorighticon);
		}
		mao.addKeyListener(new KeyAdapter() {
			@Override
			//movement
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					clip.start();
					clip.loop(Clip.LOOP_CONTINUOUSLY);
					mao.setIcon(runlefticon);
					//prevents mao from going off screen
					if(mao.getX() < 0)
					{
						mao.setBounds(mao.getX(),mao.getY(), mao.getWidth(), mao.getHeight());
					}
					else
					{
						mao.setBounds((int)(mao.getX()-width*0.01), mao.getY(), mao.getWidth(), mao.getHeight());
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT){
					clip.start();
					clip.loop(Clip.LOOP_CONTINUOUSLY);
					mao.setIcon(runrighticon);
					//prevents mao from going past certain point
					if(mao.getX() > (int)(width*0.65))
					{
						mao.setBounds(mao.getX(),mao.getY(), mao.getWidth(), mao.getHeight());
					}
					else
					{
						mao.setBounds((int)(mao.getX()+width*0.01), mao.getY(), mao.getWidth(), mao.getHeight());
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					clip.start();
					clip.loop(Clip.LOOP_CONTINUOUSLY);
					if(mao.getIcon().equals(maolefticon))
					{
						mao.setIcon(runlefticon);
					}
					if(mao.getIcon().equals(maorighticon))
					{
						mao.setIcon(runrighticon);
					}
					if(mao.getY() <= (int)(height*0.575))
					{
						//prevents mao from going past certain point
						mao.setBounds(mao.getX(), mao.getY(), mao.getWidth(), mao.getHeight());

					}
					else
					{
						mao.setBounds(mao.getX(), (int)(mao.getY()-height*0.02), mao.getWidth(), mao.getHeight());
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN){
					clip.start();
					clip.loop(Clip.LOOP_CONTINUOUSLY);
					if(mao.getIcon().equals(maolefticon))
					{
						mao.setIcon(runlefticon);
					}
					if(mao.getIcon().equals(maorighticon))
					{
						mao.setIcon(runrighticon);
					}
					if(mao.getY() > height-mao.getHeight()-5)
					{
						//prevents mao from going past certain point
						mao.setBounds(mao.getX(), mao.getY(), mao.getWidth(), mao.getHeight());

					}
					else
					{
						mao.setBounds(mao.getX(), (int)(mao.getY()+height*0.02), mao.getWidth(), mao.getHeight());
					}
				}
				//pause menu
				if (e.getKeyCode() == KeyEvent.VK_P){
					JOptionPane.showMessageDialog(null, "resume");
				}
				//attack
				if(e.getKeyCode() == KeyEvent.VK_X) {
					if(mao.getIcon().equals(maolefticon))
					{
						mao.setIcon(attacklefticon);
					}
					if(mao.getIcon().equals(maorighticon))
					{
						mao.setIcon(attackrighticon);
					}
				}

				//computer
				if(mao.getX() > (int)(width*0.45) && mao.getX() < (int)(width*0.525) && (mao.getY() + mao.getHeight()) < (int)(height*0.85))
				{
					background.setIcon(background2icon);
					interact.setVisible(true);
					interact.setBounds((int)(width*0.5), (int)(height*0.425), interact.getWidth(), interact.getHeight());
					if (e.getKeyCode() == KeyEvent.VK_Z)
					{		
						//opens link
						if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
							try {
								Desktop.getDesktop().browse(new URI("https://i.imgur.com/gqNLAVR.jpg"));
							} catch (IOException | URISyntaxException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}
				//door
				else if(mao.getX() > (int)(width*0.03) && mao.getX() < (int)(width*0.1) && (mao.getY() + mao.getHeight()) < (int)(height*0.85))
				{
					interact.setVisible(true);
					interact.setBounds((int)(width*0.01), (int)(height*0.425), interact.getWidth(), interact.getHeight());
					if (e.getKeyCode() == KeyEvent.VK_Z)
					{		
						downstairs.main(false);
						delay(1000);
					}
				}
				else
				{
					background.setIcon(backgroundicon);
					interact.setVisible(false);
				}

			}
			@Override
			//resets icon when key is released
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					clip.stop();
					mao.setIcon(maolefticon);
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT){
					clip.stop();
					mao.setIcon(maorighticon);
				}
				if (e.getKeyCode() == KeyEvent.VK_UP){
					clip.stop();
					if(mao.getIcon().equals(runlefticon))
					{
						mao.setIcon(maolefticon);
					}
					if(mao.getIcon().equals(runrighticon))
					{
						mao.setIcon(maorighticon);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN){
					clip.stop();
					if(mao.getIcon().equals(runlefticon))
					{
						mao.setIcon(maolefticon);
					}
					if(mao.getIcon().equals(runrighticon))
					{
						mao.setIcon(maorighticon);
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_X) {
					if(mao.getIcon().equals(attacklefticon) || mao.getIcon().equals(maolefticon))
					{
						mao.setIcon(maolefticon);
					}
					if(mao.getIcon().equals(attackrighticon) || mao.getIcon().equals(maorighticon))
					{
						mao.setIcon(maorighticon);
					}
				}
			}
		});
		contentPane.add(mao);

		JLabel black = new JLabel("");
		black.setIcon(new ImageIcon("images/black.png"));
		black.setBounds((int)(width*0.05), (int)(height*0.502), width/10, (int)(height/3.5));
		contentPane.add(black);

		background.setIcon(backgroundicon);
		background.setBounds(0, 0, width, height);
		contentPane.add(background);
	}
	public void delay(int time) {
		ActionListener delay = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}	
		};
		Timer timer = new Timer(time, delay);
		timer.setRepeats(false);
		timer.start();
	}
}