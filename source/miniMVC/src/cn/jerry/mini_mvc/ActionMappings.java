package cn.jerry.mini_mvc;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.DocumentException;

import cn.jerry.mini_mvc.parser.ActionParser;

public class ActionMappings {
	private static ActionMappings actionMappings = new ActionMappings();
	private Map<String, Action> actionMap = new HashMap<String, Action>();
	private ActionParser actionParser = new ActionParser();

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

	public Action getAction(String actionName) {
		return actionMap.get(actionName);
	}
}
