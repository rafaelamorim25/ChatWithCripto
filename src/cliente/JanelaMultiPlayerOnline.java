package cliente;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class JanelaMultiPlayerOnline extends MinhaJanela {

	private static final long serialVersionUID = 1L;
	Cliente cliente;

	public JanelaMultiPlayerOnline(Cliente cliente) {
		super();
		init();
		this.cliente = cliente;
	}
	
	private void init() {
		this.setBounds(100, 100, 575, 338);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel painelEsquerdo = new JPanel();
		getContentPane().add(painelEsquerdo);
		painelEsquerdo.setLayout(new BorderLayout(0, 0));
		
		JPanel painelNorte = new JPanel();
		painelEsquerdo.add(painelNorte, BorderLayout.NORTH);
		painelNorte.setBorder(new TitledBorder(null, "LIGA 4", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		
		PainelTabuleiro tab = new PainelTabuleiro(cliente);
		painelEsquerdo.add(tab, BorderLayout.CENTER);
		
		PainelChat chat = new PainelChat(cliente);
		getContentPane().add(chat);
	
		this.setVisible(true);
	}

}
