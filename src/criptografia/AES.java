package criptografia;

import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

	public static void main(String[] args) {

		String texto = "Olá vamos encriptar !";

		try {

			System.out.println("Texto: " + texto);

			AESKey key = generateKey();

			String textoEncriptado = encrypt(texto, key);

			System.out.println("Texto encriptado: " + textoEncriptado);

			String textoDesencriptado = decrypt(textoEncriptado, key);

			System.out.println("Texto desencriptado: " + textoDesencriptado);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String encrypt(String textopuro, AESKey key) throws Exception {
		Cipher encripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getKey().getBytes("UTF-8"), "AES");
		encripta.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(key.getIv().getBytes("UTF-8")));
		return converteByteArrayToHexString(encripta.doFinal(textopuro.getBytes("UTF-8")));
	}

	public static String decrypt(String textoencriptado, AESKey key) throws Exception {
		Cipher decripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getKey().getBytes("UTF-8"), "AES");
		decripta.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(key.getIv().getBytes("UTF-8")));
		return new String(decripta.doFinal(converteHexStringToByteArray(textoencriptado)), "UTF-8");
	}

	public static AESKey generateKey() {

		return new AESKey(generateHexString(), generateHexString());
	}

	public static String generateHexString() {

		String[] symbols = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
		int length = 16;
		StringBuilder key = new StringBuilder(length);

		try {

			Random random = SecureRandom.getInstanceStrong(); // as of JDK 8, this should return the strongest algorithm
																// available to the JVM
			for (int i = 0; i < length; i++) {
				int indexRandom = random.nextInt(symbols.length);
				key.append(symbols[indexRandom]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return key.toString();
	}

	public static byte[] converteHexStringToByteArray(String s) {
		int tamanho = s.length() / 2; // tamanho do array de bytes, considerando que cada 2 caracteres da string
										// representam um byte em hexa
		byte[] array = new byte[tamanho];
		for (int i = 0; i < tamanho; i++) {
			String chr = s.substring(i * 2, i * 2 + 2); // para podermos pegar de 2 em 2 caracteres
			Integer valor = Integer.parseInt(chr, 16);
			array[i] = valor.byteValue(); // coloca o byte convertido
		}
		return array;
	}

	public static String converteByteArrayToHexString(byte[] a) {
		char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		StringBuffer buf = new StringBuffer();
		for (int j = 0; j < a.length; j++) {
			buf.append(hexDigit[(a[j] >> 4) & 0x0f]);
			buf.append(hexDigit[a[j] & 0x0f]);
		}
		return buf.toString();
	}

}
