package cn.jerry.mini_mvc;

import org.dom4j.DocumentException;

public interface ObjectFactory {
	void init(String configFile) throws DocumentException;
	Object getInstanceByBeanName(String beanName) throws Exception;
	Object getInstance(Object obj) throws Exception;
}
