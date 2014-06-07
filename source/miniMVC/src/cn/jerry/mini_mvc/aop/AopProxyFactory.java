package cn.jerry.mini_mvc.aop;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.DocumentException;

import cn.jerry.mini_mvc.BeanFactory;
import cn.jerry.mini_mvc.Constants;
import cn.jerry.mini_mvc.ObjectFactory;

public class AopProxyFactory implements ObjectFactory{
	private static AopProxyFactory aopProxyFactory = new AopProxyFactory();
	private Map<String,Object> singletonMap = new HashMap<String,Object>();
	private BaseProxy baseProxy;
	private BeanFactory beanFactory = BeanFactory.getInstance();
	protected AopProxyFactory() {
	}
	public static AopProxyFactory getInstance() {
		return aopProxyFactory;
	}
	@Override
	public void init(String configFile) throws DocumentException
	{
		beanFactory.init(configFile);
		BeanParserWithAOP beanParserWithAOP = new BeanParserWithAOP();
		beanParserWithAOP.init(configFile);
		Map<String, AopAspect> aopAspectMap = beanParserWithAOP.getAopAspectMap();
		baseProxy = getProxy(aopAspectMap);
	}
	@Override
	public Object getBean(String beanName) throws Exception
	{
		Object obj = beanFactory.getBean(beanName);
		if(baseProxy.isClassAccepted(obj.getClass()))
		{
			obj = baseProxy.getInstance(obj);
		}
		return obj;
	}
	public BaseProxy getProxy(Map<String, AopAspect> aopAspectMap)
	{
		return new CgLibProxy(aopAspectMap);
	}
}
