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
import java.io.File;
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
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class school extends JFrame {

	private JPanel contentPane;
	private int width;
	private int height;
	private boolean imadconvo = false;
	private boolean imadconvo2 = false;
	private boolean michelconvo = false;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					school frame = new school();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public school() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
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
		System.out.println(volume);
		in.reset();

		File soundFile = new File("audio/walk.wav");
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);              
		Clip clip = AudioSystem.getClip();
		clip.open(audioIn);

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

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		MediaTracker tracker = new MediaTracker(this);

		JLabel speech = new JLabel("");
		speech.setForeground(Color.WHITE);
		speech.setFont(new Font("Comic Sans MS", Font.BOLD, (int)(height*0.02)));
		speech.setBounds(0, 0, width/3, (int)(height/10));
		contentPane.add(speech);
		speech.setVisible(false);

		JLabel speech2 = new JLabel("");
		speech2.setForeground(Color.WHITE);
		speech2.setFont(new Font("Comic Sans MS", Font.BOLD, (int)(height*0.02)));
		speech2.setBounds(0, 0, width/3, (int)(height/10));
		contentPane.add(speech2);
		speech2.setVisible(false);

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

		JButton mao = new JButton("");
		mao.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.print("x:" + mao.getX());
				System.out.print(" y:" + mao.getY());
				System.out.print(" w:" + (double)mao.getX()/width);
				System.out.print(" h:" + (double)mao.getY()/height);
			}
		});
		mao.setBorderPainted(false); 
		mao.setContentAreaFilled(false); 
		mao.setFocusPainted(false); 
		mao.setOpaque(false);
		mao.setBounds((int)(width*0.01), (int)(height*0.6),(int)(width/9), height/4);

		mao.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {


				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					clip.start();
					clip.loop(Clip.LOOP_CONTINUOUSLY);
					speech.setVisible(false);
					mao.setIcon(runlefticon);

					if(mao.getX() < -200)
					{
						clip.stop();
						humberview.main(null);
						delay(1000);
						dispose();
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
					//imad
					if(mao.getY()<= (int)(height*0.38) && mao.getX() > width*0.275 && mao.getX() < width*0.3)
					{
						mao.setBounds(mao.getX(),mao.getY(), mao.getWidth(), mao.getHeight());
					}
					//michael
					else if(mao.getY()<= (int)(height*0.38) && mao.getX() > width*0.625 && mao.getX() <= width*0.675)
					{
						mao.setBounds(mao.getX(),mao.getY(), mao.getWidth(), mao.getHeight());
					}
					else if(mao.getX() > (int)(width*0.95))
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
					if(mao.getY() <= (int)(height*0.38))
					{
						mao.setBounds(mao.getX(), (mao.getY()), mao.getWidth(), mao.getHeight());
					}
					else
					{
						mao.setBounds(mao.getX(), (int)(mao.getY()-height*0.02), mao.getWidth(), mao.getHeight());
					}

				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN){
					clip.start();
					clip.loop(Clip.LOOP_CONTINUOUSLY);
					speech.setVisible(false);
					if(mao.getIcon().equals(maolefticon))
					{
						mao.setIcon(runlefticon);
					}
					if(mao.getIcon().equals(maorighticon))
					{
						mao.setIcon(runrighticon);
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
				if(e.getKeyCode() == KeyEvent.VK_Z && interact.isVisible())
				{
					//imad convo
					SoundClipTest.main("audio/click.wav");
					if(!speech.isVisible() && mao.getX() > width*0.275 && mao.getX() < width*0.3 && michelconvo == false)
					{
						speech.setForeground(Color.BLACK);
						speech.setVisible(true);
						speech.setBounds((int)(mao.getX()*1.4), (int)(mao.getY()*0.8), speech.getWidth(), speech.getHeight());
						speech.setText("ahlie");
					}
					else if(speech.getText().equals("ahlie"))
					{
						speech.setForeground(Color.WHITE);
						speech.setText("ya eh");
						speech.setBounds((int)(mao.getX()*1.15), (int)(mao.getY()*0.8), speech.getWidth(), speech.getHeight());
					}
					else if(speech.getText().equals("ya eh"))
					{
						speech.setForeground(Color.BLACK);
						speech.setText("are you ready for the test");
						speech.setBounds((int)(mao.getX()*1.3), (int)(mao.getY()*0.8), speech.getWidth(), speech.getHeight());
					}
					else if(speech.getText().equals("are you ready for the test"))
					{
						speech.setForeground(Color.WHITE);
						speech.setText("nah i did no studying");
						speech.setBounds((int)(mao.getX()*1.05), (int)(mao.getY()*0.8), speech.getWidth(), speech.getHeight());
					}
					else if(speech.getText().equals("nah i did no studying"))
					{
						speech.setForeground(Color.BLACK);
						speech.setText("ahli same im acshe done");
						speech.setBounds((int)(mao.getX()*1.3), (int)(mao.getY()*0.8), speech.getWidth(), speech.getHeight());
					}
					else if(speech.getText().equals("ahli same im acshe done"))
					{
						speech.setForeground(Color.BLACK);
						speech.setText("ask mons for the test from last year");
						speech.setBounds((int)(mao.getX()*1.2), (int)(mao.getY()*0.8), speech.getWidth(), speech.getHeight());
					}
					else if(speech.getText().equals("ask mons for the test from last year"))
					{
						speech.setText("");
						speech.setVisible(false);
						interact.setVisible(false);
						imadconvo = true;
					}
					//if talked to michel
					else if(!speech.isVisible() && mao.getX() > width*0.275 && mao.getX() < width*0.3 && michelconvo)
					{
						speech.setForeground(Color.BLACK);
						speech.setVisible(true);
						speech.setBounds((int)(mao.getX()*1.3), (int)(mao.getY()*0.8), speech.getWidth(), speech.getHeight());
						speech.setText("wat did mons say");
					}
					else if(speech.getText().equals("wat did mons say"))
					{
						speech.setForeground(Color.WHITE);
						speech.setBounds((int)(mao.getX()), (int)(mao.getY()*0.8), speech.getWidth(), speech.getHeight());
						speech.setText("the test is on the teachers desk");
					}
					else if(speech.getText().equals("the test is on the teachers desk"))
					{
						speech.setForeground(Color.BLACK);
						speech.setVisible(true);
						speech.setBounds((int)(mao.getX()*1.4), (int)(mao.getY()*0.8), speech.getWidth(), speech.getHeight());
						speech.setText("steal it for me pleas bro");
					}
					else if(speech.getText().equals("steal it for me pleas bro"))
					{
						speech.setForeground(Color.WHITE);
						speech.setBounds((int)(mao.getX()*1.3), (int)(mao.getY()*0.8), speech.getWidth(), speech.getHeight());
						speech.setText("ahli");
					}
					else if(speech.getText().equals("ahli"))
					{
						imadconvo2 = true;
						speech.setVisible(false);
						speech.setText("");
					}

					//michel convo
					if(!speech.isVisible() && mao.getX() > width*0.625 && mao.getX() <= width*0.675 && imadconvo == false)
					{
						speech.setVisible(true);
						speech.setBounds((int)(mao.getX()*1.05), (int)(mao.getY()*0.85), speech.getWidth(), speech.getHeight());
						speech.setText("i missed the green team meeting this morning");
					}
					else if(speech.getText().equals("i missed the green team meeting this morning"))
					{
						speech.setBounds(mao.getX(), (int)(mao.getY()*0.85), speech.getWidth(), speech.getHeight());
						speech.setText("nah its a lighter issue");
					}
					else if(speech.getText().equals("nah its a lighter issue"))
					{
						speech.setBounds((int)(mao.getX()*1.175), (int)(mao.getY()*0.85), speech.getWidth(), speech.getHeight());
						speech.setText("xD");
					}
					else if(speech.getText().equals("xD"))
					{
						speech.setBounds((int)(mao.getX()*1.05), (int)(mao.getY()*0.85), speech.getWidth(), speech.getHeight());
						speech.setText("ur acshe black");
					}
					else if(speech.getText().equals("ur acshe black"))
					{
						speech.setVisible(false);
						speech.setText("");
					}
					//if talked to imad already
					else if(!speech.isVisible() && mao.getX() > width*0.625 && mao.getX() <= width*0.675 && imadconvo)
					{
						speech.setVisible(true);
						speech.setBounds((int)(mao.getX()*1.05), (int)(mao.getY()*0.85), speech.getWidth(), speech.getHeight());
						speech.setText("do you have last years test");
					}
					else if(speech.getText().equals("do you have last years test"))
					{
						speech.setBounds((int)(mao.getX()*1.105), (int)(mao.getY()*0.85), speech.getWidth(), speech.getHeight());
						speech.setText("nah but the test is on the teachers desk");
					}
					else if(speech.getText().equals(("nah but the test is on the teachers desk")))
					{
						speech.setBounds((int)(mao.getX()), (int)(mao.getY()*0.85), speech.getWidth(), speech.getHeight());
						speech.setText("k im teifing bare");
					}
					else if(speech.getText().equals("k im teifing bare"))
					{
						speech.setBounds((int)(mao.getX()*1.175), (int)(mao.getY()*0.85), speech.getWidth(), speech.getHeight());
						speech.setText("ah ur dom");
					}
					else if(speech.getText().equals("ah ur dom"))
					{
						speech.setVisible(false);
						speech.setText("");
						michelconvo = true;
					}


				}
				//michel
				if((e.getKeyCode() == KeyEvent.VK_RIGHT) && mao.getIcon().equals(maorighticon) || mao.getIcon().equals(runrighticon) && mao.getX() > width*0.625 && mao.getX() < (int)(width*0.675) && mao.getY()<= (int)(height*0.38))
				{
					interact.setVisible(true);
					interact.setBounds((int)(width*0.6), (int)(height*0.2), interact.getWidth(), interact.getHeight());


				}
				//imad
				else if((e.getKeyCode() == KeyEvent.VK_RIGHT) && mao.getIcon().equals(maorighticon) || mao.getIcon().equals(runrighticon) && mao.getX() > width*0.275 && mao.getX() < (int)(width*0.3) && mao.getY()<= (int)(height*0.38))
				{
					interact.setVisible(true);
					interact.setBounds((int)(width*0.25), (int)(height*0.2), interact.getWidth(), interact.getHeight());

				}
				//door
				else if(mao.getX() > (int)(width*0.4) && mao.getX() < (int)(width*0.5) && (mao.getY() <= (int)(height*0.38)) && imadconvo2 && michelconvo)
				{
					interact.setVisible(true);
					interact.setBounds((int)(width*0.4), (int)(height*0.25), interact.getWidth(), interact.getHeight());
					if(e.getKeyCode() == KeyEvent.VK_Z)
					{
						dispose();
						classroom.main(null);
					}

				}

				else if(speech.isVisible())
				{
					interact.setVisible(true);
				}
				else
				{
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

		mao.setIcon(maorighticon);
		contentPane.add(mao);


		Image mitcelimg = toolkit.getImage("images/musicmitcel.png");
		tracker.addImage(mitcelimg, 0);
		tracker.waitForAll();
		mitcelimg = mitcelimg.getScaledInstance((int)(width/7.5), (int)(height/4), Image.SCALE_SMOOTH);
		ImageIcon mitcelicon = new ImageIcon(mitcelimg);

		JLabel mitcel = new JLabel("");
		mitcel.setIcon(mitcelicon);
		mitcel.setBounds((int)(width*0.7), (int)(height*0.01), width, height);
		contentPane.add(mitcel);

		Image imadimg = toolkit.getImage("images/imadsprite.png");
		tracker.addImage(imadimg, 0);
		tracker.waitForAll();
		imadimg = imadimg.getScaledInstance((int)(width/7.5), (int)(height/4), Image.SCALE_SMOOTH);
		ImageIcon imadicon = new ImageIcon(imadimg);

		JLabel imad = new JLabel("");
		imad.setIcon(imadicon);
		imad.setBounds((int)(width*0.35), (int)(height*0.01), width, height);
		contentPane.add(imad);

		Image backgroundimg = toolkit.getImage("images/school.png");
		tracker.addImage(backgroundimg, 0);
		tracker.waitForAll();
		backgroundimg = backgroundimg.getScaledInstance(width,height, Image.SCALE_SMOOTH);
		ImageIcon backgroundicon = new ImageIcon(backgroundimg);

		JLabel background = new JLabel("");
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
