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

public class BeanParser {
	private Map<String, Bean> beanMap = new HashMap<String, Bean>();

	public void init(String configFile) throws DocumentException {
		SAXReader reader = new SAXReader();

		File file = new File(configFile);
		if (file.exists()) {
			Document document = reader.read(file);// 读取XML文件
			Element root = document.getRootElement();// 得到根节点
			for (Iterator i = root.elementIterator("bean"); i.hasNext();) {
				Bean bean = new Bean();
				Element beanNode = (Element) i.next();
				String beanName = beanNode.attribute("name").getText();
				String beanClassPath = beanNode.attribute("class").getText();
				String scope = beanNode.attribute("scope").getText();
				for (Iterator j = beanNode.elementIterator("property"); j
						.hasNext();) {
					Element propertyNode = (Element) j.next();
					String propertyName = propertyNode.attribute("name")
							.getText();
					String refBeanName = propertyNode.attribute("ref")
							.getText();
					bean.addRefBean(propertyName, refBeanName);
				}
				bean.setName(beanName);
				bean.setClassPath(beanClassPath);
				bean.setScope(scope);
				beanMap.put(beanName, bean);
			}
		}
	}

	public Map<String, Bean> getBeanMap() {
		return beanMap;
	}

}
