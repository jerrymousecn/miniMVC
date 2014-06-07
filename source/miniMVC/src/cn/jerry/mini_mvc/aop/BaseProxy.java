package cn.jerry.mini_mvc.aop;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jerry.mini_mvc.BeanFactory;

public abstract class BaseProxy {
	protected Object targetObj;
	protected BeforeAdvice beforeAdvice;
	protected AfterAdvice afterAdvice;
	protected AroundAdvice aroundAdvice;
	protected Map<String, AopAspect> aopAspectMap;

	public BaseProxy(Map<String, AopAspect> aopAspectMap) {
		this.aopAspectMap = aopAspectMap;
	}
	private void resetAdvices()
	{
		beforeAdvice = null;
		afterAdvice = null;
		aroundAdvice = null;
	}

	protected void initAdvices(Object targetObj, Object proxyObj,
			Method method, Object[] args) throws Exception {
		resetAdvices();
		
		String classFullPath = targetObj.getClass().getCanonicalName();
		String methodName = method.getName();
		for (Entry<String, AopAspect> entry : aopAspectMap.entrySet()) {
			AopAspect aopAspect = entry.getValue();
			String classPattern = preparePattern(aopAspect.getClasses());
			String methodPattern = preparePattern(aopAspect.getMethod());
			if(isMatch(classFullPath, methodName, classPattern, methodPattern))
			{
				BeanFactory beanFactory = BeanFactory.getInstance();
				beforeAdvice = (BeforeAdvice)beanFactory.getBean(aopAspect.getBeforeAdvice());
				afterAdvice = (AfterAdvice)beanFactory.getBean(aopAspect.getAfterAdvice());
				aroundAdvice = (AroundAdvice)beanFactory.getBean(aopAspect.getAroundAdvice());
				break;
			}
		}
	}
	public boolean isClassAccepted(Class clazz)
	{
		String classFullPath = clazz.getCanonicalName();
		for (Entry<String, AopAspect> entry : aopAspectMap.entrySet()) {
			AopAspect aopAspect = entry.getValue();
			String classPattern = preparePattern(aopAspect.getClasses());
			if(isMatch(classFullPath, classPattern))
			{
				return true;
			}
		}
		
		return false;
	}

	private boolean isMatch(String classFullPath, String methodName,
			String classPattern, String methodPattern) {
		if (isMatch(classFullPath, classPattern)
				&& isMatch(methodName, methodPattern))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private boolean isMatch(String srcStr, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(srcStr);
		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}

	private String preparePattern(String inputPattern) {
		inputPattern = "^"+inputPattern+"$";
		return inputPattern.replaceAll("\\*", ".*");
	}

	public abstract Object getInstance(Object targetObj);

	protected void execBeforeAdvice(Object targetObj, Object proxyObj,
			Method method, Object[] args) {
		try {
			if (beforeAdvice != null)
				beforeAdvice.before(targetObj, proxyObj, method, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected boolean isToIntercept() {
		return false;
	}

	protected void execAfterAdvice(Object targetObj, Object proxyObj,
			Method method, Object[] args) {
		try {
			if (afterAdvice != null)
				afterAdvice.after(targetObj, proxyObj, method, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void execAroundBeforeAdvice(Object targetObj, Object proxyObj,
			Method method, Object[] args) {
		try {
			if (aroundAdvice != null)
				aroundAdvice.before(targetObj, proxyObj, method, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void execAroundAfterAdvice(Object targetObj, Object proxyObj,
			Method method, Object[] args) {
		try {
			if (aroundAdvice != null)
				aroundAdvice.after(targetObj, proxyObj, method, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setBeforeAdvice(BeforeAdvice beforeAdvice) {
		this.beforeAdvice = beforeAdvice;
	}

	public void setAfterAdvice(AfterAdvice afterAdvice) {
		this.afterAdvice = afterAdvice;
	}

	public void setAroundAdvice(AroundAdvice aroundAdvice) {
		this.aroundAdvice = aroundAdvice;
	}

}
