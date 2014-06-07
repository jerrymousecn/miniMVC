package cn.jerry.mini_mvc.aop;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class BeanParserWithAOP {
	private Map<String, AopAspect> aopAspectMap = new HashMap<String, AopAspect>();

	public void init(String configFile) throws DocumentException {
		SAXReader reader = new SAXReader();

		File file = new File(configFile);
		Document document = reader.read(file);
		Element root = document.getRootElement().element("aop");
		for (Iterator i = root.elementIterator("aspect"); i.hasNext();) {
			AopAspect aopAspect = new AopAspect();
			Element beanNode = (Element) i.next();
			String aspectID = beanNode.attribute("id").getText();
			String aspcetClasses = beanNode.attribute("classes").getText();
			String aspcetMethod = beanNode.attribute("method").getText();
			String beforeAdvice = null;
			String afterAdvice = null;
			String aroundAdvice = null;

			Element beforeAdviceElement = beanNode.element("before");
			Element afterAdviceElement = beanNode.element("after");
			Element aroundAdviceElement = beanNode.element("around");
			beforeAdvice = beforeAdviceElement.attribute("bean-ref").getText();
			afterAdvice = afterAdviceElement.attribute("bean-ref").getText();
			aroundAdvice = aroundAdviceElement.attribute("bean-ref").getText();

			aopAspect.setId(aspectID);
			aopAspect.setMethod(aspcetMethod);
			aopAspect.setClasses(aspcetClasses);
			aopAspect.setBeforeAdvice(beforeAdvice);
			aopAspect.setAfterAdvice(afterAdvice);
			aopAspect.setAroundAdvice(aroundAdvice);

			aopAspectMap.put(aspectID, aopAspect);
		}
	}

	private String getAttri(Element node, String attriName) {
		String value = null;
		if (node != null && node.attribute(attriName) != null) {
			value = node.attribute(attriName).getText();
		}
		return value;
	}

	public Map<String, AopAspect> getAopAspectMap() {
		return aopAspectMap;
	}

	public static void main(String[] args) {
		BeanParserWithAOP beanParserWithAOP = new BeanParserWithAOP();
		try {
			beanParserWithAOP
					.init("E:\\workspace\\miniMVC\\src\\mini-mvc-beans.xml");
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
