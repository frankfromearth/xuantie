/**
 * 
 * @description: TODO
 * @author   : xuhh
 * @date     : 2018/4/27
 * @version  : v1.0
 */
package com.xuantie.qidaren.common.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Base64;

public class AESUtil {

	private static Cipher c1 = null; // 加密
	private static Cipher c2 = null; // 解密
	private static KeyGenerator kgen = null;
	private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

	static {
		try {
			kgen = KeyGenerator.getInstance("AES");
			kgen.init(128);
			c1 = Cipher.getInstance(ALGORITHMSTR); // 加密
			c2 = Cipher.getInstance(ALGORITHMSTR); // 解密
		} catch (Exception e) {
			Logger.error(">>> 初始化AES加解密工具异常：", e);
		}
	}

	/** 
	 * AES加密
	 * @param plaintText 明文
	 * @param encryptKey  秘钥  需16位
	 * @return  base64编码的密文
	 * @throws Exception 
	 */
	public static String encryptAES(String plaintText, String encryptKey) throws Exception {
		try {
			return new String(Base64.encode(encryptAES2Byte(plaintText, encryptKey)));
		} catch (Exception e) {
			Logger.error("AES加密异常：", e);
			throw e;
		}
	}

	/**
	 * AES解密
	 * @param encryptStr base64编码的密文
	 * @param decryptKey 秘钥  需16位
	 * @return
	 * @throws Exception
	 */
	public static String decryptAES(String encryptStr, String decryptKey) throws Exception {
		try {
			return decryptAES(Base64.decode(encryptStr), decryptKey);
		} catch (Exception e) {
			Logger.error("ASE解密异常：", e);
			throw e;
		}
	}

	/**
	 * AES加密
	 * @param plaintText  密文字符串
	 * @param encryptKey 秘钥  需16位
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptAES2Byte(String plaintText, String encryptKey) throws Exception {
		try {
			c1.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
			return c1.doFinal(plaintText.getBytes("utf-8"));
		} catch (Exception e) {
			Logger.error("AES加密异常：", e);
			throw e;
		}
	}

	/**
	 * AES解密
	 * @author xuhh
	 * @date 2018/4/27 
	 * @param encryptBytes 密文字符数组
	 * @param decryptKey 秘钥  需16位
	 * @return
	 * @throws Exception
	 */
	public static String decryptAES(byte[] encryptBytes, String decryptKey) throws Exception {
		try {
			c2.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
			byte[] decryptBytes = c2.doFinal(encryptBytes);
			return new String(decryptBytes);
		} catch (Exception e) {
			Logger.error("ASE解密异常：", e);
			throw e;
		}
	}

}
