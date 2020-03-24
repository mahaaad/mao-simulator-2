package mao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class multiplayer extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					multiplayer frame = new multiplayer();
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
	public multiplayer() throws InterruptedException {
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
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		MediaTracker tracker = new MediaTracker(this);
		
		Image backgroundimg = toolkit.getImage("images/xd.png");
		tracker.addImage(backgroundimg, 0);
		tracker.waitForAll();
		backgroundimg = backgroundimg.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		ImageIcon backgroundicon = new ImageIcon(backgroundimg);

		JLabel background = new JLabel("");
		background.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				SoundClipTest.main("audio/click.wav");
				dispose();
			}
		});
		background.setIcon(backgroundicon);
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
