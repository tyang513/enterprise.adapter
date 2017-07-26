package com.td.code;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

//import org.apache.commons.codec.binary.Base64;

public class Test {

	public static byte[] sharedkey = "000000000000000000000000".getBytes();

	public static byte[] sharedvector = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 };

	public static void encrypt(String plaintext) throws Exception {
		Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(sharedkey, "DESede"), new IvParameterSpec(sharedvector));
		byte[] encrypted = c.doFinal(plaintext.getBytes("UTF-8"));
		System.out.println(Base64.encodeBase64String(encrypted));
	}

	/**
	 * 转换密钥<br>
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static Key toKey(byte[] key) throws Exception {
		DESedeKeySpec dks = new DESedeKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey secretKey = keyFactory.generateSecret(dks);
		return secretKey;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Cipher c3des = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		byte[] keyBytes = "000000000000000000000000".getBytes();
		Key key = Test.toKey(keyBytes);
		IvParameterSpec iv = new IvParameterSpec(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 });
		c3des.init(Cipher.ENCRYPT_MODE, key, iv);
		byte[] value = "DESC".getBytes("UTF-8");
		byte[] cipherText = c3des.doFinal(value);
		String encryptedString = Base64.encodeBase64String(cipherText);
		System.out.println("加密字符串");
		System.out.println(encryptedString);
		
		Test.encrypt("asdfskkkkkkkkkkkkkkkkkkkk");
	}
}
