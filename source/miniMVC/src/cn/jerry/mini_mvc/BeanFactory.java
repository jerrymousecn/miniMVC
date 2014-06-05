package cn.jerry.mini_mvc;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.DocumentException;

import cn.jerry.mini_mvc.parser.BeanParser;

public class BeanFactory {
	private static BeanFactory beanFactory = new BeanFactory();
	private BeanFactory() {
	}
	public static BeanFactory getInstance() {
		return beanFactory;
	}
	private Map<String,Bean> beanMap = new HashMap<String,Bean>();
	public void init(String configFile) throws DocumentException
	{
		BeanParser beanParser = new BeanParser();
		beanParser.init(configFile);
		beanMap = beanParser.getBeanMap();
	}
	public Object getBean(String beanName) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, SecurityException, NoSuchFieldException
	{
		Bean bean = beanMap.get(beanName);
		String classPath = bean.getClassPath();
		Object obj = Class.forName(classPath).newInstance();
		if(bean.hasRefBeans())
		{
			Map<String, String> map = bean.getRefBeanMap();
			for(Entry<String,String> entry : map.entrySet())
			{
				String propertyName = entry.getKey();
				String refBeanName = entry.getValue();
				Object refBean = getBean(refBeanName);
				setBean(obj,propertyName,refBean);
			}
			
		}
		return obj;
	}
	public void setBean(Object obj,String propertyName,Object valueObj) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException
	{
		Class clazz = obj.getClass();
		Field field = clazz.getDeclaredField(propertyName);
		field.setAccessible(true);
		field.set(obj, valueObj);
	}
}
