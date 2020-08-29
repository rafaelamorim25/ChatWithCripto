package cliente;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class JanelaMultiPlayerOnline extends JFrame {

	private static final long serialVersionUID = 1L;
	Cliente cliente;
	private PainelChat chat;

	public JanelaMultiPlayerOnline(Cliente cliente) {
		super("Chat");
		this.cliente = cliente;
		init();
	}
	
	private void init() {
		this.setBounds(100, 100, 300, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		chat = new PainelChat(cliente);
		getContentPane().add(chat, BorderLayout.CENTER);
	
		this.setVisible(true);
	}
	
	public PainelChat getPainelChat() {
		return this.chat;
	}
}
