package mao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class test extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test frame = new test();
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
	public test() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, width-1, height-1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JButton button = new JButton("");
		button.setIcon(new ImageIcon("images/run.gif"));
		button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
	            if (e.getKeyCode() == KeyEvent.VK_A) {
	            	button.setIcon(new ImageIcon("images/run.gif"));
	            	button.setBounds(button.getX()-20, button.getY(), button.getWidth(), button.getHeight());
	            }
	            if (e.getKeyCode() == KeyEvent.VK_B) {
	            	dispose();
	            }
	            if (e.getKeyCode() == KeyEvent.VK_D){
	            	button.setIcon(new ImageIcon("images/runright.gif"));
	            	button.setBounds(button.getX()+20, button.getY(), button.getWidth(), button.getHeight());
	            }
	            if (e.getKeyCode() == KeyEvent.VK_W) {
	            	button.setBounds(button.getX(), button.getY()-20, button.getWidth(), button.getHeight());
	            }
	            if (e.getKeyCode() == KeyEvent.VK_S){
	            	button.setBounds(button.getX(), button.getY()+20, button.getWidth(), button.getHeight());
	            }
	            if (e.getKeyCode() == KeyEvent.VK_C)
	            {
	            }
	            if(e.getKeyCode() == KeyEvent.VK_Z)
	            {
	            	button.setIcon(new ImageIcon("images/attack.gif"));
	            }
			}
		});
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		button.setBounds(330, 255, 145, 209);
		contentPane.add(button);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		setVisible(true);

	}
}
