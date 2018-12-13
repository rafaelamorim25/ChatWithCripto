package cliente;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PainelTabuleiro extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JButton[][] botoes;
	private JButton[] colunas;
	private Cliente cliente;
	
	public PainelTabuleiro (Cliente cliente) {
		this.cliente = cliente;
		init();
	}
	
	private void init() {
		this.setLayout(new GridLayout(7, 7, 0, 0));
		
		this.colunas = new JButton[7];
		
		for(int i = 0; i < 7; i++) {
			colunas[i] = new JButton("V");
			colunas[i].setFont(new Font(null, Font.PLAIN, 8));
			colunas[i].addActionListener(new ActionPlay(i));
			this.add(colunas[i]);
		}
		
		
		botoes = new JButton[6][7];
		
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				botoes[i][j] = new JButton();
				botoes[i][j].setBackground(new Color(224, 224, 224, 224));
				botoes[i][j].setEnabled(false);
				this.add(botoes[i][j]);
			}
		}
	}
	
	public void travar(boolean status) {
		for(int i = 0;i < 7;i++) {
			colunas[i].setEnabled(status);
		}
	}
	
	public void pintar(int x, int y, Color c) {
		botoes[x][y].setBackground(c);
	}
	
	public void limpar() {
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				botoes[i][j].setBackground(new Color(224, 224, 224, 224));
			}
		}
	}
	
	private class ActionPlay implements ActionListener{

		int coluna;
		
		public ActionPlay(int coluna) {
			this.coluna = coluna + 1;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			cliente.enviarJogada(coluna);
		}
	}	
}
