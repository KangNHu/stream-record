package org.dream.tool.auditlog.support;

import org.dream.tool.auditlog.annotation.AuditLog;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;

import java.lang.reflect.Method;

/**
* 审计日志条件  
* @author : KangNing Hu
*/
public class AuditLogPointcut implements Pointcut {


	private AuditLogMethodMatcher auditLogMethodMatcher = new AuditLogMethodMatcher();

	@Override
	public ClassFilter getClassFilter() {
		return clazz-> true;
	}


	@Override
	public MethodMatcher getMethodMatcher() {
		return auditLogMethodMatcher;
	}

	class  AuditLogMethodMatcher implements MethodMatcher{

		@Override
		public boolean matches(Method method, Class<?> targetClass) {
			return method.isAnnotationPresent(AuditLog.class);
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
