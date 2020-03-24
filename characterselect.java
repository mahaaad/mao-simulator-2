package mao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class characterselect extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					characterselect frame = new characterselect();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * @throws LineUnavailableException 
	 * @throws InterruptedException 
	 */
	public characterselect() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		MediaTracker tracker = new MediaTracker(this);
		
		Scanner x = new Scanner(new File("src/userdata/currentaccount.txt"));
		Scanner in = new Scanner(new File("src/userdata/" + x.nextLine() + ".txt"));
		in.nextLine(); in.nextLine(); in.nextLine(); in.nextLine();
		float volume = Float.parseFloat(in.nextLine())/100;
		in.reset();

		String[] songs = {"audio/smashmusic.wav","audio/smashmusic2.wav"};
		File soundFile = new File(songs[ThreadLocalRandom.current().nextInt(0, 1 + 1)]);
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);              
		Clip clip = AudioSystem.getClip();
		clip.open(audioIn);
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		
		FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * volume) + gainControl.getMinimum();
		gainControl.setValue(gain);

		if(volume == 0) {
			gainControl.setValue(gainControl.getMinimum());
		}
		
		Image titleimg = toolkit.getImage("images/choosecharacter.png");
		tracker.addImage(titleimg, 0);
		tracker.waitForAll();
		titleimg = titleimg.getScaledInstance((int)(width*0.6),(int)(height*0.1), Image.SCALE_SMOOTH);
		ImageIcon titleicon = new ImageIcon(titleimg);
		
		JLabel title = new JLabel("");
		title.setIcon(titleicon);
		title.setBounds((int)(width*0.2),(int)(height*-0.4),width,height);
		contentPane.add(title);
		
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
				titlescreen.main(null);
				SoundClipTest.main("audio/click.wav");
				clip.stop();
			}
		});
		back.setBounds((int)(width*0.9), (int)(height*0.9), width/9, height/12);
		contentPane.add(back);
		
		JLabel clouds = new JLabel("");
		clouds.setIcon(new ImageIcon("images/clouds.gif"));
		clouds.setBounds(0, 0, width, height);
		contentPane.add(clouds);
		
	}

}
