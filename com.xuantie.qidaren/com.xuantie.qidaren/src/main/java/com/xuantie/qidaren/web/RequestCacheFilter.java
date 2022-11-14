package com.xuantie.qidaren.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.xuantie.qidaren.common.util.Logger;



/**
 * 请求缓存
 * 解决多次读取请求流
 * @author xuhh
 *
 */
public class RequestCacheFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		Logger.info("------request缓存过滤器begin------" + ((HttpServletRequest) request).getRequestURI());
		long beginTime = System.currentTimeMillis();

		// 缓存非options的request
		if (!"OPTIONS".equalsIgnoreCase(((HttpServletRequest) request).getMethod())) {
			Logger.info(">>>" + ((HttpServletRequest) request).getMethod());
			// 缓存
			ServletRequest requestWrapper = null;
			if (request instanceof HttpServletRequest) {
				requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
			}
			long endTime = System.currentTimeMillis();
			Logger.info("------request缓存过滤器end-" + (endTime - beginTime) + "(msec)------");

			// 执行过滤器链
			if (null == requestWrapper) {
				chain.doFilter(request, response);
			} else {
				chain.doFilter(requestWrapper, response);
			}
		} else {
			Logger.info(">>>" + ((HttpServletRequest) request).getMethod());
			long endTime = System.currentTimeMillis();
			Logger.info("------request缓存过滤器end-" + (endTime - beginTime) + "(msec)------" + ((HttpServletRequest) request).getRequestURI());

			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
