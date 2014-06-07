package cn.jerry.mini_mvc.example.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.jerry.mini_mvc.example.domain.Student;
import cn.jerry.mini_mvc.plugins.mybatis.MybatisSessionFactory;


public class StudentDaoImpl implements StudentDao {
	private SqlSession session = null;
	private StudentDao mapper = null;
	private MybatisSessionFactory mybatisSessionFactory;
	SqlSessionFactory factory;

	@Override
	public Student getStudentByName(String name) {
		session = mybatisSessionFactory.getSessionFactory().openSession();
		mapper = session.getMapper(StudentDao.class);
		Student student = mapper.getStudentByName(name);
		session.close();
		return student;
	}
	
}
