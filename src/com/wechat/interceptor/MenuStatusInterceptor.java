package com.wechat.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wechat.menu.Module;

/**
 * 
 * @Description 后台页面菜单状态栏，请求后置处理拦截器
 * @ClassName MenuStatusInterceptor.java
 * @author Administrator-zhur
 * @date 2016年7月21日下午2:41:29
 */
public class MenuStatusInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerMethod method = (HandlerMethod) handler;
		Module model = method.getBean().getClass().getAnnotation(Module.class);
		if(model == null){
			request.setAttribute("module", "UserSysManager");
		}else{
			request.setAttribute("module", model.value());
		}
	}

	

}
