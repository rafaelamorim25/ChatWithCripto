package cliente;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.JRadioButton;

public class JanelaInicial {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaInicial window = new JanelaInicial();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JanelaInicial() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 213, 294);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel painelSul = new JPanel();
		frame.getContentPane().add(painelSul, BorderLayout.SOUTH);
		
		JButton btnPlay = new JButton("Play");
		painelSul.add(btnPlay);
		
		JPanel painelNorte = new JPanel();
		painelNorte.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "LIG 4", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		frame.getContentPane().add(painelNorte, BorderLayout.NORTH);
		
		JPanel painelCentral = new JPanel();
		painelCentral.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		painelCentral.setAlignmentX(Component.RIGHT_ALIGNMENT);
		FlowLayout flowLayout = (FlowLayout) painelCentral.getLayout();
		flowLayout.setVgap(30);
		flowLayout.setHgap(20);
		frame.getContentPane().add(painelCentral, BorderLayout.CENTER);
		
		ButtonGroup bg = new ButtonGroup();
		
		JRadioButton rdbtnMultiPlayerOnline = new JRadioButton("Multi Player Online");
		bg.add(rdbtnMultiPlayerOnline);
		painelCentral.add(rdbtnMultiPlayerOnline);
		
		JRadioButton rdbtnMultiPlayerLocal = new JRadioButton("Multi Player Local");
		bg.add(rdbtnMultiPlayerLocal);
		painelCentral.add(rdbtnMultiPlayerLocal);
		
		JRadioButton rdbtnSinglePlayer = new JRadioButton("Single Player");
		bg.add(rdbtnSinglePlayer);
		painelCentral.add(rdbtnSinglePlayer);
	}

}
