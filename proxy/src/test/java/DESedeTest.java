

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DESedeTest {

	public static void main(String[] args) throws Exception {
		byte[] key = "000000000000000000000000".getBytes();
		byte[] ivs = new byte[] {0, 0, 0, 0, 0, 0, 0, 0};
		String DES_ECB = "DESede/CBC/PKCS5Padding";
		Cipher cipher = Cipher.getInstance(DES_ECB);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey secretKey = keyFactory.generateSecret(new DESedeKeySpec(key));
		IvParameterSpec iv = new IvParameterSpec(ivs);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		byte[] encryptedString = cipher.doFinal("865030020201142".getBytes());
		System.out.println(org.apache.commons.codec.binary.Base64.encodeBase64String(encryptedString));
		
//		System.out.println(com.Ostermiller.util.Base64.encode("abc"));
//		System.out.println(com.Ostermiller.util.Base64.decode("YWJj"));
		
//		Base64.decode()
		
//		//加密
//		CipherInputStream inputStream = new CipherInputStream(new FileInputStream(new File("/root/3des_明文.txt")), cipher);
//		FileOutputStream outputStream = new FileOutputStream(new File("/root/3des_密文.txt"));
//		Base64.encode(inputStream, outputStream);
//		inputStream.close();
//		outputStream.close();
//		inputStream = null;
//		outputStream = null;
//		
//		// 解密
//		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
//		FileInputStream input = new FileInputStream(new File("/root/3des_密文.txt"));
//		CipherOutputStream output = new CipherOutputStream(new FileOutputStream(new File("/root/3des_解密.txt")), cipher);
//		Base64.encode(input, output);
//		input.close();
//		output.close();
		
		
		
	}
}
