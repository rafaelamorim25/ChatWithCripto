package cliente;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class JanelaMultiPlayerOnline extends MinhaJanela {

	private static final long serialVersionUID = 1L;
	Cliente cliente;
	
	private PainelTabuleiro tab;
	private PainelChat chat;

	public JanelaMultiPlayerOnline(Cliente cliente) {
		super();
		this.cliente = cliente;
		init();
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
		
		tab = new PainelTabuleiro(cliente);
		painelEsquerdo.add(tab, BorderLayout.CENTER);
		
		chat = new PainelChat(cliente);
		getContentPane().add(chat);
	
		this.setVisible(true);
	}
	
	public PainelTabuleiro getPainelTabuleiro() {
		return this.tab;
	}
	
	public PainelChat getPainelChat() {
		return this.chat;
	}
}
