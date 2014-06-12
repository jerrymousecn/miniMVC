package cn.jerry.mini_mvc.aop;

import java.lang.reflect.Method;

public interface AfterAdvice {
	void after(Class targetClass,Object proxyObj, Method method, Object[] args);
}
