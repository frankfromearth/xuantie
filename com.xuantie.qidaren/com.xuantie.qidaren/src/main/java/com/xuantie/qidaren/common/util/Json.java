/**
 * 
 * @description: TODO
 * @author   : xuhh
 * @date     : 2018/4/30
 * @version  : v1.0
 */
package com.xuantie.qidaren.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

/**
 * Json工具类
 * @author xuhh
 * @date 2018/4/30
 *
 */
public class Json {

	/**
	 * 使用输出流输出json
	 * @param request http请求
	 * @param response http响应
	 * @param bizObj 需要输出的业务对象
	 * @param callBackName 跨域时,回调函数的参数名称
	 */
	public static void writeJson(HttpServletRequest request, HttpServletResponse response, Object bizObj,
			String... callBackName) {

		// 请求输出流
		ServletOutputStream out = null;
		try {
			// 回调函数名称
			String callBackParamName = (callBackName == null || callBackName.length == 0) ? "callback"
					: callBackName[0];
			String callbackFucName = request.getParameter(callBackParamName);

			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Request-Headers", "*");

			// 配置contentType
			if (StringUtils.isNotEmpty(callbackFucName)) {
				response.setContentType("text/plain;charset=UTF-8");
			} else {
				response.setContentType("text/html;charset=UTF-8");
			}

			response.setContentType("application/json");

			// 获取输出流
			out = response.getOutputStream();

			// object2json
			String returnValue = "";
			if (bizObj instanceof String) {
				returnValue = (String) bizObj;
			} else {
				returnValue = FastJson.obj2json(bizObj);
			}

			if (StringUtils.isNotEmpty(callbackFucName)) {
				returnValue = callbackFucName + "(" + returnValue + ")";
			}

			// 输出json
			out.write(returnValue.getBytes("UTF-8"));
			out.flush();
			out.close();
		} catch (Exception e) {
			Logger.error("使用输出流输出json异常:" + e.getMessage(), e);
			try {
				out.flush();
				out.close();
			} catch (Exception e2) {
				Logger.error("使用输出流输出json异常:" + e2.getMessage(), e2);
				out = null;
			}
		}
	}

	/**
	 * 获取request payload json
	 * @param request http请求
	 * @return
	 */
	public static String getPayloadJson(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			char[] buff = new char[1024];
			int len;
			while ((len = reader.read(buff)) != -1) {
				sb.append(buff, 0, len);
			}
		} catch (IOException e) {
			Logger.error("", e);
		}
		return sb.toString();
	}
}
