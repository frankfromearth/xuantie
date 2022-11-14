package com.xuantie.qidaren.common.util;

public class testUtils {
	
	public static void main(String[] args) {
		String timestamp = StringUtil.getCurrDate("yyyyMMddHHmmss"); // 时间戳
		String authflag = "xuanti!123".concat(timestamp); // 鉴权标识
		String authkey = "%$xuanti!#".concat(timestamp.substring(timestamp.length() - 6, timestamp.length())); // 鉴权秘钥
		try {
			String clientauthflag = AESUtil.encryptAES(authflag, authkey);
			System.out.println("timestamp="+timestamp);
			System.out.println("clientauthflag="+clientauthflag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
