package cn.jerry.mini_mvc.aop.example;

import java.lang.reflect.Method;
import java.util.Date;

import cn.jerry.mini_mvc.aop.AroundAdvice;

public class AroundAdviceImpl1 implements AroundAdvice {
	private Date enterMethodTime;
	private Date leaveMethodTime;
	@Override
	public void before(Object targetObj, Object proxyObj, Method method,
			Object[] args) {
		enterMethodTime = new Date();
		StringBuffer sb = new StringBuffer();
		sb.append(this.getClass().getSimpleName());
		sb.append(" targetObj: ");
		sb.append(targetObj.getClass().getSimpleName());
		sb.append(" method: ");
		sb.append(method.getName());
		System.out.println(sb.toString());
	}
	
	@Override
	public void after(Object targetObj, Object proxyObj, Method method,
			Object[] args) {
		
		leaveMethodTime = new Date();
		long timeElapsed = getTimeDiff(enterMethodTime,leaveMethodTime);
		StringBuffer sb = new StringBuffer();
		sb.append(this.getClass().getSimpleName());
		sb.append(" targetObj: ");
		sb.append(targetObj.getClass().getSimpleName());
		sb.append(" method: ");
		sb.append(method.getName());
		System.out.println(sb.toString());
		System.out.println("Time Elapsed: "+timeElapsed+" ms");
	}
	private long getTimeDiff(Date startDate,Date endDate)
	{
		return endDate.getTime()-startDate.getTime();
	}
}
