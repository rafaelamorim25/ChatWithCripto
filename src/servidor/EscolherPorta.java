package servidor;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class EscolherPorta {
	
	public static Integer porta() {
		
		JLabel lblMessage = new JLabel("Porta do Servidor:");
		JTextField txtPorta = new JTextField("12345");
		Object[] texts = { lblMessage, txtPorta };
		JOptionPane.showMessageDialog(null, texts);
		
		return Integer.parseInt(txtPorta.getText());
	}

}
