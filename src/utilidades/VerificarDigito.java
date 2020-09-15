package utilidades;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

public class VerificarDigito implements KeyListener {

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
