package cn.jerry.mini_mvc.aop.example;

import java.lang.reflect.Method;

import cn.jerry.mini_mvc.aop.BeforeAdvice;

public class BeforeAdviceImpl1 implements BeforeAdvice {

	@Override
	public void before(Object targetObj, Object proxyObj, Method method,
			Object[] args) {
		StringBuffer sb = new StringBuffer();
		sb.append(this.getClass().getSimpleName());
		sb.append(" targetObj: ");
		sb.append(targetObj.getClass().getSimpleName());
		sb.append(" method: ");
		sb.append(method.getName());
		System.out.println(sb.toString());
	}
}
