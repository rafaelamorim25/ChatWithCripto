package cliente;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class MinhaJanela extends JFrame {

	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private String caminhoArquivo, nomeArquivo;
	
	public MinhaJanela() {
		super("Se liga");
		caminhoArquivo = "Icone";
		nomeArquivo = "ligaIcon.png";
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setIcone();
	}
	
private void setIcone () {
		
		try {
			image = ImageIO.read(new File("Icone\\ligaIcon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setIconImage(image);
	}

	public String getCaminhoArquivo() {
		return this.caminhoArquivo;
	}
	
	public static String getCaminhoAplicacao() {
		try {
			return new java.io.File(".").getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


}
