import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;

public class FileDESedeTest {

	/**
	 * @param content
	 * @throws Exception
	 */
	public static void encrypt() throws Exception {
		byte[] key = "000000000000000000000000".getBytes();
		byte[] ivs = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		
		String DES_ECB = "DESede/CBC/PKCS5Padding";
		Cipher cipher = Cipher.getInstance(DES_ECB);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey secretKey = keyFactory.generateSecret(new DESedeKeySpec(key));
		IvParameterSpec iv = new IvParameterSpec(ivs);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		CipherInputStream input = new CipherInputStream(new FileInputStream(new File("/root/3des_mw.txt")), cipher);
		Base64OutputStream output = new Base64OutputStream(new FileOutputStream(new File("/root/3des_encrypt.txt")));
		int i;
		while ((i = input.read()) != -1) {
			output.write(i);
		}
		input.close();
		output.close();
	}

	public static void decrypt() throws Exception {
		byte[] key = "000000000000000000000000".getBytes();
		byte[] ivs = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey secretKey = keyFactory.generateSecret(new DESedeKeySpec(key));
		IvParameterSpec iv = new IvParameterSpec(ivs);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		Base64InputStream input = new Base64InputStream(new FileInputStream(new File("/root/3des_encrypt.txt")));
		CipherOutputStream output = new CipherOutputStream(new FileOutputStream(new File("/root/3des_decrypt.txt")), cipher);
		int i;
		while ((i = input.read()) != -1) {
			output.write(i);
		}
		input.close();
		output.close();
	}

	public static void main(String[] args) throws Exception {
//		FileDESedeTest.encrypt();
		FileDESedeTest.decrypt();

	}

}
