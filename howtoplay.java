package mao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class howtoplay extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					howtoplay frame = new howtoplay();
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
	 */
	public howtoplay() throws InterruptedException {
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

		Image downimg = toolkit.getImage("images/run.gif");
		tracker.addImage(downimg, 0);
		tracker.waitForAll();
		downimg = downimg.getScaledInstance(width/9, height/5, Image.SCALE_DEFAULT);
		ImageIcon downicon = new ImageIcon(downimg); 

		JLabel down = new JLabel("");
		down.setIcon(downicon);
		down.setBounds((int)(width*0.425), (int)(height*-0.15), width, height);
		contentPane.add(down);

		Image upimg = toolkit.getImage("images/run.gif");
		tracker.addImage(upimg, 0);
		tracker.waitForAll();
		upimg = upimg.getScaledInstance(width/9, height/5, Image.SCALE_DEFAULT);
		ImageIcon upicon = new ImageIcon(upimg); 

		JLabel up = new JLabel("");
		up.setIcon(upicon);
		up.setBounds((int)(width*0.321), (int)(height*-0.22), width, height);
		contentPane.add(up);

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
				SoundClipTest.main("audio/click.wav");
				dispose();
			}
		});
		back.setBounds((int)(width*0.825), (int)(height*0.9), width/9, height/12);
		contentPane.add(back);

		Image imadimg = toolkit.getImage("images/imad.png");
		tracker.addImage(imadimg, 0);
		tracker.waitForAll();
		imadimg = imadimg.getScaledInstance(width/8, height/4, Image.SCALE_SMOOTH);
		ImageIcon imadicon = new ImageIcon(imadimg); 

		JLabel imad = new JLabel("");
		imad.setIcon(imadicon);
		imad.setBounds((int)(width*0.875), (int)(height*0.375), width, height);
		contentPane.add(imad);

		Image swordimg = toolkit.getImage("images/sword.png");
		tracker.addImage(swordimg, 0);
		tracker.waitForAll();
		swordimg = swordimg.getScaledInstance(width/15, height/8, Image.SCALE_SMOOTH);
		ImageIcon swordicon = new ImageIcon(swordimg); 

		JLabel sword = new JLabel("");
		sword.setIcon(swordicon);
		sword.setBounds((int)(width*0.45), (int)(height*0.225), width, height);
		contentPane.add(sword);

		Image bagimg = toolkit.getImage("images/bag.png");
		tracker.addImage(bagimg, 0);
		tracker.waitForAll();
		bagimg = bagimg.getScaledInstance(width/12, height/6, Image.SCALE_SMOOTH);
		ImageIcon bagicon = new ImageIcon(bagimg);

		JLabel bag = new JLabel("");
		bag.setIcon(bagicon);
		bag.setBounds((int)(width*0.31), (int)(height*0.23), width, height);
		contentPane.add(bag);

		Image interactimg = toolkit.getImage("images/interact.png");
		tracker.addImage(interactimg, 0);
		tracker.waitForAll();
		interactimg = interactimg.getScaledInstance(width/9, height/5, Image.SCALE_SMOOTH);
		ImageIcon interacticon = new ImageIcon(interactimg);

		JLabel interact = new JLabel("");
		interact.setIcon(interacticon);
		interact.setBounds((int)(width*0.175), (int)(height*0.225), width, height);
		contentPane.add(interact);

		Image leftimg = toolkit.getImage("images/run.gif");
		tracker.addImage(leftimg, 0);
		tracker.waitForAll();
		leftimg = leftimg.getScaledInstance(width/9, height/5, Image.SCALE_DEFAULT);
		ImageIcon lefticon = new ImageIcon(leftimg);

		JLabel left = new JLabel("");
		left.setIcon(lefticon);
		left.setBounds((int)(width*0.075), (int)(height*-0.175), width, height);
		contentPane.add(left);

		Image rightimg = toolkit.getImage("images/runright.gif");
		tracker.addImage(rightimg, 0);
		tracker.waitForAll();
		rightimg = rightimg.getScaledInstance(width/9, height/5, Image.SCALE_DEFAULT);
		ImageIcon righticon = new ImageIcon(rightimg);

		JLabel right = new JLabel("");
		right.setIcon(righticon);
		right.setBounds((int)(width*0.2), (int)(height*-0.175), width, height);
		contentPane.add(right);

		Image attackimg = toolkit.getImage("images/attack.gif");
		tracker.addImage(attackimg, 0);
		tracker.waitForAll();
		attackimg = attackimg.getScaledInstance(width/9, height/5, Image.SCALE_DEFAULT);
		ImageIcon attackicon = new ImageIcon(attackimg);

		JLabel attack = new JLabel("");
		attack.setIcon(attackicon);
		attack.setBounds((int)(width*0.05), (int)(height*0.225), width, height);
		contentPane.add(attack);

		Image howtoplayimg = toolkit.getImage("images/howtoplay1.png");
		tracker.addImage(howtoplayimg, 0);
		tracker.waitForAll();
		howtoplayimg = howtoplayimg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon howtoplayicon = new ImageIcon(howtoplayimg);

		JLabel howtoplay = new JLabel("");
		howtoplay.setIcon(howtoplayicon);
		howtoplay.setBounds(0, 0, width, height);
		contentPane.add(howtoplay);

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

}
