import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;


public class Base64Test {

	public  static void encrypt(String content) throws Exception {
		byte[] key = "000000000000000000000000".getBytes();
		byte[] ivs = "00000000".getBytes();
		String DES_ECB = "DESede/CBC/PKCS5Padding";
		Cipher cipher = Cipher.getInstance(DES_ECB);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey secretKey = keyFactory.generateSecret(new DESedeKeySpec(key));
		IvParameterSpec iv = new IvParameterSpec(ivs);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		System.out.println(Base64.encodeBase64String(cipher.doFinal(content.getBytes("utf-8"))));
	}
	
	public  static void decrypt(String content) throws Exception {
		byte[] key = "000000000000000000000000".getBytes();
		byte[] ivs = "00000000".getBytes();
		String DES_ECB = "DESede/CBC/PKCS5Padding";
		Cipher cipher = Cipher.getInstance(DES_ECB);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey secretKey = keyFactory.generateSecret(new DESedeKeySpec(key));
		IvParameterSpec iv = new IvParameterSpec(ivs);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		System.out.print(new String(cipher.doFinal(Base64.decodeBase64(content))));
	}
	
	public static void main(String[] args) throws Exception {
		Base64Test.encrypt("865030020201142");
		Base64Test.decrypt("uu8rdOwhYPU3bs8zaP7UGA==");
		//uu8rdOwhYPU3bs8zaP7UGA==
		//BSOVeInd0AIZMBPHHHC/3N9NjR4vVeBwRSfEM/E788BWZoBb3YjKJQ==
	}
	
}
