package com.td.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;

/**
 * 3DES对称加密算法 3DES加密后,再用base64进行加密
 * 
 * @author yangtao
 */
public class DESedeUtil {

	public static final Object DESede_Algorithm = "ALGORITHM";

	public static final Object DESede_Ivs = "IVS";

	public static String DESede_Transformation = "TRANSFORMATION";

	public static String DESede_Key = "KEY";

	public static String DESede_CBC_NoPadding = "DESede/CBC/NoPadding";

	public static String DESede_CBC_PKCS5Padding = "DESede/CBC/PKCS5Padding";

	public static String ALGORITHM_DESEDE = "DESede";

	/**
	 * 文件加密
	 * 
	 * @param transformation
	 *            取值为: DESede/CBC/NoPadding(168)或 DESede/CBC/PKCS5Padding
	 * @param key
	 *            key
	 * @param algorithm
	 *            取值为：DESede
	 * @param iv
	 *            向量
	 * @param inputFilePath
	 *            输入文件路径
	 * @param outputFilePath
	 *            输出文件路径
	 * @throws Exception
	 *             异常
	 */
	public static void encryptBase64(String transformation, byte[] key, String algorithm, byte[] ivs, String inputFilePath, String outputFilePath)
			throws Exception {
		Cipher cipher = Cipher.getInstance(transformation);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
		SecretKey secretKey = keyFactory.generateSecret(new DESedeKeySpec(key));
		IvParameterSpec iv = new IvParameterSpec(ivs);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		CipherInputStream input = new CipherInputStream(new FileInputStream(new File(inputFilePath)), cipher);
		Base64OutputStream output = new Base64OutputStream(new FileOutputStream(new File(outputFilePath)));
		int i;
		while ((i = input.read()) != -1) {
			output.write(i);
		}
		input.close();
		output.close();
	}

//	/**
//	 * 文件加密 最终文件只有一行字符串
//	 * 
//	 * @param transformation
//	 *            取值为: DESede/CBC/NoPadding(168)或 DESede/CBC/PKCS5Padding
//	 * @param key
//	 *            key
//	 * @param algorithm
//	 *            取值为：DESede
//	 * @param iv
//	 *            向量
//	 * @param inputFilePath
//	 *            输入文件路径
//	 * @param outputFilePath
//	 *            输出文件路径
//	 * @throws Exception
//	 *             异常
//	 */
//	public static void encryptBase64ByLine(String transformation, byte[] key, String algorithm, byte[] ivs, String inputFilePath,
//			String outputFilePath) throws Exception {
//		Cipher cipher = Cipher.getInstance(transformation);
//		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
//		SecretKey secretKey = keyFactory.generateSecret(new DESedeKeySpec(key));
//		IvParameterSpec iv = new IvParameterSpec(ivs);
//		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
//		InputStream input = new CipherInputStream(new FileInputStream(new File(inputFilePath)), cipher);
//		BufferedReader read = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8")));
//		OutputStream output = new Base64OutputStream(new FileOutputStream(new File(outputFilePath)));
//		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, Charset.forName("UTF-8")));
//		String line = null;
//		while ((line = read.readLine()) != null) {
//			writer.write(line);
//			writer.newLine();
//		}
//		read.close();
//		writer.close();
//	}

	/**
	 * 文件加密 每行文件为一个加密字符串
	 * 
	 * @param transformation
	 *            转换类型
	 * @param key
	 *            密钥
	 * @param algorithm
	 *            算法
	 * @param ivs
	 *            向量
	 * @param inputFilePath
	 *            输出文件路径
	 * @param outputFilePath
	 *            输入文件路径
	 * @throws Exception
	 */
	public static void encryptBase64ByLine(String transformation, String key, String algorithm, String ivs, String inputFilePath,
			String outputFilePath) throws Exception {
		DESede deSede = new DESede(transformation, key, algorithm, ivs);
		Cipher cipher = deSede.initEncryptCipher();
		BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inputFilePath)), Charset.forName("UTF-8")));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outputFilePath)), Charset.forName("UTF-8")));
		String line = null;
		while ((line = read.readLine()) != null) {
			String s = deSede.encrypt(cipher, line);
			writer.write(s);
			writer.newLine();
		}
		read.close();
		writer.close();
	}

	/**
	 * 文件解密
	 * 
	 * @param transformation
	 *            取值为: DESede/CBC/NoPadding(168)或 DESede/CBC/PKCS5Padding
	 * 
	 * @param key
	 *            key
	 * @param algorithm
	 *            取值为：DESede
	 * @param ivs
	 *            向量
	 * @param inputFilePath
	 *            输入文件路径
	 * @param outputFilePath
	 *            输出文件路径
	 * @throws Exception
	 *             异常
	 */
	public static void decryptBase64(String transformation, byte[] key, String algorithm, byte[] ivs, String inputFilePath, String outputFilePath)
			throws Exception {
		Cipher cipher = Cipher.getInstance(transformation);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
		SecretKey secretKey = keyFactory.generateSecret(new DESedeKeySpec(key));
		IvParameterSpec iv = new IvParameterSpec(ivs);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		Base64InputStream input = new Base64InputStream(new FileInputStream(new File(inputFilePath)));
		CipherOutputStream output = new CipherOutputStream(new FileOutputStream(new File(outputFilePath)), cipher);
		int i;
		while ((i = input.read()) != -1) {
			output.write(i);
		}
		input.close();
		output.close();
	}

//	/**
//	 * 文件解密
//	 * 
//	 * @param transformation
//	 *            取值为: DESede/CBC/NoPadding(168)或 DESede/CBC/PKCS5Padding
//	 * 
//	 * @param key
//	 *            key
//	 * @param algorithm
//	 *            取值为：DESede
//	 * @param ivs
//	 *            向量
//	 * @param inputFilePath
//	 *            输入文件路径
//	 * @param outputFilePath
//	 *            输出文件路径
//	 * @throws Exception
//	 *             异常
//	 */
//	public static void decryptBase64ByLine(String transformation, byte[] key, String algorithm, byte[] ivs, String inputFilePath,
//			String outputFilePath) throws Exception {
//		Cipher cipher = Cipher.getInstance(transformation);
//		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
//		SecretKey secretKey = keyFactory.generateSecret(new DESedeKeySpec(key));
//		IvParameterSpec iv = new IvParameterSpec(ivs);
//		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
//		InputStream input = new Base64InputStream(new FileInputStream(new File(inputFilePath)));
//		BufferedReader read = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8")));
//		OutputStream output = new CipherOutputStream(new FileOutputStream(new File(outputFilePath)), cipher);
//		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, Charset.forName("UTF-8")));
//		String line = null;
//		while ((line = read.readLine()) != null) {
//			writer.write(line);
//			writer.newLine();
//		}
//		read.close();
//		writer.close();
//	}

	/**
	 * 将文件进行解密, 一行加密字符串
	 * 
	 * @param transformation
	 *            转换类型
	 * @param key
	 *            密钥
	 * @param algorithm
	 *            算法
	 * @param ivs
	 *            向量
	 * @param inputFilePath
	 *            文件输入路径
	 * @param outputFilePath
	 *            文件输出路径
	 * @throws Exception
	 */
	public static void decryptBase64ByLine(String transformation, String key, String algorithm, String ivs, String inputFilePath,
			String outputFilePath) throws Exception {
		DESede deSede = new DESede(transformation, key, algorithm, ivs);
		Cipher cipher = deSede.initDecryptCipher();
		BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inputFilePath)), Charset.forName("UTF-8")));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outputFilePath)), Charset.forName("UTF-8")));
		String line = null;
		while ((line = read.readLine()) != null) {
			writer.write(deSede.decrypt(cipher, line));
			writer.newLine();
		}
		read.close();
		writer.close();
	}

	public static void main(String[] args) throws Exception {
//		byte[] key = "000000000000000000000000".getBytes();//"012345678012345678012345678".getBytes();
//		byte[] ivs = "00000000".getBytes(); // "01234567".getBytes();
		// System.out.println("000000000000000000000000".length());
//		DESedeUtil.encryptBase64ByLine(DESedeUtil.DESede_CBC_PKCS5Padding, key, DESedeUtil.ALGORITHM_DESEDE, ivs, "/root/3des_mw.txt",
//				"/root/3des_encrypt.txt");
//		DESedeUtil.decryptBase64ByLine(DESedeUtil.DESede_CBC_PKCS5Padding, key, DESedeUtil.ALGORITHM_DESEDE, ivs, "/root/3des_encrypt.txt",
//				"/root/3des_decrypt.txt");
		System.out.println(System.currentTimeMillis());
		DESedeUtil.encryptBase64ByLine(DESedeUtil.DESede_CBC_PKCS5Padding, "000000000000000000000000", DESedeUtil.ALGORITHM_DESEDE, "00000000",
				"/root/uuid.txt", "/root/uuid_encrypt.txt");
		System.out.println(System.currentTimeMillis());
		DESedeUtil.decryptBase64ByLine(DESedeUtil.DESede_CBC_PKCS5Padding, "000000000000000000000000", DESedeUtil.ALGORITHM_DESEDE, "00000000",
				"/root/uuid_encrypt.txt", "/root/uuid_decrypt.txt");
		System.out.println(System.currentTimeMillis());
	}

}
