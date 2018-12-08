package cliente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class PainelChat extends JPanel {

	private static final long serialVersionUID = 1L;
	private Cliente cliente;
	private JTextArea texto;
	private JTextField txtMsg;
	
	public PainelChat(Cliente cliente) {
		this.cliente = cliente;
		this.init();
	}
	
	private void init() {
		
		this.setLayout(new BorderLayout(0, 0));
		this.setBackground(Color.LIGHT_GRAY);
		
		JPanel painelN = new JPanel();
		painelN.setBorder(new TitledBorder(null, "CHAT", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		this.add(painelN, BorderLayout.NORTH);
		
		JPanel painelC = new JPanel();
		this.add(painelC, BorderLayout.CENTER);
		painelC.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblHistorico = new JLabel("Histórico");
		painelC.add(lblHistorico);
		texto = new JTextArea(10, 20);
		texto.setEditable(false);
		texto.setBackground(new Color(240, 240, 240));
		JScrollPane scroll = new JScrollPane(texto);
		painelC.add(scroll);
		texto.setLineWrap(true);
		texto.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE));
		
		JPanel painelS = new JPanel();
		this.add(painelS, BorderLayout.SOUTH);
		painelS.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel painelSulN = new JPanel();
		painelS.add(painelSulN);
		txtMsg = new JTextField(20);
		txtMsg.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE));
		painelSulN.setLayout(new FlowLayout());
		painelSulN.add(txtMsg);
		
		
		JPanel painelSulS = new JPanel();
		painelS.add(painelSulS);
		JButton btnSend = new JButton("Enviar");
		txtMsg.addKeyListener(new ActionEnviarEnter());
		painelSulS.add(btnSend);
		btnSend.addActionListener(new ActionEnviar());
	}
	
	public void mostrarMensagem(String msg) {
		texto.append(msg + "\n");
	}
	
	public void enviar () {
		cliente.enviarMensagem(txtMsg.getText());
		txtMsg.setText("");
	}
	
	private class ActionEnviar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			enviar();
		}
	}
	
	private class ActionEnviarEnter implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {}
		
		@Override
		public void keyReleased(KeyEvent e) {}
		
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				enviar();
			}	
		}
	}
}
