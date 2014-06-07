package cn.jerry.mini_mvc.aop;

import java.lang.reflect.Method;

public interface AfterAdvice {
	public void after(Object targetObj,Object proxyObj, Method method, Object[] args);
}
