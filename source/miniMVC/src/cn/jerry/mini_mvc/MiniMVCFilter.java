package cn.jerry.mini_mvc;
import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import cn.jerry.mini_mvc.aop.AopProxyFactory;


public class MiniMVCFilter implements Filter {
	private ActionMappings actionMappings;
	private ObjectFactory objectFactory;
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		ActionContext actionContext = new ActionContext(request,response);
		ActionContext.setContext(actionContext);
		try {
			String shortUri = getShortURI(request);
			if(isAcceptedAction(shortUri))
			{
				String actionName = getActionName(shortUri);
				Action action = actionMappings.getAction(actionName);
				action.setRequest(request);
				action.setObjectFactory(objectFactory);
				
				String redirectPagePath = action.invokemethod();
				RequestDispatcher dispatcher = request.getRequestDispatcher(redirectPagePath);
				dispatcher.forward(request, response);
			}
			else
			{
				filterChain.doFilter(servletRequest, servletResponse);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
			ActionContext.clearUp();
		}
	}
	
	
	private boolean isAcceptedAction(String shortURI)
	{
		if(shortURI.endsWith(".action"))
			return true;
		return false;
	}
	
	private String getShortURI(HttpServletRequest request)
	{
		String totalURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String shortURI = totalURI.substring(contextPath.length());
		return shortURI;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		initActionMapping(filterConfig);
		initBeanParser(filterConfig);
	}
	private void initActionMapping(FilterConfig filterConfig)
	{
		String configPath = filterConfig.getInitParameter("config");
		String configFullPath = getFullPath(filterConfig,configPath);
		actionMappings = ActionMappings.getInstance();
		try {
			actionMappings.init(configFullPath);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	private void initBeanParser(FilterConfig filterConfig)
	{
		String beanConfigPath = filterConfig.getInitParameter("bean-config");
		String beanConfigFullPath = getFullPath(filterConfig,beanConfigPath);
//		beanFactory = BeanFactory.getInstance();
		objectFactory = AopProxyFactory.getInstance();
		try {
			objectFactory.init(beanConfigFullPath);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	private String getFullPath(FilterConfig filterConfig,String relativePath)
	{
		String realPath = filterConfig.getServletContext().getRealPath("/");
		return realPath +"WEB-INF"+File.separatorChar+"classes"+File.separatorChar+relativePath;
	}
	private String getActionName(String shortUri)
	{
		String actionName = shortUri.substring(1,shortUri.length()-".action".length());
		return actionName;
	}
	@Override
	public void destroy() {

	}


}
