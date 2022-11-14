/**
 * 
 * @description: TODO
 * @author   : xuhh
 * @date     : 2018/4/30
 * @version  : v1.0
 */
package com.xuantie.qidaren.common.util;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 自定义String工具类
 * 
 * @author xuhh
 * @date 2018/4/30
 * 
 */
public class StringUtil {

	/**
	 * string数组差异覆盖
	 * @param source
	 *            源数组
	 * @param dest
	 *            目标数组
	 * @return 目标组[1,2,3] 源数组[1,4,5,6,7] 新数组[1,4,5]
	 */
	public static String[] arrayDiffCopy(String[] source, String[] dest) {
		// 目标数组为空或长度为0
		if (dest == null || dest.length == 0 || source == null
				|| source.length == 0) {
			return dest;
		}
		// 取小的数组长度作为循环长度
		int minleng = source.length >= dest.length ? dest.length
				: source.length;
		// 拷贝源数组内容到目标数组
		String[] result = dest.clone();
		for (int i = 0; i < minleng; i++) {
			if (StringUtils.isNotBlank(source[i])) {
				result[i] = source[i];
			}
		}
		return result;
	}

	/**
	 * 去重合并两个数组
	 * @param array1
	 * @param array2
	 * @return 数据组a[1,2,3] 数组b[2,3,4,5] 新数组[1,2,3,4,5]
	 */
	public static String[] distinctMergeArray(String[] array1, String[] array2) {
		// 空判断
		if (array1 == null && array2 == null) {
			String[] arr = {};
			return arr;
		} else if (array1 == null && array2 != null) {
			return array2;
		} else if (array1 != null && array2 == null) {
			return array1;
		}
		// 直接转成set的数组,取长度大的
		int flag = 1;
		if (array1.length < array2.length) {
			flag = 2;
		}
		// 使用set合并去重数组
		Set<String> arrset = new TreeSet<String>(
				flag == 1 ? Arrays.asList(array1) : Arrays.asList(array2));
		if (flag == 1) {
			for (String item : array2) {
				arrset.add(item);
			}
		} else {
			for (String item : array1) {
				arrset.add(item);
			}
		}
		// 返回结果
		String[] result = {};
		return arrset.toArray(result);
	}

	/**
	 * 生成url参数字符串不包含?号
	 * @param paramMap
	 *            参数map
	 * @return
	 */
	public static String map2QueryString(Map<String, Object> paramMap) {
		// 参数验证
		if (paramMap == null || paramMap.size() == 0) {
			return "";
		}
		// 循环map
		StringBuilder sb = new StringBuilder();
		for (Entry<String, Object> item : paramMap.entrySet()) {
			sb.append(item.getKey() == null ? "" : item.getKey());
			sb.append("=");
			sb.append(item.getValue() == null ? "" : item.getValue());
			sb.append("&");
		}
		// 去除最后一个&
		String result = "";
		if (sb != null && sb.length() > 1) {
			result = sb.substring(0, sb.length() - 1);
		}
		return result;
	}

	/**
	 * 获取请求客户端ip
	 * @param request
	 * @return
	 */
	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("client-ip");
		String cdnip = request.getHeader("Cdn-Src-Ip");
		if (cdnip != null) {
			ip = cdnip;
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
				if (ip == null || ip.length() == 0
						|| "unknown".equalsIgnoreCase(ip)) {
					ip = request.getHeader("WL-Proxy-Client-IP");
					if (ip == null || ip.length() == 0
							|| "unknown".equalsIgnoreCase(ip)) {
						ip = request.getHeader("HTTP_CLIENT_IP");
						if (ip == null || ip.length() == 0
								|| "unknown".equalsIgnoreCase(ip)) {
							ip = request.getHeader("HTTP_X_FORWARDED_FOR");
							if (ip == null || ip.length() == 0
									|| "unknown".equalsIgnoreCase(ip)) {
								ip = request.getRemoteAddr();
							}
						}
					}
				}
			}
		}
		return ip;
	}

	/**
	 * 获取url中不包含http协议的域名
	 * @param origin
	 * @return
	 */
	public static String getOriginWithoutProtocol(String origin) {
		try {
			if (StringUtils.isBlank(origin)) {
				return "";
			}
			if (origin.contains("https://")) {
				origin = origin.replace("https://", "");
			} else if (origin.contains("http://")) {
				origin = origin.replace("http://", "");
			}
			String[] originArray = origin.split("/");
			return originArray[0];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 按比率隐藏字符串部分信息，以*代替隐藏部分，位于字符串中部
	 * @param str
	 *            待处理字符串
	 * @param rate
	 *            隐藏比率
	 * @return
	 */
	public static String hidePartByRate(String str, double rate) {
		try {
			if (StringUtils.isBlank(str)) {
				return str;
			}
			if (str.length() == 1) {
				return "*";
			}
			if (rate >= 1) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < str.length(); i++) {
					sb.append("*");
				}
				return sb.toString();
			}
			int hideCharNum = (int) Math.round(str.length() * rate); // 隐藏的字符个数
			int showCharNum = str.length() - hideCharNum; // 显示的字符个数
			if (showCharNum > 1) {
				int cutCharNum = showCharNum / 2; // 前后平均截取的字符个数
				String preStr = str.substring(0, cutCharNum);
				String suffixStr = str.substring(str.length() - cutCharNum);
				StringBuilder sb = new StringBuilder();
				sb.append(preStr);
				for (int i = 0; i < hideCharNum; i++) {
					sb.append("*");
				}
				sb.append(suffixStr);
				return sb.toString();
			} else {
				String preStr = str.substring(0, 1);
				StringBuilder sb = new StringBuilder();
				sb.append(preStr);
				for (int i = 0; i < hideCharNum; i++) {
					sb.append("*");
				}
				return sb.toString();
			}
		} catch (Exception e) {
			Logger.error("按比率隐藏字符串部分信息异常：", e);
		}
		return str;
	}

	/**
	 * 逗号分隔的map键字符串获取对应的key-value map
	 * @param multMapKeyStr
	 *            multMapKeyStr 以逗号分隔的map键字符串
	 * @param map
	 *            目标map
	 * @return
	 */
	public static Map<String, String> multKeyStr2MultValueMap(
			String multMapKeyStr, Map<String, String> map) {
		try {
			if (StringUtils.isBlank(multMapKeyStr) || map == null
					|| map.size() == 0) {
				return null;
			}
			String[] keys = multMapKeyStr.split(",");
			Map<String, String> result = new HashMap<String, String>();
			for (int i = 0; i < keys.length; i++) {
				if (StringUtils.isNotBlank(map.get(keys[i]))) {
					result.put(keys[i], map.get(keys[i]));
				}
			}
			return result;

		} catch (Exception e) {
			Logger.error("逗号分隔的map键字符串获取对应的key-value map异常：", e);
		}
		return null;
	}

	/**
	 * 逗号分隔的map键字符串获取对应的map值字符串
	 * @param multMapKeyStr
	 *            以逗号分隔的map键字符串
	 * @param map
	 *            目标map
	 * @return 对应的map值字符串
	 */
	public static String multKeyStr2MultValueStr(String multMapKeyStr,
			Map<String, String> map) {
		try {
			if (StringUtils.isBlank(multMapKeyStr) || map == null
					|| map.size() == 0) {
				return "";
			}
			String[] keys = multMapKeyStr.split(",");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < keys.length; i++) {
				if (StringUtils.isNotBlank(map.get(keys[i]))) {
					sb.append(map.get(keys[i]));
					sb.append(",");
				}
			}
			if (sb.length() > 1) {
				sb.deleteCharAt(sb.length() - 1); // 去除最后一个多余的逗号
			}
			return sb.toString();

		} catch (Exception e) {
			Logger.error("逗号分隔的map键字符串获取对应的map值字符串异常：", e);
		}
		return "";
	}

	/**
	 * 是否只包含英文字母数字特殊字符
	 * 
	 * @description:
	 * @param str
	 *            待校验的参数
	 * @return boolean 校验通过返回ture
	 * 
	 */
	public static boolean isLetterOrDigitOrChar(String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		}
		String regex = "^[a-z0-9A-Z`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]+$";
		return str.matches(regex);
	}

	/**
	 * 是否只包含汉字英文数字
	 * 
	 * @description:
	 * @param str
	 *            待校验的参数
	 * @return boolean 校验通过返回ture
	 * 
	 */
	public static boolean isLetterOrDigitOrCharOrChina(String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		}
		String regex = "^[a-zA-Z0-9\u4E00-\u9FA5]+$";
		return str.matches(regex);
	}

	/**
	 * 判断是否为手机号
	 * 
	 * @description:
	 * @param phone
	 * @return boolean
	 * 
	 */
	public static boolean isMobile(String phone) {
		String regex = "^1[3|4|5|7|8]\\d{9}$"; // 手机号码
		// 移动： 139 138 137 136 135 134 147 150 151 152 157 158 159 182 183 184
		// 187 188
		// 联通： 130 131 132 155 156 185 186 145
		// 电信： 133 153 180 181 189
		boolean flag = false;
		try {
			if (phone == null) {
				return flag;
			}
			phone = phone.replaceAll(" ", "");
			if ("".equals(phone)) {
				return flag;
			}
			// flag = match(regex, phone);
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(phone);
			flag = matcher.matches();

		} catch (Exception e) {
			return false;
		}
		return flag;
	}

	/**
	 * 判断是否为手机号
	 * @param mobileNo
	 *            待判断号码
	 * @param regExp
	 *            正则表达式
	 * @return
	 */
	public static boolean isMobile(String mobileNo, String regExp) {
		try {
			Pattern p = Pattern.compile(regExp);
			Matcher m = p.matcher(mobileNo);
			return m.matches();
		} catch (Exception e) {
			Logger.error("判断是否为手机号异常：", e);
			return false;
		}
	}

	/**
	 * 判断是否为邮箱
	 * 
	 * @description:
	 * @param email
	 * @return boolean
	 * 
	 */
	public static boolean isEmail(String email) {
		try {
			Pattern p = Pattern
					.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
			Matcher m = p.matcher(email);
			boolean b = m.matches();
			if (!b) {
				return false;
			}
			return true;
		} catch (Exception e) {
			Logger.error("isEmail exception == ", e);
		}
		return false;
	}

	/**
	 * base64解密
	 * 
	 * @description:
	 * @param sign
	 *            待解密参数
	 * @return String
	 * 
	 */
	public static String base64Decoder(String sign) {
		try {
			if (StringUtils.isBlank(sign)) {
				return "";
			}
			Base64 bd = new Base64();
			byte[] pp = bd.decode(sign);
			return new String(pp, "UTF-8");
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * MD5加密
	 * @description:
	 * @param s
	 * @return String
	 * 
	 */
	public static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获得客户端真实IP地址
	 * 
	 * @description:
	 * @param request
	 * @return String
	 * 
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("client-ip");
		String cdnip = request.getHeader("Cdn-Src-Ip");
		if (cdnip != null) {
			ip = cdnip;
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
				if (ip == null || ip.length() == 0
						|| "unknown".equalsIgnoreCase(ip)) {
					ip = request.getHeader("WL-Proxy-Client-IP");
					if (ip == null || ip.length() == 0
							|| "unknown".equalsIgnoreCase(ip)) {
						ip = request.getHeader("HTTP_CLIENT_IP");
						if (ip == null || ip.length() == 0
								|| "unknown".equalsIgnoreCase(ip)) {
							ip = request.getHeader("HTTP_X_FORWARDED_FOR");
							if (ip == null || ip.length() == 0
									|| "unknown".equalsIgnoreCase(ip)) {
								ip = request.getRemoteAddr();
							}
						}
					}
				}
			}
		}
		return ip;
	}

	/**
	 * 判断子串在字符串中的个数
	 * 
	 */
	public static int substringCount(String str, String substring) {
		if (StringUtils.isBlank(str) || StringUtils.isBlank(substring)) {
			return 0;
		}
		int result = 0;
		if (str.startsWith(substring)) {
			result++;
			str = str.substring(substring.length());
		}
		if (str.endsWith(substring)) {
			result++;
			str = str.substring(0, str.length() - substring.length());
		}
		String[] arr = str.split(substring);
		result = result + arr.length - 1;
		return result;
	}

	/**
	 * 金额验证(不能为负数)
	 * 
	 * @description:
	 * @param str
	 *            例：100或100.12或100.1；有小数点时最多保留两位小数
	 * @return boolean
	 * 
	 */
	public static boolean isMoney(String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		}
		Pattern pattern = Pattern
				.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
		Matcher match = pattern.matcher(str);
		return match.matches();
	}

	/**
	 * 判断字符串是否为纯数字
	 * 
	 * @description:
	 * @param str
	 * @return boolean
	 * 
	 */
	public static boolean isNumer(String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	/**获取随机数 random_len表示几位数**/
	public static String getRandomNum(int random_len){
		try {
			//35是因为数组是从0开始的，26个字母+10个数字
	        final int maxNum = 36;
	        int i; //生成的随机数
	        int count = 0; //生成的密码的长度
	        char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	        StringBuffer random = new StringBuffer("");
	        Random r = new Random();
	        while(count < random_len){
	        	//生成随机数，取绝对值，防止生成负数，
	        	i = Math.abs(r.nextInt(maxNum)); //生成的数最大为36-1
	        	if (i >= 0 && i < str.length) {
	        		random.append(str[i]);
	        		count ++;
	        	}
	        }
	        return random.toString();
		} catch (Exception e) {
			Logger.error("StringUtil genRandomNum Exception = " + e.getMessage(), e);
			return null;
		}
    }

	/**
	 * 获取范围内指定长度的不重复int型随机数
	 * 
	 * @param scope
	 *            随机数范围 0-x
	 * @param amount
	 *            返回的随机数个数
	 * @return
	 */
	public static Integer[] getDistinctRandomIntArr(int scope, int amount) {
		if (scope <= 0 || scope <= amount) {
			return null;
		}
		Random random = new Random();
		Integer[] values = new Integer[amount];
		HashSet<Integer> hashSet = new HashSet<Integer>();
		// 生成随机数字并存入HashSet
		while (hashSet.size() < values.length) {
			hashSet.add(random.nextInt(scope));
		}
		return hashSet.toArray(values);
	}

	/**
	 * 使用DecimalFormat进行数字格式化
	 * 
	 * @description:
	 * @param db
	 *            参数
	 * @param templet
	 *            模板：1.每三位以逗号进行分隔并保留两位小数"###,##0.00";2.保留两位小数"#0.00"
	 * @return String 例："###,##0.00"=12,000.00
	 * 
	 */
	public static String Decimalformat(double db, String templet) {
		if (StringUtils.isBlank(templet)) {
			return "";
		}
		DecimalFormat df1 = new DecimalFormat(templet);
		return df1.format(db);
	}

	/**
	 * 验证签名-签名方式 MD5(对非空value值根据key键值按字典升序排序+私钥)
	 * 
	 * @description:
	 * @param map
	 *            请求参数集合
	 * @param key
	 *            私钥
	 * @param sign
	 *            签名
	 * @return boolean
	 * 
	 */
	public static boolean checkSign(Map<String, String> map, String key,
			String sign) {
		try {
			if (StringUtils.isBlank(key) || StringUtils.isBlank(sign)) {
				return false;
			}
			String md = MD5Util.getMD5HexString(getKeySortAsc(map) + key);
			if (sign.equalsIgnoreCase(md)) {
				return true;
			}
		} catch (Exception e) {
			Logger.error("验证签名异常：", e);
		}
		return false;
	}

	/**
	 * 对非空value值根据key键值按字典升序排序
	 * 
	 * @description:
	 * @param map
	 *            例{asc=value1, close=value2, bool=value3,key=null}
	 *            结果：ascvalue1boolvalue3closevalue2
	 * @return String
	 * 
	 */
	public static String getKeySortAsc(Map<String, String> map) {
		try {
			StringBuilder kv = new StringBuilder();
			if (map != null && map.size() > 0) {
				Collection<String> keyset = map.keySet();
				// 将key放入list集合
				List<String> keys = new ArrayList<String>(keyset);
				// 对key键值按字典升序排序
				Collections.sort(keys);
				// 存放排序后的keyvalue
				for (int i = 0; i < keys.size(); i++) {
					if (StringUtils.isNotBlank(map.get(keys.get(i)))) {// value值非空
						// 拼装
						kv.append(keys.get(i)).append(map.get(keys.get(i)));
					}
				}
			}
			return kv.toString();
		} catch (Exception e) {
			Logger.error("对非空value值根据key键值按字典升序排序异常：", e);
		}
		return "";

	}

	/**
	 * 获取UUID
	 * 
	 * @description:
	 * @return String
	 * 
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 判断是否为固定电话
	 * @param phone
	 *            待判断固定电话
	 * @return
	 */
	public static boolean isPhone(String phone) {
		if (StringUtils.isBlank(phone)) {
			return false;
		}
		try {
			String reg = "(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|"
					+ "(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)";
			Pattern p = Pattern.compile(reg);
			Matcher m = p.matcher(phone);
			return m.matches();
		} catch (Exception e) {
			Logger.error("判断是否为手机号异常：", e);
			return false;
		}
	}

	/**
	 * 判断是否为正确的url地址
	 * @param url
	 * @return
	 */
	public static boolean isUrl(String url) {
		if (StringUtils.isBlank(url)) {
			return false;
		}
		String reg = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(url);
		return m.matches();
	}
	 public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }  	
	
    /**
     * 获取当前时间
     * @return
     */
    public static String getCurrDate() {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMM");
		Date dDate = new Date();
		String strTime = myFormatter.format(dDate);
		return strTime;
	}
    
    /**
     * 获取当前时间
     * @return
     */
    public static String getCurrDate(String type) {
		SimpleDateFormat myFormatter = new SimpleDateFormat(type);
		Date dDate = new Date();
		String strTime = myFormatter.format(dDate);
		return strTime;
	}
    
    public static String getTomorrow(String type){
    	Date date=new Date();//取时间
    	Calendar calendar = new GregorianCalendar();
    	calendar.setTime(date);
    	calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
    	date=calendar.getTime(); //这个时间就是日期往后推一天的结果
    	SimpleDateFormat formatter = new SimpleDateFormat(type);
    	return formatter.format(date);
    }
    public static String getNextMonth(String type){
    	Date date=new Date();//取时间
    	Calendar calendar = new GregorianCalendar();
    	calendar.setTime(date);
    	calendar.add(calendar.MONTH ,1);//把日期往后增加一天.整数往后推,负数往前移动
    	date=calendar.getTime(); //这个时间就是日期往后推一天的结果
    	SimpleDateFormat formatter = new SimpleDateFormat(type);
    	return formatter.format(date);
    }
    public static String getYesterday(String type){
    	Date date=new Date();//取时间
    	Calendar calendar = new GregorianCalendar();
    	calendar.setTime(date);
    	calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
    	date=calendar.getTime(); //这个时间就是日期往后推一天的结果
    	SimpleDateFormat formatter = new SimpleDateFormat(type);
    	return formatter.format(date);
    }
    
    public static boolean isLastDayOfMonth() { 
    	Date dDate = new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, c.get(Calendar.YEAR));
        c.set(Calendar.MONTH, c.get(Calendar.MONTH)+1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH, -12);
        //System.out.println("本月倒数第二天为："+df.format(c.getTime()));      
		String strTime = df.format(dDate);
		if(strTime.equals(df.format(c.getTime()))){
			 return true;
		}
 		return false;
    } 
    
    
    public static void main () {
    	System.out.println("isLastDayOfMonth="+isLastDayOfMonth());
    }
}
