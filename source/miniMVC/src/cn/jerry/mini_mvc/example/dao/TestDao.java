package cn.jerry.mini_mvc.example.dao;

public class TestDao implements ITestDao{
	private String configFile;
	public TestDao()
	{
	}
	public void test1()
	{
		System.out.println("test1 in TestDao ...");
	}
}
