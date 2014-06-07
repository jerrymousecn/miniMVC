package cn.jerry.mini_mvc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BeanUtil {

	public static void copyBeanProperties(Object sourceBean,
			Object destinationBean) {
		Class<?> parentClass = sourceBean.getClass();
		while (parentClass != null) {
			final Field[] fields = parentClass.getDeclaredFields();
			for (Field field : fields) {
				try {
					field.setAccessible(true);
					field.set(destinationBean, field.get(sourceBean));
//					Object fieldValue = field.get(sourceBean);
//					setBeanProperty(destinationBean,field.getName(),fieldValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			parentClass = parentClass.getSuperclass();
		}
	}

	public static void setBeanProperty(Object obj, String fieldName,
			Object fieldValue) {
		Class<?> parentClass = obj.getClass();
		while (parentClass != null) {
			final Field[] fields = parentClass.getDeclaredFields();
			for (Field field : fields) {
				if (field.getName().equals(fieldName)) {
					try {
						field.setAccessible(true);
						field.set(obj, fieldValue);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
			}
			parentClass = parentClass.getSuperclass();
		}
	}
	
	public static Object invokeMethod(Object obj, String methodName) {
		Object result = null;
		try {
			Method method = obj.getClass().getMethod(methodName);
			result = method.invoke(obj);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}

}
