package cn.jerry.mini_mvc.example.action;

import cn.jerry.mini_mvc.ActionContext;
import cn.jerry.mini_mvc.example.dao.ITestDao;

public class TestAction {
	private ITestDao testDao;
	private String name;
	public String execute()
	{
		testDao.test1();
		ActionContext context = ActionContext.getContext();
		context.setAttriInRequest("message", "Get Info from request: Dear "+name+", welcome to miniMVC.");
		context.setAttriInSession("message", "Get Info from session: Dear "+name+", welcome to miniMVC.");
		return "success";
	}
}
