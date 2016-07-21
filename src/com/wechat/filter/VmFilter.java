package com.wechat.filter;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.Velocity;

/**
 * 
 * @Description 过滤器VmFilter 主要是对HttpServletRequest进行预处理
 * 1,在request到达Servlet之前，拦截客户的HttpServletRequest
 * 2,根据需要检查HttpServletRequest,也可以修改HttpServletRequest头和数据
 * 3,在HttpServletResponse到达客户端之前，拦截HttpServletResponse
 * 4,根据需要检查HttpServletResponse,也可以修改HttpServletResponse头和数据
 * @ClassName VmFilter.java
 * @author Administrator-zhur
 * @date 2016年7月21日下午2:05:16
 */
public class VmFilter implements Filter {

	public void init(FilterConfig config) throws ServletException {
		try{
			Properties prop = new Properties();
			prop.put("runtime.log", config.getServletContext().getRealPath("/WEB-INF/log/velocity.log"));
			prop.put("file.resource.loader.path", config.getServletContext().getRealPath("/WEB-INF/vm"));
			prop.put("input.encoding", "UTF-8");
			prop.put("output.encoding", "UTF-8");
			Velocity.init(prop);
		}catch( Exception e ){
			e.printStackTrace();
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;
		chain.doFilter(req, rep);
	}
	public void destroy() {
	}
}
