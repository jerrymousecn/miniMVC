package cn.jerry.mini_mvc.example.dao;

import cn.jerry.mini_mvc.example.domain.Student;

public interface StudentDao {
	public Student getStudentByName(String name);
}
