package cn.jerry.mini_mvc.parser;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.jerry.mini_mvc.Constants;

public class ActionParser {
	private Map<String, XMLAction> actionMap = new HashMap<String, XMLAction>();

	public void init(String configFile) throws DocumentException {
		SAXReader reader = new SAXReader();

		File file = new File(configFile);
		Document document = reader.read(file);
		Element root = document.getRootElement();
		for (Iterator i = root.elementIterator("package"); i.hasNext();) {
			Element packageNode = (Element) i.next();
			for (Iterator j = packageNode.elementIterator("action"); j
					.hasNext();) {
				XMLAction action = new XMLAction();
				Element actionNode = (Element) j.next();
				String actionName = actionNode.attribute("name").getText();
				String className = actionNode.attribute("class").getText();
				String methodName = actionNode.attribute("method").getText();
				if (methodName == null)
					methodName = Constants.DEFAULT_METHOD_NAME;
				action.setClassName(className);
				action.setName(actionName);
				action.setMethodName(methodName);
				Map<String, String> resultMap = new HashMap<String, String>();
				for (Iterator k = actionNode.elementIterator("result"); k
						.hasNext();) {
					Element resultNode = (Element) k.next();
					String resultName = resultNode.attribute("name").getText();
					String resultURL = resultNode.getText();
					resultMap.put(resultName, resultURL);
				}
				action.setResultMap(resultMap);
				actionMap.put(actionName, action);
			}
		}
	}

	public Map<String, XMLAction> getActionMap() {
		return actionMap;
	}
}
