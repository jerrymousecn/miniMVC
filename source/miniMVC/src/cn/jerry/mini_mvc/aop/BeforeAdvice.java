package cn.jerry.mini_mvc.aop;

import java.lang.reflect.Method;

public interface BeforeAdvice {
	public void before(Object targetObj,Object proxyObj, Method method, Object[] args);
}
