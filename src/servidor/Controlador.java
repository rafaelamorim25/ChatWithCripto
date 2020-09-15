package servidor;

import java.io.IOException;

public class Controlador implements IComunicacao {

	private Usuario user1;
	private Usuario user2;

	public Controlador() {

	}

	public void addUser1(Usuario user) {
		user1 = user;
		user1.setComunicador(this);
	}

	public void addUser2(Usuario user) {
		user2 = user;
		user2.setComunicador(this);
	}

	@Override
	public void comunicar(String msg, Usuario user) {

		this.enviarMensagem(msg, user);

	}

	public void enviarMensagem(String msg, Usuario user) { //Envia de um usuario para o outro, a mensagem será encriptada novamente

		if (user.equals(user1)) {
			user2.enviarParaCliente(msg);
		} else {
			user1.enviarParaCliente(msg);
		}
	}

	@Override
	public void comunicar(String msg) throws IOException {

	}
}
