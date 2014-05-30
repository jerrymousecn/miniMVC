package cn.jerry.mini_mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionContext {
	private static ThreadLocal<ActionContext> ctx= new ThreadLocal<ActionContext>();
	private HttpServletRequest request;
	private HttpServletResponse response;
	public ActionContext(HttpServletRequest request,HttpServletResponse response)
	{
		this.request = request;
		this.response = response;
	}
	
	public static void setContext(ActionContext _actionContext)
	{
		ctx.set(_actionContext);
	}
	public static ActionContext getContext()
	{
		return ctx.get();
	}
	public void setAttriInRequest(String key,String value)
	{
		request.setAttribute(key,value);
	}
	public void setAttriInSession(String key,String value)
	{
		request.getSession().setAttribute(key,value);
	}
	public static void clearUp()
	{
		ctx.remove();
	}
}
