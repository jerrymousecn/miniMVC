package cn.jerry.mini_mvc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.dom4j.DocumentException;

import cn.jerry.mini_mvc.parser.ActionParser;
import cn.jerry.mini_mvc.parser.XMLAction;

public class ActionMappings {
	private static ActionMappings actionMappings = new ActionMappings();
	private Map<String, XMLAction> actionMap = new HashMap<String, XMLAction>();
	private ActionParser actionParser = new ActionParser();
	private Map<String,String> requestDataMap;
	private Map<String,String> sessionDataMap;
	private ObjectFactory objectFactory;

	private ActionMappings() {
	}
	public static ActionMappings getInstance()
	{
		return actionMappings;
	}
	public void init(String configFile) throws DocumentException
	{
		actionParser.init(configFile);
		actionMap = actionParser.getActionMap();
	}

	private XMLAction getAction(String actionName) {
		return actionMap.get(actionName);
	}
	public String execute(Map requestMap,String actionName) throws Exception
	{
		setRequest(requestMap);
		XMLAction action = getAction(actionName);
		String className = action.getClassName();
		String redirectPagePath = null;
		Class clazz;
		Object obj = null;
		try {
			clazz = Class.forName(className);
			obj = clazz.newInstance();
		} catch (Exception e) {
			obj = objectFactory.getInstanceByBeanName(className);
			clazz = obj.getClass();
		}
		injectData(obj);
		String result = (String)BeanUtil.invokeMethod(obj, action.getMethodName());
		redirectPagePath = action.getResultMap().get(result);
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
	private void injectData(Object obj,String fieldName,Object fieldValue)
	{
		BeanUtil.setBeanProperty(obj, fieldName, fieldValue);
	}
	public Map<String, String> getRequestDataMap() {
		return requestDataMap;
	}
	public Map<String, String> getSessionDataMap() {
		return sessionDataMap;
	}
	public void setSessionDataMap(Map<String, String> sessionDataMap) {
		this.sessionDataMap = sessionDataMap;
	}
	public void setRequest(Map requestParameterMap)
	{
		Map<String,String> map = new HashMap<String,String>();
		Set keySet = requestParameterMap.keySet();
		Iterator iter = keySet.iterator();
		while(iter.hasNext())
		{
			String key = (String)iter.next();
			String[] valueArr = (String[])requestParameterMap.get(key);
			map.put(key, valueArr[0]);
		}
		this.requestDataMap = map;
	}
	public void setObjectFactory(ObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}
}
