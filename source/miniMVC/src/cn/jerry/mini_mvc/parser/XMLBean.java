package cn.jerry.mini_mvc.parser;

import java.util.HashMap;
import java.util.Map;

import cn.jerry.mini_mvc.Constants;

public class XMLBean {
	private String name;
	private String className;
	private String scope;
	private Map<String,XMLBeanProperty> propertyMap = new HashMap<String,XMLBeanProperty>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public boolean hasProperties()
	{
		if(propertyMap==null || propertyMap.size()==0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	public void addProperty(String propertyName,XMLBeanProperty beanProperty)
	{
		propertyMap.put(propertyName, beanProperty);
	}
	public Map<String, XMLBeanProperty> getPropertyMap() {
		return propertyMap;
	}
	public boolean isSingleton()
	{
		if(Constants.SCOPE_SINGLETON.equals(scope))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
