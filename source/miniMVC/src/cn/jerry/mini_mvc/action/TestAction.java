package cn.jerry.mini_mvc.action;

import cn.jerry.mini_mvc.ActionContext;

public class TestAction {
	private String name;
	public String execute()
	{
		ActionContext context = ActionContext.getContext();
		context.setAttriInRequest("message", "Get Info from request: Dear "+name+", welcome to miniMVC.");
		context.setAttriInSession("message", "Get Info from session: Dear "+name+", welcome to miniMVC.");
		return "success";
	}
}
