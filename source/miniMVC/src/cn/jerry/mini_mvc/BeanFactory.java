package cn.jerry.mini_mvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.dom4j.DocumentException;
import cn.jerry.mini_mvc.parser.BeanParser;

public class BeanFactory implements ObjectFactory{
	private static BeanFactory beanFactory = new BeanFactory();
	private Map<String,Object> singletonMap = new HashMap<String,Object>();
	protected BeanFactory() {
	}
	public static BeanFactory getInstance() {
		return beanFactory;
	}
	private Map<String,Bean> beanMap = new HashMap<String,Bean>();
	
	@Override
	public void init(String configFile) throws DocumentException
	{
		BeanParser beanParser = new BeanParser();
		beanParser.init(configFile);
		beanMap = beanParser.getBeanMap();
	}
	@Override
	public Object getBean(String beanName) throws Exception
	{
		if(singletonMap.get(beanName)!=null)
			return singletonMap.get(beanName);
		Bean bean = beanMap.get(beanName);
		String classPath = bean.getClassPath();
		Object obj = Class.forName(classPath).newInstance();
		if(bean.hasProperties())
		{
			Map<String, BeanProperty> map = bean.getPropertyMap();
			for(Entry<String,BeanProperty> entry : map.entrySet())
			{
				String propertyName = entry.getKey();
				BeanProperty beanProperty = entry.getValue();
				Object beanInProperty;
				if(beanProperty.hasRefToOtherBean())
				{
					beanInProperty = getBean(beanProperty.getRefBeanName());
				}
				else
				{
					beanInProperty = beanProperty.getValue();
				}
				setBean(obj,propertyName,beanInProperty);
			}
		}
		if(bean.isSingleton())
		{
			saveSingletonBean(beanName, obj);
		}
		return obj;
	}
	private void saveSingletonBean(String beanName,Object obj)
	{
		singletonMap.put(beanName, obj);
	}
	public void setBean(Object obj,String fieldName,Object fieldValue) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException
	{
		BeanUtil.setBeanProperty(obj, fieldName, fieldValue);
	}
}
