/**
 * 
 * @description: TODO
 * @author   : xuhh
 * @date     : 2018/4/30
 * @version  : v1.0
 */
package com.xuantie.qidaren.common.util;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 
 * fastJson工具类
 * @author xuhh
 * @date 2018/4/30
 *
 */
public class FastJson {

	/**
	 * 判断字符串是否为json格式
	 * @param str
	 */
	public static boolean isJson(String str) {
		try {
			if (StringUtils.isBlank(str)) {
				return false;
			}
			JSONObject json = JSON.parseObject(str);
			if (null != json) {
				return true;
			}
		} catch (Exception e) {
			try {
				JSONArray jsonArr = JSON.parseArray(str);
				if (null != jsonArr) {
					return true;
				}
			} catch (Exception e2) {
				return false;
			}
		}
		return false;
	}

	/**
	 * 对象转json
	 * @param obj
	 * @return
	 */
	public static String obj2json(Object obj) {
		return JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue);
	}

	/**
	 * json转对象
	 * @param json
	 * @param type
	 * @return
	 */
	public static <K> K json2obj(String json, Class<K> type) {
		return JSON.parseObject(json, type);
	}

	/**
	 * json转对象
	 * @param json
	 * @param type
	 * @return
	 */
	public static <K> K json2obj(String json, TypeReference<K> type) {
		return JSON.parseObject(json, type);
	}

	/**
	 * json转对象
	 * @param json
	 * @param type
	 * @return
	 */
	public static <K> K json2obj(String json, Type type) {
		return JSON.parseObject(json, type);
	}

	/**
	 * json转map(保持json顺序)
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> json2linkedmap(String json) {
		LinkedHashMap<String, Object> jsonMap = JSON.parseObject(json, LinkedHashMap.class);
		LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
		if (jsonMap != null && jsonMap.size() > 0) {
			for (Entry<String, Object> item : jsonMap.entrySet()) {
				result.put(item.getKey(), item.getValue().toString());
			}
		}
		return result;
	}

	/**
	 * json转map
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String, String> json2hashmap(String json) {
		HashMap<String, Object> jsonMap = JSON.parseObject(json, HashMap.class);
		HashMap<String, String> result = new HashMap<String, String>();
		if (jsonMap != null && jsonMap.size() > 0) {
			for (Entry<String, Object> item : jsonMap.entrySet()) {
				result.put(item.getKey(), item.getValue().toString());
			}
		}
		return result;
	}
}
