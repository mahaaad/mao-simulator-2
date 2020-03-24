package mao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class checkers extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					checkers frame = new checkers();
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

	public checkers() {
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		int row = 8;
		int col = 8;
		panel.setLayout(new GridLayout(row, col));

		JLabel[][] grid= new JLabel[row][col];
		for (int i = 0; i < row; i++){
			for (int j = 0; j < col; j++){
				grid[i][j] = new JLabel();
				grid[i][j].setBorder(new LineBorder(Color.BLACK));
				if(((int)(i%2) == 0 && (int)(j%2) == 0) || ((int)(i%2) != 0 && (int)(j%2) != 0))
				{
				grid[i][j].setBackground(Color.black);
				}
				else
				{
					grid[i][j].setBackground(Color.red);	
				}
				grid[i][j].setOpaque(true);
				panel.add(grid[i][j]);
			}
		}
	}
}