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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class downstairs extends JFrame {

	private JLabel background = new JLabel("");
	private JPanel contentPane;
	private int scorenum;

	/**
	 * Launch the application.
	 */
	public static void main(boolean returned) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					downstairs frame = new downstairs(returned);
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
	public downstairs(boolean returned) throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1500, 800);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);

		Scanner x = new Scanner(new File("src/userdata/currentaccount.txt"));
		Scanner in = new Scanner(new File("src/userdata/" + x.nextLine() + ".txt"));
		in.nextLine();
		in.nextLine();
		scorenum = Integer.parseInt(in.nextLine());
		float volume = Float.parseFloat(in.nextLine())/100;
		in.reset();

		File soundFile = new File("audio/walk.wav");
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);              
		Clip clip = AudioSystem.getClip();
		clip.open(audioIn);

		File piano = new File("audio/piano.wav");
		AudioInputStream audioIn2 = AudioSystem.getAudioInputStream(piano);              
		Clip pianoclip = AudioSystem.getClip();
		pianoclip.open(audioIn2);

		File badnboujee = new File("audio/badnboujee.wav");
		AudioInputStream audioIn3 = AudioSystem.getAudioInputStream(badnboujee);              
		Clip badnboujeeclip = AudioSystem.getClip();
		badnboujeeclip.open(audioIn3);

		FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * volume) + gainControl.getMinimum();
		gainControl.setValue(gain);

		if(volume == 0) {
			gainControl.setValue(gainControl.getMinimum());
		}

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		MediaTracker tracker = new MediaTracker(this);

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

		Image backgroundimg = toolkit.getImage("images/downstairs.png");
		tracker.addImage(backgroundimg, 0);
		tracker.waitForAll();
		backgroundimg = backgroundimg.getScaledInstance(width,height, Image.SCALE_SMOOTH);
		ImageIcon backgroundicon = new ImageIcon(backgroundimg);

		Image background2img = toolkit.getImage("images/downstairs2.png");
		tracker.addImage(background2img, 0);
		tracker.waitForAll();
		background2img = background2img.getScaledInstance(width,height, Image.SCALE_SMOOTH);
		ImageIcon background2icon = new ImageIcon(background2img);

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
		mao.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("x:" + mao.getX());
				System.out.print(" y:" + mao.getY());
				System.out.print(" w:" + (double)mao.getX()/width);
				System.out.print(" y:" + (double)mao.getY()/height);
			}
		});
		mao.setBorderPainted(false); 
		mao.setContentAreaFilled(false); 
		mao.setFocusPainted(false); 
		mao.setOpaque(false);
		if(returned)
		{
			mao.setBounds((int)(width*0.01),(int)(height*0.5),(int)(width/10), height/4);	
			mao.setIcon(maorighticon);
		}
		else
		{
			mao.setBounds((int)(width*0.86),(int)((height+mao.getHeight())*-0.12),(int)(width/10), height/4);
			mao.setIcon(maolefticon);
		}
		mao.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				boolean stairs = false;
				if(mao.getX() > (int)(width*0.42) && mao.getY() < (int)(height*0.50))
				{
					stairs = true;
				}
				else
				{
					stairs = false;
				}

				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					clip.start();
					clip.loop(Clip.LOOP_CONTINUOUSLY);
					mao.setIcon(runlefticon);
					if(stairs == true)
					{
						mao.setBounds((int)(mao.getX()-width*0.001), (int)(mao.getY()+height*0.017), mao.getWidth(), mao.getHeight());
					}
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
					if(stairs == true)
					{
						mao.setBounds((int)(mao.getX()+width*0.001), (int)(mao.getY()-height*0.0165), mao.getWidth(), mao.getHeight());
					}
					if(mao.getX() > (int)(width*0.95))
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
					if(stairs == true)
					{
						mao.setBounds(mao.getX(), mao.getY(), mao.getWidth(), mao.getHeight());
					}
					if(mao.getX() < (int)(width*0.42) && mao.getY() > (int)(height*0.495))
					{
						mao.setBounds(mao.getX(), (int)(mao.getY()-height*0.02), mao.getWidth(), mao.getHeight());
					}
					if(mao.getX() > (int)(width*0.42) && mao.getY() > (int)(height*0.51))
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
					if(stairs == true)
					{
						mao.setBounds(mao.getX(), mao.getY(), mao.getWidth(), mao.getHeight());
					}
					else if(mao.getY() > height-mao.getHeight())
					{
						mao.setBounds(mao.getX(), mao.getY(), mao.getWidth(), mao.getHeight());
					}
					else
					{
						mao.setBounds(mao.getX(), (int)(mao.getY()+height*0.02), mao.getWidth(), mao.getHeight());
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_P){
					JOptionPane.showMessageDialog(null, "resume");
				}
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

				//go back upstairs
				if(mao.getY()+mao.getHeight() < 0)
				{
					clip.stop();
					home.main(true);
					dispose();


				}
				//piano
				int randomNum = ThreadLocalRandom.current().nextInt(0, 2 + 1);
				Clip[] songs = {pianoclip, pianoclip, badnboujeeclip};
				if(mao.getX() > (int)(width*0.75) && mao.getX() < (int)(width*0.87) && mao.getY() > (int)(height*0.5) && mao.getY() < (int)(height*0.57))
				{
					interact.setVisible(true);
					interact.setBounds((int)(width*0.725), (int)(height*0.425), interact.getWidth(), interact.getHeight());

					if (e.getKeyCode() == KeyEvent.VK_Z)
					{	
						scorenum += 5;
						score.setText("score: " + scorenum);
						try {
						addScore(scorenum);
						}
						catch(Exception e1) {
							
						}
						mao.setIcon(attackrighticon);				
						songs[randomNum].start();
					}
					else
					{
						songs[randomNum].stop();
					}
				}
				//door
				else if(mao.getX() > (int)(width*-0.006) && mao.getX() < (int)(width*0.08) && (mao.getY() + mao.getHeight()) < (int)(height*0.85))
				{
					interact.setVisible(true);
					interact.setBounds((int)(width*0.005), (int)(height*0.32), interact.getWidth(), interact.getHeight());
					if (e.getKeyCode() == KeyEvent.VK_Z)
					{		
						
						outside.main(null);
						delay(1000);
					}
				}
				//window shmaudy
				else if(mao.getX() > (int)(width*0.17) && mao.getX() < (int)(width*0.3) && (mao.getY() + mao.getHeight()) < (int)(height*0.85))
				{
					interact.setVisible(true);
					interact.setBounds((int)(width*0.17), (int)(height*0.2), interact.getWidth(), interact.getHeight());
					if (e.getKeyCode() == KeyEvent.VK_Z)
					{		
						scorenum += 5;
						score.setText("score: " + scorenum);
						try {
						addScore(scorenum);
						}catch(Exception e1) {}
						if(background.getIcon().equals(backgroundicon))
						{
							background.setIcon(background2icon);
						}
						else
						{
							background.setIcon(backgroundicon);
						}
						SoundClipTest.main("audio/blinds.wav");
					}
				}
				else
				{
					songs[randomNum].setFramePosition(0);
					songs[randomNum].stop();
					interact.setVisible(false);
				}

			}
			@Override
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
		black.setBounds((int)(width*0.02), (int)(height*0.425), width/10, (int)(height/3.5));
		contentPane.add(black);

		background.setIcon(backgroundicon);
		background.setBounds(0, 0, width, height);
		contentPane.add(background);
	}
	public void addScore(int num) throws FileNotFoundException {
		Scanner x = new Scanner(new File("src/userdata/currentaccount.txt"));
		String account = x.nextLine();
		Scanner in = new Scanner(new File("src/userdata/" + account + ".txt"));
		String username = in.nextLine();
		String pass = in.nextLine();
		in.nextLine();
		String vol = in.nextLine();
		List<String> lines = Arrays.asList(username, pass, Integer.toString(num), vol);
		Path file = Paths.get("src/userdata/" + account + ".txt");
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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