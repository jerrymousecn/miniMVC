package cn.jerry.mini_mvc;

import java.util.HashMap;
import java.util.Map;

public class Bean {
	private String name;
	private String classPath;
	private String scope;
	private Map<String,String> refBeanMap = new HashMap<String,String>();
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
	public boolean hasRefBeans()
	{
		if(refBeanMap==null || refBeanMap.size()==0)
		{
			return false;
		}
		return true;
	}
	public void addRefBean(String propertyName,String refBeanName)
	{
		refBeanMap.put(propertyName, refBeanName);
	}
	public Map<String, String> getRefBeanMap() {
		return refBeanMap;
	}
}
