package cn.jerry.mini_struts;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

public class Action {
	private String name;
	private String classPath;
	private Map<String,String> resultMap;
	private Map<String,String> requestDataMap;
	private String methodName = "execute";
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
	public Map<String, String> getResultMap() {
		return resultMap;
	}
	public void setResultMap(Map<String, String> resultMap) {
		this.resultMap = resultMap;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Object newInstance()
	{
		return "";
	}
	public String invokemethod()
	{
		String redirectPagePath = null; 
		try {
			Class class1 = Class.forName(classPath);
			Object obj = class1.newInstance();
			injectData(obj);
			Method method = class1.getMethod(methodName);
			String result = (String)method.invoke(obj);
			redirectPagePath = resultMap.get(result);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return redirectPagePath;
	}
	private void injectData(Object obj)
	{
		if(requestDataMap==null)
			return;
		for(Entry<String,String> entry:requestDataMap.entrySet())
		{
			String key = entry.getKey();
			String value = entry.getValue();
			injectData(obj,key,value);
		}
	}
	private void injectData(Object obj,String filedName,Object fieldValue)
	{
		try {
			Class class1 = Class.forName(classPath);
			Field field = class1.getDeclaredField(filedName);
			if(field!=null)
			{
				field.setAccessible(true);
				field.set(obj, fieldValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	public Map<String, String> getRequestDataMap() {
		return requestDataMap;
	}
	public void setRequestDataMap(Map<String, String> requestDataMap) {
		this.requestDataMap = requestDataMap;
	}
	public void setRequest(HttpServletRequest request)
	{
		Map<String,String> map = new HashMap<String,String>();
		Map requestParameterMap = request.getParameterMap();
		Set keySet = requestParameterMap.keySet();
		Iterator iter = keySet.iterator();
		while(iter.hasNext())
		{
			String key = (String)iter.next();
			String[] valueArr = (String[])requestParameterMap.get(key);
			System.out.println("key: "+key);
			map.put(key, valueArr[0]);
		}
		setRequestDataMap(map);
	}
	
}
