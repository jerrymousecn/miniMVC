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
		setTargetObj(targetObj);
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetObj.getClass());
		enhancer.setCallback(this);
		Object proxyObj =  enhancer.create();
		BeanUtil.copyBeanProperties(targetObj, proxyObj);
		return proxyObj;
	}
	public Object getInstance(Class targetClass) {
		setTargetClass(targetClass);
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetClass);
		enhancer.setCallback(this);
		Object proxyObj =  enhancer.create();
		return proxyObj;
	}

	@Override
	public Object intercept(Object proxyObj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		initAdvices(getTargetClass(), proxyObj, method, args);
		
		execBeforeAdvice(getTargetClass(), proxyObj, method, args);
		execAroundBeforeAdvice(getTargetClass(), proxyObj, method, args);
		Object resultObj = proxy.invokeSuper(proxyObj, args);
		execAroundAfterAdvice(getTargetClass(), proxyObj, method, args);
		execAfterAdvice(getTargetClass(), proxyObj, method, args);
		return resultObj;
	}
}
