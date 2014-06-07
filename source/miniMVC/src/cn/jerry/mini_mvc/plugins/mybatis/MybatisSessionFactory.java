package cn.jerry.mini_mvc.plugins.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;

public interface MybatisSessionFactory {
	public SqlSessionFactory getSessionFactory();
}
