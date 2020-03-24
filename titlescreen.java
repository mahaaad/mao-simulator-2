package mao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;

import java.time.*;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class titlescreen extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					titlescreen frame = new titlescreen(); 
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
	public titlescreen() throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, width, height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);

		Scanner a = new Scanner(new File("src/userdata/currentaccount.txt"));
		Scanner in = new Scanner(new File("src/userdata/" + a.nextLine() + ".txt"));
		in.nextLine(); in.nextLine(); in.nextLine();
		float volume = Float.parseFloat(in.nextLine())/100;
		System.out.println(volume);
		in.reset();


		File soundFile = new File("audio/startscreen.wav");
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);              
		Clip clip = AudioSystem.getClip();
		clip.open(audioIn);
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);


		FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		float range = gainControl.getMaximum() - gainControl.getMinimum()/2;
		float gain = (range * volume) + gainControl.getMinimum()/2;
		gainControl.setValue(gain);

		if(volume == 0) {
			gainControl.setValue(gainControl.getMinimum());
		}
		else {

			gainControl.setValue(gain);
		}

		//button x/y/width/height
		int x = (int) (width-(width*0.5));
		int y = (int) (height-(height*0.65));
		int w = x/2;
		int h = y/5;

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		MediaTracker tracker = new MediaTracker(this);

		JLabel title = new JLabel("");
		title.setBounds((int)(width*0.1), (int)(height*-0.25), width, height);
		contentPane.add(title);

		JLabel two = new JLabel("");
		two.setBounds((int)(width*0.825), (int)(height*-0.27), width, height);
		contentPane.add(two);

		JLabel start = new JLabel();
		start.setBounds(x, (int)(y*1.075), w, h);
		Image startimg = toolkit.getImage("images/singleplayer.png");
		tracker.addImage(startimg, 0);
		tracker.waitForAll();
		startimg = startimg.getScaledInstance((int)(start.getX()/2.25), (int)(start.getY()/3), Image.SCALE_SMOOTH);
		ImageIcon starticon = new ImageIcon(startimg);	
		Image startwhiteimg = toolkit.getImage("images/singleplayerwhite.png");
		tracker.addImage(startwhiteimg, 0);
		tracker.waitForAll();
		startwhiteimg = startwhiteimg.getScaledInstance((int)(start.getX()/2.25), (int)(start.getY()/3), Image.SCALE_SMOOTH);
		ImageIcon startwhiteicon = new ImageIcon(startwhiteimg);	
		start.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				start.setIcon(startwhiteicon);
				SoundClipTest.main("audio/hover.wav");

			}
			@Override
			public void mouseExited(MouseEvent e) {
				start.setIcon(starticon);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				home.main(false);
				SoundClipTest.main("audio/click.wav");
				clip.stop();
				dispose();
			}
		});
		contentPane.add(start);
		
		JLabel multiplayer = new JLabel();
		multiplayer.setBounds((int)(x*0.97), (int)(y*1.3), w, h);
		Image multiplayerimg = toolkit.getImage("images/multiplayer.png");
		tracker.addImage(multiplayerimg, 0);
		tracker.waitForAll();
		multiplayerimg = multiplayerimg.getScaledInstance((int)(start.getX()/2), (int)(start.getY()/3), Image.SCALE_SMOOTH);
		ImageIcon multiplayericon = new ImageIcon(multiplayerimg);	
		Image multiplayerwhiteimg = toolkit.getImage("images/multiplayerwhite.png");
		tracker.addImage(multiplayerwhiteimg, 0);
		tracker.waitForAll();
		multiplayerwhiteimg = multiplayerwhiteimg.getScaledInstance((int)(start.getX()/2), (int)(start.getY()/3), Image.SCALE_SMOOTH);
		ImageIcon multiplayerwhiteicon = new ImageIcon(multiplayerwhiteimg);	
		multiplayer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				multiplayer.setIcon(multiplayerwhiteicon);
				SoundClipTest.main("audio/hover.wav");

			}
			@Override
			public void mouseExited(MouseEvent e) {
				multiplayer.setIcon(multiplayericon);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				multiplayer x;
				try {
					x = new multiplayer();
					x.main(null);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	
				SoundClipTest.main("audio/click.wav");
				clip.stop();
				dispose();

			}
		});
		contentPane.add(multiplayer);

		JLabel settings = new JLabel();
		settings.setBounds((int)(x*1.01), (int)(y*1.525), w, h);
		Image settingsimg = toolkit.getImage("images/settings.png");
		tracker.addImage(settingsimg, 0);
		tracker.waitForAll();
		settingsimg = settingsimg.getScaledInstance((int)(start.getX()/2.7), (int)(start.getY()/6.5), Image.SCALE_SMOOTH);
		ImageIcon settingsicon = new ImageIcon(settingsimg);	
		Image settingswhiteimg = toolkit.getImage("images/settingswhite.png");
		tracker.addImage(settingswhiteimg, 0);
		tracker.waitForAll();
		settingswhiteimg = settingswhiteimg.getScaledInstance((int)(start.getX()/2.7), (int)(start.getY()/6.5), Image.SCALE_SMOOTH);
		ImageIcon settingswhiteicon = new ImageIcon(settingswhiteimg);
		settings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				settings.setIcon(settingswhiteicon);
				SoundClipTest.main("audio/hover.wav");

			}
			@Override
			public void mouseExited(MouseEvent e) {
				settings.setIcon(settingsicon);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				mao.settings.main(null);
				SoundClipTest.main("audio/click.wav");
				clip.stop();
			}
		});
		contentPane.add(settings);

		JLabel highscore = new JLabel();
		highscore.setBounds((int)(x*1.01), (int)(y*1.725), w, h);
		Image highscoreimg = toolkit.getImage("images/highscores.png");
		tracker.addImage(highscoreimg, 0);
		tracker.waitForAll();
		highscoreimg = highscoreimg.getScaledInstance((int)(start.getX()/2.7), (int)(start.getY()/6), Image.SCALE_SMOOTH);
		ImageIcon highscoreicon = new ImageIcon(highscoreimg);	
		Image highscorewhiteimg = toolkit.getImage("images/highscoreswhite.png");
		tracker.addImage(highscorewhiteimg, 0);
		tracker.waitForAll();
		highscorewhiteimg = highscorewhiteimg.getScaledInstance((int)(start.getX()/2.7), (int)(start.getY()/6), Image.SCALE_SMOOTH);
		ImageIcon highscorewhiteicon = new ImageIcon(highscorewhiteimg);
		highscore.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				highscore.setIcon(highscorewhiteicon);

				SoundClipTest.main("audio/hover.wav");

			}
			@Override
			public void mouseExited(MouseEvent e) {
				highscore.setIcon(highscoreicon);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				highscores.main(null);
				SoundClipTest.main("audio/click.wav");

			}
		});
		contentPane.add(highscore);

		JLabel instructions = new JLabel();
		instructions.setBounds((int)(x*1.01), (int)(y*1.95), w, h);
		Image instructionsimg = toolkit.getImage("images/instructions.png");
		tracker.addImage(instructionsimg, 0);
		tracker.waitForAll();
		instructionsimg = instructionsimg.getScaledInstance((int)(start.getX()/2.5), (int)(start.getY()/6), Image.SCALE_SMOOTH);
		ImageIcon instructionsicon = new ImageIcon(instructionsimg);	
		Image instructionswhiteimg = toolkit.getImage("images/instructionswhite.png");
		tracker.addImage(instructionswhiteimg, 0);
		tracker.waitForAll();
		instructionswhiteimg = instructionswhiteimg.getScaledInstance((int)(start.getX()/2.5), (int)(start.getY()/6), Image.SCALE_SMOOTH);
		ImageIcon instructionswhiteicon = new ImageIcon(instructionswhiteimg);
		instructions.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				instructions.setIcon(instructionswhiteicon);

				SoundClipTest.main("audio/hover.wav");

			}
			@Override
			public void mouseExited(MouseEvent e) {
				instructions.setIcon(instructionsicon);
			}
			@Override
			public void mouseClicked(MouseEvent e) {

				howtoplay.main(null);

				SoundClipTest.main("audio/click.wav");

			}
		});
		contentPane.add(instructions);

		JLabel credits = new JLabel();
		credits.setBounds((int)(x*0.99), (int)(y*2.17), w, h);
		Image creditsimg = toolkit.getImage("images/credits.png");
		tracker.addImage(creditsimg, 0);
		tracker.waitForAll();
		creditsimg = creditsimg.getScaledInstance((int)(start.getX()/2.5), (int)(start.getY()/4), Image.SCALE_SMOOTH);
		ImageIcon creditsicon = new ImageIcon(creditsimg);	
		Image creditswhiteimg = toolkit.getImage("images/creditswhite.png");
		tracker.addImage(creditswhiteimg, 0);
		tracker.waitForAll();
		creditswhiteimg = creditswhiteimg.getScaledInstance((int)(start.getX()/2.5), (int)(start.getY()/4), Image.SCALE_SMOOTH);
		ImageIcon creditswhiteicon = new ImageIcon(creditswhiteimg);
		credits.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				credits.setIcon(creditswhiteicon);

				SoundClipTest.main("audio/hover.wav");

			}
			@Override
			public void mouseExited(MouseEvent e) {
				credits.setIcon(creditsicon);
			}
			@Override
			public void mouseClicked(MouseEvent e) {

				clip.stop();
				credits x;
				try {
					x = new credits();
					x.main(null);
				} catch (InterruptedException | UnsupportedAudioFileException | IOException
						| LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				SoundClipTest.main("audio/click.wav");

			}
		});
		contentPane.add(credits);
		
		Image image = toolkit.getImage("images/background.png");
		tracker.addImage(image, 0);
		tracker.waitForAll();
		image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(image);

		Image titleimg = toolkit.getImage("images/title.png");
		tracker.addImage(titleimg, 0);
		tracker.waitForAll();
		titleimg = titleimg.getScaledInstance((int)(width*0.75),(int)(height*0.175), Image.SCALE_SMOOTH);
		ImageIcon titleicon = new ImageIcon(titleimg);

		Image twoimg = toolkit.getImage("images/2.png");
		tracker.addImage(twoimg, 0);
		tracker.waitForAll();
		twoimg = twoimg.getScaledInstance((int)(width*0.1),(int)(height*0.25), Image.SCALE_SMOOTH);
		ImageIcon twoicon = new ImageIcon(twoimg);

		title.setIcon(titleicon);
		two.setIcon(twoicon);
		start.setIcon(starticon);
		settings.setIcon(settingsicon);
		highscore.setIcon(highscoreicon);
		instructions.setIcon(instructionsicon);
		multiplayer.setIcon(multiplayericon);
		credits.setIcon(creditsicon);

		Image quitimg = toolkit.getImage("images/quit.png");
		tracker.addImage(quitimg, 0);
		tracker.waitForAll();
		quitimg = quitimg.getScaledInstance((int)(w*0.4), (int)(h*0.9), Image.SCALE_SMOOTH);
		ImageIcon quiticon = new ImageIcon(quitimg);

		Image quitwhiteimg = toolkit.getImage("images/quitwhite.png");
		tracker.addImage(quitwhiteimg, 0);
		tracker.waitForAll();
		quitwhiteimg = quitwhiteimg.getScaledInstance((int)(w*0.4), (int)(h*0.9), Image.SCALE_SMOOTH);
		ImageIcon quitwhiteicon = new ImageIcon(quitwhiteimg);

		JLabel quit = new JLabel();
		quit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				quit.setIcon(quitwhiteicon);

				SoundClipTest.main("audio/hover.wav");

			}
			public void mouseExited(MouseEvent arg0) {
				quit.setIcon(quiticon);
			}
			public void mouseClicked(MouseEvent arg0) {

				SoundClipTest.main("audio/click.wav");

				System.exit(0);
			}
		});
		quit.setIcon(quiticon);
		quit.setBounds((int)(width*0.9), (int)(y*2.72), (int)(w*0.6), (int)(h*0.5));
		contentPane.add(quit);

		Image quavoimg = toolkit.getImage("images/quavo.png");
		tracker.addImage(quavoimg, 0);
		tracker.waitForAll();
		quavoimg = quavoimg.getScaledInstance((int)(width*0.1),(int)(height*0.1), Image.SCALE_SMOOTH);
		ImageIcon quavoicon = new ImageIcon(quavoimg);

		JLabel quavo = new JLabel("");
		quavo.setIcon(quavoicon);
		quavo.setBounds((int)(width*0.85), (int)(height*0.165), (int)(width*0.4), (int)(height*0.5));
		contentPane.add(quavo);

		Image topmaoimg = toolkit.getImage("images/spriteflipped.png");
		tracker.addImage(topmaoimg, 0);
		tracker.waitForAll();
		topmaoimg = topmaoimg.getScaledInstance((int)(width*0.07),(int)(height*0.1), Image.SCALE_SMOOTH);
		ImageIcon topmaoicon = new ImageIcon(topmaoimg);

		JLabel topmao = new JLabel("");
		topmao.setIcon(topmaoicon);
		topmao.setBounds((int)(width*0.71), (int)(height*-0.1), (int)(width*0.4), (int)(height*0.5));
		contentPane.add(topmao);

		Image mao2img = toolkit.getImage("images/attackright.gif");
		tracker.addImage(mao2img, 0);
		tracker.waitForAll();
		mao2img = mao2img.getScaledInstance((int)(width*0.125),(int)(height*0.2), Image.SCALE_DEFAULT);
		ImageIcon mao2icon = new ImageIcon(mao2img);

		JLabel mao2 = new JLabel("");
		mao2.setIcon(mao2icon);
		mao2.setBounds((int)(width*0.69), (int)(height*0.6), (int)(width*0.4), (int)(height*0.5));
		contentPane.add(mao2);

		Image maoimg = toolkit.getImage("images/sprite1.png");
		tracker.addImage(maoimg, 0);
		tracker.waitForAll();
		maoimg = maoimg.getScaledInstance((int)(width*0.25),(int)(height*0.45), Image.SCALE_SMOOTH);
		ImageIcon maoicon = new ImageIcon(maoimg);

		JLabel mao = new JLabel("");
		mao.setIcon(maoicon);
		mao.setBounds((int)(width*0.3), (int)(height*0.35), (int)(width*0.4), (int)(height*0.5));
		contentPane.add(mao);

		Image mitcelimg = toolkit.getImage("images/musicmitcel.png");
		tracker.addImage(mitcelimg, 0);
		tracker.waitForAll();
		mitcelimg = mitcelimg.getScaledInstance((int)(width*0.11),(int)(height*0.2), Image.SCALE_SMOOTH);
		ImageIcon mitcelicon = new ImageIcon(mitcelimg);

		JLabel mitcel = new JLabel("");
		mitcel.setIcon(mitcelicon);
		mitcel.setBounds((int)(width*0.75), (int)(height*0.7), (int)(width*0.4), (int)(height*0.3));
		contentPane.add(mitcel);

		JLabel background = new JLabel("");
		background.setIcon(icon);
		background.setBounds(0, 0, width, height);
		contentPane.add(background);

		Image cloudsimg = toolkit.getImage("images/clouds.gif");
		tracker.addImage(cloudsimg, 0);
		tracker.waitForAll();
		cloudsimg = cloudsimg.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		ImageIcon cloudsicon = new ImageIcon(cloudsimg);

		JLabel clouds = new JLabel("");
		clouds.setIcon(new ImageIcon("images/clouds.gif"));
		clouds.setBounds(0, 0, width, height);
		contentPane.add(clouds);

	}
}
