package org.codingeasy.streamrecord.core.support;

import org.codingeasy.streamrecord.core.annotation.Record;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;

import java.lang.reflect.Method;

/**
* 生成记录条件
* @author : KangNing Hu
*/
public class RecordPointcut implements Pointcut {


	private RecordMethodMatcher recordMethodMatcher = new RecordMethodMatcher();

	@Override
	public ClassFilter getClassFilter() {
		return clazz-> true;
	}


	@Override
	public MethodMatcher getMethodMatcher() {
		return recordMethodMatcher;
	}

	class RecordMethodMatcher implements MethodMatcher{

		@Override
		public boolean matches(Method method, Class<?> targetClass) {
			return method.isAnnotationPresent(Record.class);
		}

		@Override
		public boolean isRuntime() {
			return false;
		}

		@Override
		public boolean matches(Method method, Class<?> targetClass, Object... args) {
			return false;
		}
	}
}
