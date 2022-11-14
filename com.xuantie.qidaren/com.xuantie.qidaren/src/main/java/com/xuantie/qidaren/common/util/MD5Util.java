package com.xuantie.qidaren.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * 
 * MD5工具类
 * @author xuhh
 * @date 2018/4/30
 *
 */
public class MD5Util {

	/**
	 * 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合
	 */
	private static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e",
			"f" };

	/**
	 * 初始化
	 */
	private static MessageDigest messagedigest = null;
	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			Logger.error("MD5Util初始化失败,MessageDigest不支持MD5.", e);
		}
	}

	/**
	 * 字节转16进制
	 * @param bByte 字节
	 * @return
	 * @throws Exception 
	 */
	private static String byte2Hex(byte bByte) throws Exception {
		try {
			int iRet = bByte;
			if (iRet < 0) {
				iRet += 256;
			}
			int iD1 = iRet / 16;
			int iD2 = iRet % 16;
			return hexDigits[iD1] + hexDigits[iD2];
		} catch (Exception e) {
			throw new Exception("字节转16进制异常：", e);
		}
	}

	/**
	 * 字节数组转16进制
	 * @param bytes 字节数组
	 * @return
	 * @throws Exception 
	 */
	private static String byteArray2Hex(byte[] bytes) throws Exception {
		try {
			StringBuffer sBuffer = new StringBuffer();
			for (int i = 0; i < bytes.length; i++) {
				sBuffer.append(byte2Hex(bytes[i]));
			}
			return sBuffer.toString();
		} catch (Exception e) {
			throw new Exception("字节数组转16进制异常：", e);
		}
	}

	/**
	 * 获取字符串 MD5 值
	 * @param str 字符串
	 * @return
	 * @throws Exception 
	 */
	public static byte[] getMD5(String str) throws Exception {
		try {
			return getMD5(str.getBytes());
		} catch (Exception e) {
			throw new Exception("获取字符串 MD5 值异常：", e);
		}
	}

	/**
	 * 获取字节数组 MD5 值
	 * @param bytes 字节数组
	 * @return
	 * @throws Exception 
	 */
	public static byte[] getMD5(byte[] bytes) throws Exception {
		try {
			messagedigest.update(bytes);
			return messagedigest.digest();
		} catch (Exception e) {
			throw new Exception("获取字节数组 MD5 值异常：", e);
		}
	}

	/**
	 * 获取文件 MD5 值
	 * @param file 文件
	 * @return
	 * @throws Exception 
	 */
	public static byte[] getFileMD5(File file) throws Exception {
		InputStream fis = null;
		try {
			fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int numRead = 0;
			while ((numRead = fis.read(buffer)) > 0) {
				messagedigest.update(buffer, 0, numRead);
			}
			//fis.close();
			return messagedigest.digest();
		} catch (Exception e) {
			throw new Exception("获取文件 MD5 值异常：", e);
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (Exception e2) {
				fis = null;
			}
		}
	}

	/**
	 * 获取文件流 MD5 值
	 * @param ioIn 文件流
	 * @return
	 * @throws Exception
	 */
	public static byte[] getFileMD5(InputStream ioIn) throws Exception {
		try {
			byte[] buffer = new byte[1024];
			int numRead = 0;
			while ((numRead = ioIn.read(buffer)) > 0) {
				messagedigest.update(buffer, 0, numRead);
			}
			ioIn.close();
			return messagedigest.digest();
		} catch (Exception e) {
			throw new Exception("获取文件流 MD5 值异常：", e);
		}

	}

	/**
	 * 获取字符串 MD5 值 16进制表示
	 * @param str
	 * @return
	 * @throws Exception 
	 */
	public static String getMD5HexString(String str) throws Exception {
		try {
			return byteArray2Hex(getMD5(str));
		} catch (Exception e) {
			throw new Exception("获取字符串 MD5 值 16进制表示异常：", e);
		}
	}

	/**
	 * 获取字节数组 MD5 值 16进制表示
	 * @param bytes 字节数组
	 * @return
	 * @throws Exception 
	 */
	public static String getMD5HexString(byte[] bytes) throws Exception {
		try {
			return byteArray2Hex(getMD5(bytes));
		} catch (Exception e) {
			throw new Exception("获取字节数组 MD5 值 16进制表示异常：", e);
		}
	}

	/**
	 * 获取文件 MD5 值 16进制表示
	 * @param file 文件
	 * @return
	 * @throws Exception 
	 */
	public static String getFileMD5HexString(File file) throws Exception {
		try {
			return byteArray2Hex(getFileMD5(file));
		} catch (Exception e) {
			throw new Exception("获取文件 MD5 值 16进制表示异常：", e);
		}
	}

	/**
	 * 获取文件流 MD5 值 16进制表示 
	 * @param ioIn 文件流
	 * @return
	 * @throws Exception
	 */
	public static String getFileMD5HexString(InputStream ioIn) throws Exception {
		try {
			return byteArray2Hex(getFileMD5(ioIn));
		} catch (Exception e) {
			throw new Exception("获取文件流 MD5 值 16进制表示异常：", e);
		}
	}
}
