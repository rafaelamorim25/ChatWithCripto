package cliente;

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

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import servidor.IComunicacao;

public class Conexao  implements Runnable {
	
	private JTextField txtIP;
	private JTextField txtPorta;
	private JTextField txtNome;
	private Socket socket;
	private OutputStream ou;
	private Writer ouw;
	private BufferedWriter bfw;
	private IComunicacao comunicador;
	
	public Conexao(IComunicacao iComunicador) throws IOException {
		comunicador = iComunicador;
		
		boolean naoConectou = true;
		
		while(naoConectou) {
			try {
				this.initialize();
				this.conectar();
				naoConectou = false;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Impossível conectar nesse endereço ou porta, tente novamente");
			}	
		}
	}
	
	private void initialize() {
		JLabel lbl = new JLabel("Dados para conexão:");
		txtIP = new JTextField("127.0.0.1");
		txtPorta = new JTextField("12345");
		txtPorta.addKeyListener(new VerificarDigito());
		txtNome = new JTextField("Cliente");
		
		Object[] texts = {lbl, txtIP, txtPorta, txtNome};
		JOptionPane.showMessageDialog(null, texts);
	}
	
	private void conectar() throws IOException {

		socket = new Socket(txtIP.getText(), Integer.parseInt(txtPorta.getText()));
		ou = socket.getOutputStream();
		ouw = new OutputStreamWriter(ou);
		bfw = new BufferedWriter(ouw);
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
				
				this.comunicador.comunicar(msg);	
			}
	}
	
	public void enviar(String msg) {
		try {
			bfw.write(msg + "\r\n");
			bfw.flush();
		} catch (IOException e) {
			System.out.println("naoEnviou");
		}
		
	}
	
	public String getNome() {
		return this.txtNome.getText();
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
		
		public void keyPressed(KeyEvent evento) {}
		
		public void keyReleased(KeyEvent arg0) {}
	}

	@Override
	public void run() {
		
		try {
			this.escutar();
		} catch (IOException e) {
			System.out.println("deu ruim aqui");
			e.printStackTrace();
		}
	}
}
