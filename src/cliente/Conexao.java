package cliente;

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
import utilidades.VerificarDigito;

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
				JOptionPane.showMessageDialog(null, "Imposs�vel conectar nesse endere�o ou porta, tente novamente");
			}	
		}
	}
	
	@Override
	public void run() {
		
		try {
			
			this.escutar();
			
		} catch (IOException e) {
			System.out.println("N�o foi poss�vel escutar");
			System.exit(0);
		}
	}
	
	private void initialize() { //Parametros da conex�o
		JLabel lbl = new JLabel("Dados para conex�o:");
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

		while (true) {
			if (bfr.ready()) {
				msg = bfr.readLine(); //Recebe a mensagem
				this.comunicador.comunicar(msg); //Envia para o cliente
			}
		}

	}
	
	public void enviar(String msg) {
		try {
			bfw.write(msg + "\r\n"); //Envia a mensagem
			bfw.flush();
		} catch (IOException e) {
			System.out.println("O outro usu�rio saiu do chat");
			System.exit(0);
		}
		
	}
	
	public String getNome() {
		return this.txtNome.getText();
	}
}
