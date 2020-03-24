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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class classroom extends JFrame {

	private JPanel contentPane;
	private int width;
	private int height;
	private JButton mao = new JButton("");
	private JLabel background = new JLabel("");
	private JLabel imad = new JLabel("");
	private JLabel mitcel = new JLabel("");
	private JLabel interact = new JLabel("");
	//if stop is true the player cannot move
	private boolean stop = false;
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	private MediaTracker tracker = new MediaTracker(this);
	private ImageIcon imadicon;
	private ImageIcon imadrighticon;
	private ImageIcon maolefticon;
	private ImageIcon maorighticon;
	private boolean convo = false;

	File musicFile = new File("audio/badnboujee2.wav");
	AudioInputStream musicIn = AudioSystem.getAudioInputStream(musicFile);              
	Clip music = AudioSystem.getClip();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					classroom frame = new classroom();
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
	public classroom() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
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
		String scorenum = in.nextLine();
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
		float gain2 = (range2 * volume/2) + gainControl2.getMinimum()/2;
		gainControl2.setValue(gain2);

		if(volume == 0) {
			gainControl2.setValue(gainControl2.getMinimum());
		}
		else {

			gainControl2.setValue(gain2);
		}

		Image quavoimg = toolkit.getImage("images/quavoleft.png");
		tracker.addImage(quavoimg, 0);
		tracker.waitForAll();
		quavoimg = quavoimg.getScaledInstance((int)(width*0.15),(int)(height*0.15), Image.SCALE_SMOOTH);
		ImageIcon quavoicon = new ImageIcon(quavoimg);

		JLabel quavo = new JLabel("");
		quavo.setIcon(quavoicon);
		quavo.setBounds((int)(width*0.825), (int)(height*0.375), (int)(width*0.4), (int)(height*0.5));
		contentPane.add(quavo);
		quavo.setVisible(false);

		Image mitcelimg = toolkit.getImage("images/musicmitcel.png");
		tracker.addImage(mitcelimg, 0);
		tracker.waitForAll();
		mitcelimg = mitcelimg.getScaledInstance((int)(width/7.5), (int)(height/4), Image.SCALE_SMOOTH);
		ImageIcon mitcelicon = new ImageIcon(mitcelimg);

		mitcel.setIcon(mitcelicon);
		mitcel.setBounds((int)(width*0.7), (int)(height*0.01), width, height);
		contentPane.add(mitcel);
		mitcel.setVisible(false);

		Image imadimg = toolkit.getImage("images/imadsprite.png");
		tracker.addImage(imadimg, 0);
		tracker.waitForAll();
		imadimg = imadimg.getScaledInstance((int)(width/7.5), (int)(height/4), Image.SCALE_SMOOTH);
		imadicon = new ImageIcon(imadimg);

		Image imadrightimg = toolkit.getImage("images/imadrightsprite.png");
		tracker.addImage(imadrightimg, 0);
		tracker.waitForAll();
		imadrightimg = imadrightimg.getScaledInstance((int)(width/7.5), (int)(height/4), Image.SCALE_SMOOTH);
		imadrighticon = new ImageIcon(imadrightimg);


		imad.setIcon(imadrighticon);
		imad.setBounds((int)(width*-0.005), (int)(height*0.1), width, height);
		contentPane.add(imad);
		imad.setVisible(false);

		Image backgroundimg = toolkit.getImage("images/classroom.png");
		tracker.addImage(backgroundimg, 0);
		tracker.waitForAll();
		backgroundimg = backgroundimg.getScaledInstance(width,height, Image.SCALE_SMOOTH);
		ImageIcon backgroundicon = new ImageIcon(backgroundimg);

		Image background2img = toolkit.getImage("images/classroom2.png");
		tracker.addImage(background2img, 0);
		tracker.waitForAll();
		background2img = background2img.getScaledInstance(width,height, Image.SCALE_SMOOTH);
		ImageIcon background2icon = new ImageIcon(background2img);

		JLabel speech = new JLabel("");
		speech.setForeground(Color.WHITE);
		speech.setFont(new Font("Comic Sans MS", Font.BOLD, (int)(height*0.02)));
		speech.setBounds(0, 0, width/3, (int)(height/10));
		contentPane.add(speech);
		speech.setVisible(false);

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
		healthbar.setBounds((int)(width*0.02), (int)(height*-0.43), (int)((width/4)), height);
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
		score.setBounds((int)(width*0.27), (int)(height*-0.43), width, height);
		contentPane.add(score);

		Image interactimg = toolkit.getImage("images/interactwhite.png");
		tracker.addImage(interactimg, 0);
		tracker.waitForAll();
		interactimg = interactimg.getScaledInstance((int)(width/5), height/10, Image.SCALE_SMOOTH);
		ImageIcon interacticon = new ImageIcon(interactimg);

		Image interactblackimg = toolkit.getImage("images/interacwhite.png");
		tracker.addImage(interactblackimg, 0);
		tracker.waitForAll();
		interactblackimg = interactblackimg.getScaledInstance((int)(width/5), height/10, Image.SCALE_SMOOTH);
		ImageIcon interactblackicon = new ImageIcon(interactblackimg);

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

		mao.setBounds((int)(width*0.7),(int)(height*0.8575),(int)(width/10), height/4);
		mao.setIcon(maorighticon);
		mao.setBorderPainted(false); 
		mao.setContentAreaFilled(false); 
		mao.setFocusPainted(false); 
		mao.setOpaque(false);
		mao.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT && stop == false) {
					clip.start();
					clip.loop(Clip.LOOP_CONTINUOUSLY);
					mao.setIcon(runlefticon);
					if(mao.getX() < 0)
					{
						mao.setBounds(mao.getX(),mao.getY(), mao.getWidth(), mao.getHeight());
					}
					else if(mao.getY() <= (int)(height*0.6) && mao.getX() <= width*0.625)
					{
						mao.setBounds(mao.getX(),mao.getY(), mao.getWidth(), mao.getHeight());
					}
					else if(convo)
					{
						mao.setIcon(maorighticon);
						imad.setIcon(imadrighticon);
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
					if(mao.getX() > (int)(width*0.775))
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
					if(mao.getY() <= (int)(height*0.575))
					{
						mao.setBounds(mao.getX(), mao.getY(), mao.getWidth(), mao.getHeight());

					}
					else if (mao.getX() <= width*0.6 && mao.getY() <= (int)(height*0.6))
					{
						mao.setBounds(mao.getX(), mao.getY(), mao.getWidth(), mao.getHeight());
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
					if(mao.getY() > height-mao.getHeight()-5)
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
				if(e.getKeyCode() == KeyEvent.VK_X  && stop == false) {
					if(mao.getIcon().equals(maolefticon))
					{
						mao.setIcon(attacklefticon);
					}
					if(mao.getIcon().equals(maorighticon))
					{
						mao.setIcon(attackrighticon);
					}
				}

				//pick up test from desk
				if(mao.getY() <= (int)(height*0.575) && mao.getX() > (int)(width*0.775) && background.getIcon().equals(backgroundicon) && convo == false)
				{
					interact.setVisible(true);
					interact.setBounds(mao.getX(), (int)(height*0.475), interact.getWidth(), interact.getHeight());
					if (e.getKeyCode() == KeyEvent.VK_Z)
					{
						SoundClipTest.main("audio/paper.wav");
						background.setIcon(background2icon);
						interact.setVisible(false);

					}

				}
				//move away from desk and start imad animation
				else if ((e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_DOWN) && !imad.isVisible() && background.getIcon().equals(background2icon))
				{
					mao.setIcon(maolefticon);
					stop = true;
					imad.setVisible(true);
					SoundClipTest.main("audio/door.wav");
					imadDelay();
				}
				//convo
				else if(imad.isVisible() && imad.getX() >= (int)(width*0.64))
				{

					if (e.getKeyCode() == KeyEvent.VK_Z)
					{
						if(!speech.isVisible() && convo == false)
						{
							SoundClipTest.main("audio/click.wav");
							speech.setForeground(Color.BLACK);
							speech.setVisible(true);
							speech.setText("allow me");
							speech.setBounds((int)(imad.getX()*1.05), (int)(height/2), speech.getWidth(), speech.getHeight());
						}
						else if(speech.getText().equals("allow me"))
						{
							SoundClipTest.main("audio/click.wav");
							speech.setBounds((int)(mao.getX()), (int)(height/2), speech.getWidth(), speech.getHeight());
							speech.setText("k ur lowed then");
							speech.setForeground(Color.WHITE);
						}
						else if(speech.getText().equals("k ur lowed then"))
						{
							mao.setBounds(mao.getX()-(int)(width*0.05), mao.getY(), mao.getWidth(), mao.getHeight());
							SoundClipTest.main("audio/click.wav");
							SoundClipTest.main("audio/paper.wav");
							speech.setBounds((int)(mao.getX()), (int)(height/2), speech.getWidth(), speech.getHeight());
							speech.setText("");
							speech.setForeground(Color.WHITE);
						}
						else if(speech.getText().equals(""))
						{
							mao.setBounds(mao.getX()+(int)(width*0.025), mao.getY(), mao.getWidth(), mao.getHeight());
							SoundClipTest.main("audio/click.wav");
							speech.setBounds((int)(imad.getX()*1.075), (int)(height/2), speech.getWidth(), speech.getHeight());
							speech.setText("...");
							speech.setForeground(Color.WHITE);
						}
						else if(speech.getText().equals("..."))
						{
							SoundClipTest.main("audio/click.wav");
							speech.setBounds((int)(imad.getX()*1.05), (int)(height/2), speech.getWidth(), speech.getHeight());
							speech.setText("ok im bare ready now");
							speech.setForeground(Color.WHITE);
						}
						else if(speech.getText().equals("ok im bare ready now"))
						{
							SoundClipTest.main("audio/click.wav");
							speech.setBounds((int)(mao.getX()*0.95), (int)(speech.getY()*0.96), speech.getWidth(), speech.getHeight());
							speech.setText("<html>k lets go back outside before <br>the teacher comes in ay hehe xD</html>");
							speech.setForeground(Color.WHITE);
							imad.setIcon(imadicon);
						}
						else if(speech.getText().equals("<html>k lets go back outside before <br>the teacher comes in ay hehe xD</html>"))
						{
							SoundClipTest.main("audio/click.wav");
							convo = true;
							interact.setVisible(false);
							speech.setVisible(false);
							quavo.setVisible(true);
							SoundClipTest.main("audio/explosion.wav");		

							ActionListener delay = new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									music.start();
									imad.setIcon(imadrighticon);
									mao.setIcon(maorighticon);
									interact.setBounds((int)(quavo.getX()*0.8), (int)(quavo.getY()*2), interact.getWidth(), interact.getHeight());
									interact.setIcon(interactblackicon);
									speech.setVisible(true);
									speech.setText("ya u mons thought i was gona lowe that ay");
									speech.setBounds((int)(quavo.getX()*0.95), (int)(quavo.getY()*1.2), speech.getWidth(), speech.getHeight());
								}	
							};
							Timer timer = new Timer(1500, delay);
							timer.setRepeats(false);
							timer.start();
						}
						else if(speech.getText().equals("ya u mons thought i was gona lowe that ay"))
						{
							speech.setBounds((int)(quavo.getX()), (int)(quavo.getY()*1.2), speech.getWidth(), speech.getHeight());
							speech.setText("<html>but little did u know <br>that i was actually quavo</html>");
						}
						else if(speech.getText().equals("<html>but little did u know <br>that i was actually quavo</html>"))
						{
							speech.setBounds((int)(quavo.getX()*0.95), (int)(quavo.getY()*1.2), speech.getWidth(), speech.getHeight());
							speech.setText("now send that test over here rn");
						}
						else if(speech.getText().equals("now send that test over here rn"))
						{
							speech.setBounds((int)(imad.getX()*1.05), (int)(height/2), speech.getWidth(), speech.getHeight());
							speech.setText("oh my bad mr.quavo");
						}
						else if(speech.getText().equals("oh my bad mr.quavo"))
						{
							SoundClipTest.main("audio/paper.wav");
							speech.setBounds((int)(quavo.getX()*0.95), (int)(quavo.getY()*1.2), speech.getWidth(), speech.getHeight());
							speech.setText("<html>nah nonathat u wastes ar going <br>   to principals office</html>");
						}
						else if(speech.getText().equals("<html>nah nonathat u wastes ar going <br>   to principals office</html>"))
						{
							music.stop();
							dispose();
							office.main(null);
						}
						

					}
				}
				else
				{
					interact.setVisible(false);
				}

			}
			@Override
			public void keyReleased(KeyEvent e) {
				clip.stop();
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {

					if(!stop) {
						mao.setIcon(maolefticon);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT){

					if(!stop) {
						mao.setIcon(maorighticon);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_UP){

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

		background.setIcon(backgroundicon);
		background.setBounds(0, 0, width, height);
		contentPane.add(background);
	}
	public void imadDelay() {
		ActionListener delay = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					imadMove();
				} catch (Exception e1) {

				}
			}	
		};
		Timer timer = new Timer(1000, delay);
		timer.setRepeats(false);
		timer.start();
	}

	public void imadMove() throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
		interact.setBounds((int)(interact.getX()*0.9), (int)(interact.getY()*0.8), interact.getWidth(), interact.getHeight());
		Image imadrunrightimg = toolkit.getImage("images/imadrunright.gif");
		tracker.addImage(imadrunrightimg, 0);
		tracker.waitForAll();
		imadrunrightimg = imadrunrightimg.getScaledInstance((int)(width/7.5), (int)(height/4), Image.SCALE_DEFAULT);
		ImageIcon imadrunrighticon = new ImageIcon(imadrunrightimg);

		Image imadrightimg = toolkit.getImage("images/imadrightsprite.png");
		tracker.addImage(imadrightimg, 0);
		tracker.waitForAll();
		imadrightimg = imadrightimg.getScaledInstance((int)(width/7.5), (int)(height/4), Image.SCALE_SMOOTH);
		ImageIcon imadrighticon = new ImageIcon(imadrightimg);

		File sound = new File("audio/walk.wav");
		AudioInputStream audio = AudioSystem.getAudioInputStream(sound);              
		Clip walk = AudioSystem.getClip();
		walk.open(audio);
		walk.start();

		ActionListener move = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//move down
					if(!(imad.getY() >= (int)(height*0.25)) && imad.getX() == (int)(width*-0.005))
					{
						imad.setIcon(imadrunrighticon);
						imad.setBounds(imad.getX(), (int)(imad.getY()+(imad.getHeight()*0.01)), imad.getWidth(), imad.getHeight());
					}
					//move right
					else if (imad.getX() <= (int)(width*0.65))
					{
						imad.setBounds((int)(imad.getX()+width*0.01), imad.getY(), imad.getWidth(), imad.getHeight());	
					}
					//move up
					else if((imad.getY() >= (int)(height*0.2)))
					{
						imad.setBounds(imad.getX(), (int)(imad.getY()-imad.getHeight()*0.01), imad.getWidth(), imad.getHeight());
					}
					//stop
					else
					{
						walk.stop();
						interact.setVisible(true);
						imad.setIcon(imadrighticon);
						return;
					}
				}
				catch(Exception e1)
				{
				}
			}	
		};
		Timer timer = new Timer(30, move);
		timer.setRepeats(true);
		timer.start();
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
