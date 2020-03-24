package mao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

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
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class office extends JFrame {

	private JPanel contentPane;
	private int width;
	private int height;
	private JLabel interact = new JLabel("");
	private JButton mao = new JButton("");
	private JLabel healthbar = new JLabel("");
	private JLabel bosshealthbar = new JLabel("");
	private JLabel background = new JLabel("");
	private JLabel imad = new JLabel("");
	private JLabel chairman = new JLabel("");
	private JLabel winner = new JLabel("");
	private ImageIcon chairmandeadicon;
	private int scorenum;
	private boolean stop = false;
	private int x;
	private int y;
	private boolean convo = false;

	File musicFile = new File("audio/bossconvo.wav");
	AudioInputStream musicIn = AudioSystem.getAudioInputStream(musicFile);              
	Clip music = AudioSystem.getClip();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					office frame = new office();
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
	 * @throws LineUnavailableException,  InterruptedException 
	 */
	public office() throws UnsupportedAudioFileException, IOException, LineUnavailableException,  InterruptedException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int)screenSize.getWidth();
		height = (int)screenSize.getHeight();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
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

		music.open(musicIn);

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

		FloatControl gainControl2 = (FloatControl)music.getControl(FloatControl.Type.MASTER_GAIN);
		float range2 = gainControl2.getMaximum() - gainControl2.getMinimum()/2;
		float gain2 = (range2 * volume/3) + gainControl2.getMinimum()/2;
		gainControl2.setValue(gain2);

		if(volume == 0) {
			gainControl2.setValue(gainControl2.getMinimum());
		}
		else {

			gainControl2.setValue(gain2);
		}

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		MediaTracker tracker = new MediaTracker(this);
		
		Image winnerimg = toolkit.getImage("images/yourwinner.png");
		tracker.addImage(winnerimg, 0);
		tracker.waitForAll();
		winnerimg = winnerimg.getScaledInstance(width,height, Image.SCALE_SMOOTH);
		ImageIcon winnericon = new ImageIcon(winnerimg);
		
		winner.setIcon(winnericon);
		winner.setBounds(0, 0, width, height);
		contentPane.add(winner);
		winner.setVisible(false);

		JLabel speech = new JLabel("");
		speech.setForeground(Color.BLACK);
		speech.setFont(new Font("Comic Sans MS", Font.BOLD, (int)(height*0.02)));
		speech.setBounds(0, 0, width, (int)(height/10));
		contentPane.add(speech);
		speech.setVisible(false);

		Image chairmandeadimg = toolkit.getImage("images/chairmandead.png");
		tracker.addImage(chairmandeadimg, 0);
		tracker.waitForAll();
		chairmandeadimg = chairmandeadimg.getScaledInstance((int)(height/5), (int)(width/8), Image.SCALE_SMOOTH);
		chairmandeadicon = new ImageIcon(chairmandeadimg);
		
		Image chairmanimg = toolkit.getImage("images/chairman.png");
		tracker.addImage(chairmanimg, 0);
		tracker.waitForAll();
		chairmanimg = chairmanimg.getScaledInstance((int)(width/11),(int)(height/5), Image.SCALE_SMOOTH);
		ImageIcon chairmanicon = new ImageIcon(chairmanimg);

		chairman.setIcon(chairmanicon);
		chairman.setBounds((int) (width/1.6), (int) (height/2.55),(int)(width/11),(int)(height/5));
		contentPane.add(chairman);
		chairman.setVisible(false);

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

		healthbar.setIcon(healthbaricon);
		healthbar.setBounds((int)(width*0.02), (int)(height*0.007), (int)(width/4), (int)(height/8));
		contentPane.add(healthbar);

		bosshealthbar.setIcon(healthbaricon);
		bosshealthbar.setBounds(chairman.getX(), (int)(chairman.getY()*0.95), chairman.getWidth(), (int)(height*0.01));
		contentPane.add(bosshealthbar);
		bosshealthbar.setVisible(false);

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

		Image interactimg = toolkit.getImage("images/interact1.png");
		tracker.addImage(interactimg, 0);
		tracker.waitForAll();
		interactimg = interactimg.getScaledInstance((int)(width/5), height/10, Image.SCALE_SMOOTH);
		ImageIcon interacticon = new ImageIcon(interactimg);

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

		mao.setBorderPainted(false); 
		mao.setContentAreaFilled(false); 
		mao.setFocusPainted(false); 
		mao.setOpaque(false);
		mao.setBounds((int)(width*0.01), (int)(height*0.52),(int)(width/10), height/4);
		mao.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT && stop == false) {
					clip.start();
					clip.loop(Clip.LOOP_CONTINUOUSLY);
					mao.setIcon(runlefticon);

					if(mao.getX() <= -50)
					{
						mao.setBounds(mao.getX(), (mao.getY()), mao.getWidth(), mao.getHeight());
					}
					else
					{
						mao.setBounds((int)(mao.getX()-width*0.01), mao.getY(), mao.getWidth(), mao.getHeight());
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT && stop == false){
					clip.start();
					clip.loop(Clip.LOOP_CONTINUOUSLY);
					mao.setIcon(runrighticon);

					if(mao.getX() >= (int)(width*0.9))
					{
						mao.setBounds(mao.getX(),mao.getY(), mao.getWidth(), mao.getHeight());
					}
					else
					{
						mao.setBounds((int)(mao.getX()+width*0.01), mao.getY(), mao.getWidth(), mao.getHeight());
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_UP && stop == false) {
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
					if(mao.getY() <= (int)(height*0.55))
					{
						mao.setBounds(mao.getX(), (mao.getY()), mao.getWidth(), mao.getHeight());
					}
					else
					{
						mao.setBounds(mao.getX(), (int)(mao.getY()-height*0.02), mao.getWidth(), mao.getHeight());
					}

				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN && stop == false){
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
					else if(convo == false)
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
				if (e.getKeyCode() == KeyEvent.VK_X && stop == false){
					//attack boss
					if(collision())
					{
						//add score
						scorenum += 10;
						score.setText("score: " + scorenum);
						try {
						addScore(scorenum);
						}catch(Exception e1) {}
						SoundClipTest.main("audio/click.wav");
						//lower boss health
						bosshealthbar.setBounds(chairman.getX(), (int)(chairman.getY()*0.95), bosshealthbar.getWidth()-3, bosshealthbar.getHeight());
					}
					if(mao.getIcon().equals(maolefticon))
					{
						mao.setIcon(attacklefticon);
					}
					else if(mao.getIcon().equals(maorighticon))
					{
						mao.setIcon(attackrighticon);
					}
				}

				if(mao.getX() >= width/3 && convo == false)
				{

					stop = true;
					interact.setBounds(width/3, (int)(mao.getY()*0.7), interact.getWidth(), interact.getHeight());
					interact.setVisible(true);
					//convo
					if (e.getKeyCode() == KeyEvent.VK_Z)
					{
						if(speech.getText().equals(""))
						{						
							chairman.setVisible(true);
							SoundClipTest.main("audio/explosion.wav");
							speech.setVisible(true);
							speech.setBounds((int) (chairman.getX()*1.05),(int) (chairman.getY()*0.8),speech.getWidth(),speech.getHeight());
							speech.setText("ahlie");
							music.start();
						}
						else if(speech.getText().equals("ahlie"))
						{
							speech.setBounds(mao.getX(),(int) (mao.getY()*0.85),speech.getWidth(),speech.getHeight());
							speech.setText("yo i acshe killd u last time wth");
							SoundClipTest.main("audio/click.wav");
						}
						else if(speech.getText().equals("yo i acshe killd u last time wth"))
						{
							speech.setBounds(mao.getX(),(int) (mao.getY()*0.85),speech.getWidth(),speech.getHeight());
							speech.setText("how are you the principal of humberview now");
							SoundClipTest.main("audio/click.wav");
						}
						else if(speech.getText().equals("how are you the principal of humberview now"))
						{
							speech.setBounds((int) (chairman.getX()*1.05),(int) (chairman.getY()*0.8),speech.getWidth(),speech.getHeight());
							speech.setText("hehehehe...");
							SoundClipTest.main("audio/click.wav");
						}
						else if(speech.getText().equals("hehehehe..."))
						{
							speech.setBounds((int) (chairman.getX()*0.8),(int) (chairman.getY()*0.8),speech.getWidth(),speech.getHeight());
							speech.setText("...and you're supposed to be the top student at this school");
							SoundClipTest.main("audio/click.wav");
						}
						else if(speech.getText().equals("...and you're supposed to be the top student at this school"))
						{
							speech.setBounds(mao.getX(),(int) (mao.getY()*0.85),speech.getWidth(),speech.getHeight());
							speech.setText("yo wat is this sorcery");
							SoundClipTest.main("audio/click.wav");
						}
						else if(speech.getText().equals("yo wat is this sorcery"))
						{
							speech.setBounds((int) (chairman.getX()),(int) (chairman.getY()*0.8),speech.getWidth(),speech.getHeight());
							speech.setText("it was a rather simple plan");
							SoundClipTest.main("audio/click.wav");
						}
						else if(speech.getText().equals("it was a rather simple plan"))
						{
							speech.setBounds((int) (chairman.getX()*0.8),(int) (chairman.getY()*0.8),speech.getWidth(),speech.getHeight());
							speech.setText("the person you thought u defeated was infact a hologram");
							SoundClipTest.main("audio/click.wav");
						}
						else if(speech.getText().equals("the person you thought u defeated was infact a hologram"))
						{
							speech.setBounds((int) (chairman.getX()*0.8),(int) (chairman.getY()*0.8),speech.getWidth(),speech.getHeight());
							speech.setText("a mere illusion that could have no effect on me whatsoever");
							SoundClipTest.main("audio/click.wav");
						}
						else if(speech.getText().equals("a mere illusion that could have no effect on me whatsoever"))
						{
							speech.setBounds((int) (chairman.getX()*0.8),(int) (chairman.getY()*0.8),speech.getWidth(),speech.getHeight());
							speech.setText("i then sought out the prinicpal of humberview");
							SoundClipTest.main("audio/click.wav");
						}
						else if(speech.getText().equals("i then sought out the prinicpal of humberview"))
						{
							speech.setBounds((int) (chairman.getX()*1),(int) (chairman.getY()*0.8),speech.getWidth(),speech.getHeight());
							speech.setText("and easily eliminated her");
							SoundClipTest.main("audio/click.wav");
						}
						else if(speech.getText().equals("and easily eliminated her"))
						{
							speech.setBounds((int) (chairman.getX()*0.8),(int) (chairman.getY()*0.8),speech.getWidth(),speech.getHeight());
							speech.setText("and then i simply applied for a job as prinicipal of this school");
							SoundClipTest.main("audio/click.wav");
						}
						else if(speech.getText().equals("and then i simply applied for a job as prinicipal of this school"))
						{
							speech.setBounds((int) (chairman.getX()*0.7),(int) (chairman.getY()*0.8),speech.getWidth(),speech.getHeight());
							speech.setText("and here i am standing infront of the only thing getting in my way from being the only mao");
							SoundClipTest.main("audio/click.wav");
						}
						else if(speech.getText().equals("and here i am standing infront of the only thing getting in my way from being the only mao"))
						{
							speech.setBounds((int) (chairman.getX()),(int) (chairman.getY()*0.8),speech.getWidth(),speech.getHeight());
							speech.setText("this time ur acshe don");
							SoundClipTest.main("audio/click.wav");
						}
						else if(speech.getText().equals("this time ur acshe don"))
						{
							speech.setBounds(mao.getX(),(int) (mao.getY()*0.85),speech.getWidth(),speech.getHeight());
							speech.setText("k styl");
							SoundClipTest.main("audio/click.wav");
						}
						else if(speech.getText().equals("k styl"))
						{
							speech.setBounds((int) (chairman.getX()*1.05),(int) (chairman.getY()*0.8),speech.getWidth(),speech.getHeight());
							speech.setText("now feel my wrath ey");
							SoundClipTest.main("audio/click.wav");
						}
						else if(speech.getText().equals("now feel my wrath ey"))
						{
							speech.setBounds((int) (chairman.getX()*1.05),(int) (chairman.getY()*0.8),speech.getWidth(),speech.getHeight());
							speech.setText("你結束了兄弟");
							SoundClipTest.main("audio/click.wav");
						}
						else if(speech.getText().equals("你結束了兄弟"))
						{
							speech.setVisible(false);
							interact.setVisible(false);
							SoundClipTest.main("audio/click.wav");
							stop = false;
							convo = true;
							music.stop();
							bosshealthbar.setVisible(true);
							move();
							try {
								musicFile = new File("audio/smashmusic2.wav");
								musicIn = AudioSystem.getAudioInputStream(musicFile);
								music = AudioSystem.getClip();
								music.open(musicIn);
								music.start();
							} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}              
						}
					}
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
				if (e.getKeyCode() == KeyEvent.VK_X){
					if(mao.getIcon().equals(attacklefticon))
					{
						mao.setIcon(maolefticon);
					}
					if(mao.getIcon().equals(attackrighticon))
					{
						mao.setIcon(maorighticon);
					}
				}
			}
		});

		mao.setIcon(maorighticon);
		contentPane.add(mao);

		Image backgroundimg = toolkit.getImage("images/office.png");
		tracker.addImage(backgroundimg, 0);
		tracker.waitForAll();
		backgroundimg = backgroundimg.getScaledInstance(width,height, Image.SCALE_SMOOTH);
		ImageIcon backgroundicon = new ImageIcon(backgroundimg);

		JLabel background = new JLabel("");
		background.setIcon(backgroundicon);
		background.setBounds(0, 0, width, height);
		contentPane.add(background);
	}

	public void move() {
		//generates random coordinates to move it to
		x = ThreadLocalRandom.current().nextInt(0, width);
		y = ThreadLocalRandom.current().nextInt((int)(height/2.25)  - chairman.getHeight(), height);

		ActionListener delay = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if boss health is 0 then player wins
				if(bosshealthbar.getWidth() <= 0)
				{
					chairman.setIcon(chairmandeadicon);
					winner.setVisible(true);
					((Timer)e.getSource()).stop();
					//player wins and credtis roll after a 5 seconds
					ActionListener delay = new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							credits.main(null);
							dispose();
							music.stop();
						}	
					};
					Timer timer = new Timer(5000, delay);
					timer.setRepeats(false);
					timer.start();

				}
				//if mao and chairman are colliding then lower health
				if(collision())
				{
					healthbar.setBounds(healthbar.getX(), healthbar.getY(), healthbar.getWidth() - (int)(width*0.001), healthbar.getHeight());
					if(healthbar.getWidth() <= 0)
					{

						music.stop();	
						((Timer)e.getSource()).stop();
						dispose();
						gameover.main(null);
						return;
					}
				}
				//if chairman is not at the generated x coordinate it moves towards it
				if(!(chairman.getX() <= x+10 && chairman.getX() >= x-10))
				{
					//checks if its less or more than current x value then moves left/right
					if(chairman.getX() < x)
					{
						chairman.setBounds(chairman.getX() + 10, chairman.getY(), chairman.getWidth(), chairman.getHeight());
					}
					else
					{
						chairman.setBounds(chairman.getX() - 10, chairman.getY(), chairman.getWidth(), chairman.getHeight());
					}
				}
				else
				{
					x = ThreadLocalRandom.current().nextInt(0, width);
				}
				//if chairman is not at the generated y coordinate it moves towards it
				if(!(chairman.getY() <= y+10 && chairman.getY() >= y-10))
				{
					//checks if its less or more than current x value then moves up/down
					if(chairman.getY() < y)
					{
						chairman.setBounds(chairman.getX(), chairman.getY() + 10, chairman.getWidth(), chairman.getHeight());
					}
					else 
					{
						chairman.setBounds(chairman.getX(), chairman.getY() - 10, chairman.getWidth(), chairman.getHeight());
					}
				}
				else
				{
					y = ThreadLocalRandom.current().nextInt((int)(height/2.25), height);
				}
				bosshealthbar.setBounds(chairman.getX(), (int)(chairman.getY()*0.95), bosshealthbar.getWidth(), bosshealthbar.getHeight());
			}	
		};
		Timer timer = new Timer(20, delay);
		timer.setRepeats(true);
		timer.start();
	}

	public boolean collision(){

		Area areaA = new Area(chairman.getBounds());
		Area areaB = new Area(mao.getBounds());

		return areaA.intersects(areaB.getBounds2D());
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
}