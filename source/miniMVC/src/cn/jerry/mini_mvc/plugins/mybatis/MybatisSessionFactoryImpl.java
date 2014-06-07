package cn.jerry.mini_mvc.plugins.mybatis;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisSessionFactoryImpl implements MybatisSessionFactory {
	private String configFile;
	public MybatisSessionFactoryImpl()
	{
		
	}
	public SqlSessionFactory getSessionFactory() {
		SqlSessionFactory factory = null;
		try {
			Reader reader = null;
			reader = Resources.getResourceAsReader(configFile);

			factory = new SqlSessionFactoryBuilder()
					.build(reader);
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return factory;
	}
	public String getConfigFile() {
		return configFile;
	}
	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}
}
