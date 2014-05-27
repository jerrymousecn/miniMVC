package cn.jerry.mini_struts;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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


public class MiniStrutsFilter implements Filter {
	private ActionMappings actionMappings;
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
				
				String redirectPagePath = action.invokemethod();
				RequestDispatcher dispatcher = request.getRequestDispatcher(redirectPagePath);
				dispatcher.forward(request, response);
			}
			else
			{
				filterChain.doFilter(servletRequest, servletResponse);
			}
		} finally {
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
		String realPath = filterConfig.getServletContext().getRealPath("/");
		String strutsConfigPath = filterConfig.getInitParameter("config");
		String configPathFullPath = realPath +"WEB-INF"+File.separatorChar+"classes"+File.separatorChar+strutsConfigPath;
		actionMappings = ActionMappings.getInstance();
		try {
			actionMappings.init(configPathFullPath);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

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
