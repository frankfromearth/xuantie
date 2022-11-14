
package com.xuantie.qidaren;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.xuantie.qidaren.common.interceptor.AuthenticateInterceptor;
import com.xuantie.qidaren.common.interceptor.SecurityInterceptor;
import com.xuantie.qidaren.web.RequestCacheFilter;


/**
 * 
 * web配置类
 * @author xuhh
 * @date 2018/4/27
 *
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

	/**
	 * 视图解析器
	 */
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/page/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	/**
	 * 注册拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		// Tips: 注册拦截器,可以注册多个拦截器,实现拦截器链

		// 1.注册接口鉴权拦截器
		registry.addInterceptor(new AuthenticateInterceptor()).addPathPatterns("/**")
				.excludePathPatterns("/common/errorjson").excludePathPatterns("/common/errorpage")
				.excludePathPatterns("/error").excludePathPatterns("/test/**")
				.excludePathPatterns("/scard/rechargecallback/**").excludePathPatterns("/scard/rechargenotify/**");

		// 2.安全防护拦截器(防刷) PS:启用防刷还需配置controller配置注解
		registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/**");

		super.addInterceptors(registry);
	}

	/**
	 * 跨域过滤器, Spring通过CROS协议解决跨域问题
	 * @return
	 */
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", buildConfig()); // 4
		return new CorsFilter(source);
	}

	private CorsConfiguration buildConfig() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*"); // 1
		corsConfiguration.addAllowedHeader("*"); // 2
		corsConfiguration.addAllowedMethod("*"); // 3
		return corsConfiguration;
	}

	/**
	 * 注册过滤器
	 * @return
	 */
	@Bean
	public FilterRegistrationBean requestCacheFilter() {

		// request缓存拦截器
		RequestCacheFilter cacheFilter = new RequestCacheFilter();

		// 拦截器容器
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setEnabled(true); // 开启拦截器
		registrationBean.setFilter(cacheFilter); // request缓存拦截器
		registrationBean.addUrlPatterns("/*"); // 设置路由
		registrationBean.setOrder(1); // 设置拦截器优先级

		return registrationBean;
	}
}
