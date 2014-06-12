package cn.jerry.mini_mvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.DocumentException;

import cn.jerry.mini_mvc.parser.XMLBean;
import cn.jerry.mini_mvc.parser.BeanParser;
import cn.jerry.mini_mvc.parser.XMLBeanProperty;

public class BeanFactory implements ObjectFactory{
	private static BeanFactory beanFactory = new BeanFactory();
	private Map<String,Object> singletonMap = new HashMap<String,Object>();
	protected BeanFactory() {
	}
	public static BeanFactory getInstance() {
		return beanFactory;
	}
	private Map<String,XMLBean> beanMap = new HashMap<String,XMLBean>();
	
	@Override
	public void init(String configFile) throws DocumentException
	{
		BeanParser beanParser = new BeanParser();
		beanParser.init(configFile);
		beanMap = beanParser.getBeanMap();
	}
	public XMLBean getBean(String beanName) throws Exception {
		XMLBean bean = beanMap.get(beanName);
		return bean;
	}
	@Override
	public Object getInstance(Object obj) throws Exception {
		return obj;
	}
	@Override
	public Object getInstanceByBeanName(String beanName) throws Exception
	{
		if(singletonMap.get(beanName)!=null)
			return singletonMap.get(beanName);
		XMLBean bean = beanMap.get(beanName);
		String className = bean.getClassName();
		Object obj = Class.forName(className).newInstance();
		
		injectObj(bean, obj);
		
		if(bean.isSingleton())
		{
			saveSingletonBean(beanName, obj);
		}
		return obj;
	}
	public void injectObj(XMLBean bean,Object obj) throws Exception
	{
		if(bean.hasProperties())
		{
			Map<String, XMLBeanProperty> map = bean.getPropertyMap();
			for(Entry<String,XMLBeanProperty> entry : map.entrySet())
			{
				String propertyName = entry.getKey();
				XMLBeanProperty beanProperty = entry.getValue();
				Object beanInProperty;
				if(beanProperty.hasRefToOtherBean())
				{
					beanInProperty = getInstanceByBeanName(beanProperty.getRefBeanName());
				}
				else
				{
					beanInProperty = beanProperty.getValue();
				}
				setBean(obj,propertyName,beanInProperty);
			}
		}
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
