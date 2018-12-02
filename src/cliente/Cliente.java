package cliente;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;

public class Cliente extends JFrame implements ActionListener, KeyListener {

	private JTextField textField;
	private Socket socket;
	private OutputStream ou;
	private Writer ouw;
	private BufferedWriter bfw;
	private JButton[][] botoes;
	private JButton btnJogar;
	private JTextArea texto;
	private JTextField txtMsg;
	private JButton btnSend;
	private JButton btnSair;
	private JLabel lblHistorico;
	private JLabel lblMsg;
	private JPanel painelDireito;
	private JTextField txtIP;
	private JTextField txtPorta;
	private JTextField txtNome;
	private JPanel painelN;
	private JPanel painelC;
	private JPanel painelS;
	private JPanel painelSulN;
	private JPanel painelSulS;

	public static void main(String[] args) throws IOException {
		Cliente app = new Cliente();
		app.conectar();
		app.escutar();
	}

	public Cliente() throws IOException {
		initialize();
	}

	private void initialize() {
		this.setBounds(100, 100, 575, 338);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel painelEsquerdo = new JPanel();
		getContentPane().add(painelEsquerdo);
		painelEsquerdo.setLayout(new BorderLayout(0, 0));
		
		JPanel painelNorte = new JPanel();
		painelEsquerdo.add(painelNorte, BorderLayout.NORTH);
		painelNorte.setBorder(new TitledBorder(null, "LIGA 4", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		
		JPanel painelSul = new JPanel();
		painelEsquerdo.add(painelSul, BorderLayout.SOUTH);
		
		JLabel lbl = new JLabel("Coluna:");
		painelSul.add(lbl);
		
		textField = new JTextField();
		textField.addKeyListener(new VerificarDigito());
		painelSul.add(textField);
		textField.setColumns(10);
		
		btnJogar = new JButton("Jogar");
		btnJogar.addActionListener(this);
		painelSul.add(btnJogar);
		
		JPanel painelCentral = new JPanel();
		painelEsquerdo.add(painelCentral, BorderLayout.CENTER);
		painelCentral.setLayout(new GridLayout(6, 7, 0, 0));
		
		botoes = new JButton[6][7];
		
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				botoes[i][j] = new JButton();
				botoes[i][j].setBackground(new Color(224, 224, 224, 224));
				botoes[i][j].setEnabled(false);
				painelCentral.add(botoes[i][j]);
			}
		}
		
		painelDireito = new JPanel();
		getContentPane().add(painelDireito);
		painelDireito.setLayout(new BorderLayout(0, 0));
		painelDireito.setBackground(Color.LIGHT_GRAY);
		
		painelN = new JPanel();
		painelN.setBorder(new TitledBorder(null, "CHAT", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		painelDireito.add(painelN, BorderLayout.NORTH);
		
		painelC = new JPanel();
		painelDireito.add(painelC, BorderLayout.CENTER);
		painelC.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblHistorico = new JLabel("Histórico");
		painelC.add(lblHistorico);
		texto = new JTextArea(10, 20);
		texto.setEditable(true);
		texto.setBackground(new Color(240, 240, 240));
		JScrollPane scroll = new JScrollPane(texto);
		painelC.add(scroll);
		texto.setLineWrap(true);
		texto.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE));
		
		
		painelS = new JPanel();
		painelDireito.add(painelS, BorderLayout.SOUTH);
		painelS.setLayout(new GridLayout(0, 1, 0, 0));
		
		painelSulN = new JPanel();
		painelS.add(painelSulN);
		lblMsg = new JLabel("Mensagem");
		txtMsg = new JTextField(20);
		txtMsg.addKeyListener(this);
		txtMsg.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE));
		painelSulN.setLayout(new BoxLayout(painelSulN, BoxLayout.X_AXIS));
		painelSulN.add(lblMsg);
		painelSulN.add(txtMsg);
		
		painelSulS = new JPanel();
		painelS.add(painelSulS);
		btnSair = new JButton("Sair");
		painelSulS.add(btnSair);
		btnSair.setToolTipText("Sair do Chat");
		btnSend = new JButton("Enviar");
		painelSulS.add(btnSend);
		btnSend.setToolTipText("Enviar Mensagem");
		btnSend.addActionListener(this);
		btnSend.addKeyListener(this);
		btnSair.addActionListener(this);
		
		this.setVisible(true);
	}
	
	public void conectar() throws IOException {

		socket = new Socket("127.0.0.1", 12345);
		ou = socket.getOutputStream();
		ouw = new OutputStreamWriter(ou);
		bfw = new BufferedWriter(ouw);
	}

	public void enviarJogada(String msg) throws IOException {

		if (msg.equals("Sair")) {
			bfw.write("Desconectado \r\n");
		} else {
			System.out.println("enviou: "+ msg);
			bfw.write(msg + "\r\n");
		}
		bfw.flush();
		textField.setText("");
	}
	
	public void escutar() throws IOException {

		InputStream in = socket.getInputStream();
		InputStreamReader inr = new InputStreamReader(in);
		BufferedReader bfr = new BufferedReader(inr);
		String msg = "";

		while (!"Sair".equalsIgnoreCase(msg))

			if (bfr.ready()) {
				msg = bfr.readLine();
				System.out.println("Chegou no cliente: "+ msg);
				
				controle(msg);
					
			}
	}
	
	public void controle(String msg) {
		
		if(msg.contains("Pinte: ")) {
			realizarJogada(msg);
		}else {
			JOptionPane.showMessageDialog(null, msg);
		}
	}
	
	public void realizarJogada(String msg) {
		Color c;
		String jogada[] = msg.split(" ");
		
		c = (Integer.parseInt(jogada[3])==0) ? Color.blue : Color.red;
		
		Boolean travei = (Integer.parseInt(jogada[4])==1) ? false : true;
		
		btnJogar.setEnabled(travei);
		
		botoes[Integer.parseInt(jogada[1])][Integer.parseInt(jogada[2])].setBackground(c);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			if (e.getActionCommand().equals(btnJogar.getActionCommand()))
				this.enviarJogada("Jogada: " + textField.getText());
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	protected class VerificarDigito implements KeyListener {

		boolean backspace;

		public VerificarDigito(){
			backspace = false;
		}

		public void keyTyped(KeyEvent evento){
			char letra;
			letra = evento.getKeyChar();

			if (!backspace) {
				if ( (letra < '0') || (letra > '9')) {
					evento.consume();
					JOptionPane.showMessageDialog(null,"Somente dígitos");
				}
			}else{
				backspace = false;
			}
		}
		
		public void keyPressed(KeyEvent evento) {
			if (evento.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				backspace = true;
			}
		}
		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
