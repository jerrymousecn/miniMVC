package cn.jerry.mini_mvc.example.action;

import cn.jerry.mini_mvc.ActionContext;
import cn.jerry.mini_mvc.example.dao.StudentDao;
import cn.jerry.mini_mvc.example.domain.Student;


public class StudentAction {
	private StudentDao studentDao;
	private String name;
	public String execute()
	{
		ActionContext context = ActionContext.getContext();
		Student student = studentDao.getStudentByName(name);
		context.setAttriInRequest("nickName", student.getNickName());
		return "success";
	}
}
