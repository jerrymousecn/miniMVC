package cn.jerry.mini_mvc;

import java.util.HashMap;
import java.util.Map;

public class Bean {
	private String name;
	private String classPath;
	private String scope;
	private Map<String,BeanProperty> propertyMap = new HashMap<String,BeanProperty>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassPath() {
		return classPath;
	}
	public void setClassPath(String classPath) {
		this.classPath = classPath;
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
	public void addProperty(String propertyName,BeanProperty beanProperty)
	{
		propertyMap.put(propertyName, beanProperty);
	}
	public Map<String, BeanProperty> getPropertyMap() {
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
