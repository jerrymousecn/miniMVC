package cn.jerry.mini_struts.action;

import cn.jerry.mini_struts.ActionContext;

public class TestAction {
	private String name;
	public String execute()
	{
		ActionContext context = ActionContext.getContext();
		context.setAttriInRequest("message", "Dear "+name+", welcome to miniMVC.");
		return "success";
	}
}
