package com.td.common.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

public class DESede {

	/**
	 * 密钥
	 */
	private String key;

	/**
	 * 向量
	 */
	private String ivs;

	/**
	 * 转化类型
	 */
	private String transformation;

	/**
	 * 算法
	 */
	private String algorithm;

	/**
	 * @param key
	 *            密钥
	 * @param ivs
	 *            向量
	 */
	public DESede(String transformation, String key, String algorithm, String ivs) {
		this.transformation = transformation;
		this.key = key;
		this.algorithm = algorithm;
		this.ivs = ivs;
	}

	/**
	 * 初始化DESede Cipher
	 * 
	 * @return
	 * @throws Exception
	 */
	public Cipher initEncryptCipher() throws Exception {
		Cipher cipher = Cipher.getInstance(transformation);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
		SecretKey secretKey = keyFactory.generateSecret(new DESedeKeySpec(key.getBytes()));
		IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		return cipher;
	}

	/**
	 * 加密
	 * 
	 * @param cipher
	 *            加密器
	 * @see com.td.common.util.DESede.initEncryptCipher();
	 * @param s
	 *            输入字符串
	 * @param isBase64
	 *            是否需要进行base64编码
	 * @return
	 * @throws Exception
	 */
	public String encrypt(Cipher cipher, String s, boolean isBase64) throws Exception {
		if (isBase64) {
			return Base64.encodeBase64String(cipher.doFinal(s.getBytes("utf-8")));
		}
		return new String(cipher.doFinal(s.getBytes("utf-8")));
	}

	/**
	 * 加密
	 * 
	 * @param cipher
	 *            加密器
	 * @see com.td.common.util.DESede.initEncryptCipher();
	 * @param s
	 *            输入字符串
	 * @return 加密后的字符串
	 * @throws Exception
	 */
	public String encrypt(Cipher cipher, String s) throws Exception {
		return encrypt(cipher, s, true);
	}

	public Cipher initDecryptCipher() throws Exception {
		Cipher cipher = Cipher.getInstance(transformation);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
		SecretKey secretKey = keyFactory.generateSecret(new DESedeKeySpec(key.getBytes()));
		IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		return cipher;
	}

	/**
	 * 解密
	 * 
	 * @param cipher
	 *            密码器
	 * @see com.td.common.util.DESede.initEncryptCipher();
	 * @param s
	 *            输入字符串
	 * @param isBase64
	 *            是否需要进行base64编码
	 * @return
	 * @throws Exception
	 */
	public String decrypt(Cipher cipher, String s, boolean isBase64) throws Exception {
		if (isBase64) {
			return new String(cipher.doFinal(Base64.decodeBase64(s)));
		}
		return new String(cipher.doFinal(s.getBytes("utf-8")));
	}

	/**
	 * 解密
	 * 
	 * @param cipher
	 *            密码器
	 * @param s
	 *            解密字符串
	 * @return 解密后的字符串
	 * @throws Exception
	 */
	public String decrypt(Cipher cipher, String s) throws Exception {
		return decrypt(cipher, s, true);
	}

	public static void main(String[] args) throws Exception {
		DESede deSede = new DESede(DESedeUtil.DESede_CBC_PKCS5Padding, "000000000000000000000000", DESedeUtil.ALGORITHM_DESEDE, "00000000");
		Cipher cipher = deSede.initEncryptCipher();
		System.out.println(deSede.encrypt(cipher, "865030020201142"));
	}
	
}
