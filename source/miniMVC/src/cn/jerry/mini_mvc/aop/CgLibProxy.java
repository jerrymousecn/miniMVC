package cn.jerry.mini_mvc.aop;

import java.lang.reflect.Method;
import java.util.Map;

import cn.jerry.mini_mvc.BeanUtil;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CgLibProxy extends BaseProxy implements MethodInterceptor {

	public CgLibProxy(Map<String, AopAspect> aopAspectMap) {
		super(aopAspectMap);
	}
	
	public Object getInstance(Object targetObj) {
		this.targetObj = targetObj;
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(this.targetObj.getClass());
		enhancer.setCallback(this);
		Object proxyObj =  enhancer.create();
		BeanUtil.copyBeanProperties(targetObj, proxyObj);
		return proxyObj;
	}

	@Override
	public Object intercept(Object proxyObj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		initAdvices(targetObj, proxyObj, method, args);
		
		execBeforeAdvice(targetObj, proxyObj, method, args);
		execAroundBeforeAdvice(targetObj, proxyObj, method, args);
		Object resultObj = proxy.invokeSuper(proxyObj, args);
		execAroundAfterAdvice(targetObj, proxyObj, method, args);
		execAfterAdvice(targetObj, proxyObj, method, args);
		return resultObj;
	}
}
