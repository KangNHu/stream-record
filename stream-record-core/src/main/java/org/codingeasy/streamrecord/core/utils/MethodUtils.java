package org.codingeasy.streamrecord.core.utils;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
* 方法相关的工具类  
* @author : KangNing Hu
*/
public class MethodUtils {


	/**
	 * 获取方法形参的名称列表
	 * @param method 目标方法
	 * @return
	 */
	public static String[] getParamNames(Method method){
		//获取被拦截方法参数名列表(使用Spring支持类库)
		LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
		return u.getParameterNames(method);
	}


	/**
	 * 从方法参数中的注解找出指定类型的注解对象
	 * @param paramAnnotations 方法参数注解列表
	 * @param paramIndex 方法参数索引
	 * @param annotationType 需要寻找的注解类型
	 * @param <T> 注解类型
	 * @return 返回 annotationType 的注解对象
	 */
	public static  <T extends Annotation> T findAnnotationByMethodParam(Annotation[][] paramAnnotations , int paramIndex , Class<T> annotationType){
		Annotation[] annotations = paramAnnotations[paramIndex];
		for (Annotation annotation : annotations){
			if (annotation.annotationType().isAssignableFrom(annotationType)){
				return (T) annotation;
			}
		}
		return null;
	}
}
