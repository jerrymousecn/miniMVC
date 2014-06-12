package cn.jerry.mini_mvc.aop;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.DocumentException;

import cn.jerry.mini_mvc.BeanFactory;
import cn.jerry.mini_mvc.ObjectFactory;
import cn.jerry.mini_mvc.parser.XMLBean;

public class AopProxyFactory implements ObjectFactory {
	private static AopProxyFactory aopProxyFactory = new AopProxyFactory();
	private Map<String, Object> singletonMap = new HashMap<String, Object>();
	private BaseProxy baseProxy;
	private BeanFactory beanFactory = BeanFactory.getInstance();

	protected AopProxyFactory() {
	}

	public static AopProxyFactory getInstance() {
		return aopProxyFactory;
	}

	@Override
	public void init(String configFile) throws DocumentException {
		beanFactory.init(configFile);
		BeanParserWithAOP beanParserWithAOP = new BeanParserWithAOP();
		beanParserWithAOP.init(configFile);
		Map<String, AopAspect> aopAspectMap = beanParserWithAOP
				.getAopAspectMap();
		baseProxy = getProxy(aopAspectMap);
	}

	@Override
	public Object getInstanceByBeanName(String beanName) throws Exception {
		XMLBean bean = beanFactory.getBean(beanName);
		String className = bean.getClassName();
		Class clazz = Class.forName(className);
		Object obj = null;
		if (baseProxy.isClassAccepted(clazz)) {
			obj = baseProxy.getInstance(clazz);
			beanFactory.injectObj(bean, obj);
		}
		else
		{
			obj = beanFactory.getInstanceByBeanName(beanName);
		}
		return obj;
	}
	@Override
	public Object getInstance(Object obj) throws Exception {
		if (baseProxy.isClassAccepted(obj.getClass())) {
			obj = baseProxy.getInstance(obj);
		}
		return obj;
	}

	public BaseProxy getProxy(Map<String, AopAspect> aopAspectMap) {
		return new CgLibProxy(aopAspectMap);
	}


}
