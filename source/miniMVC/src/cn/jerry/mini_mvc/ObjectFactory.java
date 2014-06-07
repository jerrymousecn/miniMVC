package cn.jerry.mini_mvc;

import org.dom4j.DocumentException;

public interface ObjectFactory {
	public void init(String configFile) throws DocumentException;
	public Object getBean(String beanName) throws Exception;
}
