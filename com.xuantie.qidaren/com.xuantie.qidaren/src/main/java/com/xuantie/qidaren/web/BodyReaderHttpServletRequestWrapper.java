package com.xuantie.qidaren.web;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;


/**
 * 自定义包装器类
 * @author xuhh
 *
 */
public class BodyReaderHttpServletRequestWrapper extends
		HttpServletRequestWrapper {

	private final byte[] body;
	
	public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		if (request.getContentType() == null || !request.getContentType().contains("multipart/form-data")) {
			// 1.调用getParameter会初始化request.parametersParsed参数为true,否则会导致RequestWrapper取不到Form Data中的值。
			// 2.调用getParameter会导致文件上传接口出现跨域错误,此处排除"multipart/form-data"的请求。
			request.getParameter("");
		}
		body = IOUtils.toByteArray(request.getInputStream());
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(body)));
	}
	
	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bais = new ByteArrayInputStream(body);
		
		return new ServletInputStream() {
			@Override
			public int read() throws IOException {
				// TODO Auto-generated method stub
				return bais.read();
			}

			/**
			 * 非阻塞获取使用此方法
			 */
			@Override
			public boolean isFinished() {
				// TODO Auto-generated method stub
				return false;
			}

			/**
			 * Returns true if data can be read without blocking else returns false.
			 * 非阻塞获取返回true
			 */
			@Override
			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void setReadListener(ReadListener listener) {
				
			}
		};
	}
}
