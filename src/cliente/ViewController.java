package cliente;

import java.io.IOException;

public class ViewController {
	
	private JanelaMultiPlayerOnline telaPrincipal;
	private Cliente cliente;
	private MinhaJanela telaExibindo = null;
	
	public ViewController() {
		try {
			cliente = new Cliente();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.telaPrincipal = new JanelaMultiPlayerOnline(cliente);
		
	}
	
	public void fecharTelaExibindo() {
		if(telaExibindo!=null) {
			telaExibindo.dispose();
		}
	}
	
	public void exibirTelaPrincipal() {
		this.fecharTelaExibindo();
		telaExibindo = telaPrincipal;
		telaExibindo.setVisible(true);
	}
	
	public static void main(String[] args) {
		ViewController v = new ViewController();
		v.exibirTelaPrincipal();
	}

}
