package cn.jerry.mini_mvc.parser;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.jerry.mini_mvc.Bean;
import cn.jerry.mini_mvc.BeanProperty;

public class BeanParser {
	private Map<String, Bean> beanMap = new HashMap<String, Bean>();

	public void init(String configFile) throws DocumentException {
		SAXReader reader = new SAXReader();

		File file = new File(configFile);
		Document document = reader.read(file);
		Element root = document.getRootElement();
		for (Iterator i = root.elementIterator("bean"); i.hasNext();) {
			Bean bean = new Bean();
			Element beanNode = (Element) i.next();
			String beanName = getAttri(beanNode, "name");
			String beanClassPath = getAttri(beanNode, "class");
			String scope = getAttri(beanNode, "scope");
			for (Iterator j = beanNode.elementIterator("property"); j.hasNext();) {
				Element propertyNode = (Element) j.next();
				String propertyName = getAttri(propertyNode, "name");
				String refBeanName = getAttri(propertyNode, "ref");
				String value = getAttri(propertyNode, "value");
				BeanProperty beanProperty = new BeanProperty(propertyName,
						refBeanName, value);
				bean.addProperty(propertyName, beanProperty);
			}
			bean.setName(beanName);
			bean.setClassPath(beanClassPath);
			bean.setScope(scope);
			beanMap.put(beanName, bean);
		}
	}

	private String getAttri(Element node, String attriName) {
		String value = null;
		if (node != null && node.attribute(attriName) != null) {
			value = node.attribute(attriName).getText();
		}
		return value;
	}

	public Map<String, Bean> getBeanMap() {
		return beanMap;
	}

}
