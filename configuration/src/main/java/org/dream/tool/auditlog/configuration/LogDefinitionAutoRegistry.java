package org.dream.tool.auditlog.configuration;

import org.dream.tool.auditlog.core.annotation.AuditLog;
import org.dream.tool.auditlog.core.matedata.LogDefinition;
import org.dream.tool.auditlog.core.matedata.LogDefinitionBuilder;
import org.dream.tool.auditlog.core.support.AuditLogPointcutAdvisor;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 日志定义自动注册器
 *
 * @author : KangNing Hu
 */
public class LogDefinitionAutoRegistry extends AuditLogPointcutAdvisor implements InstantiationAwareBeanPostProcessor  {

	private Logger logger = Logger.getLogger(LogDefinitionAutoRegistry.class.getName());


	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		Class<?> beanClass = bean.getClass();
		if (AopUtils.isAopProxy(bean)){
			 beanClass = AopUtils.getTargetClass(bean);
		}
		//判断是否为log definition class
		if (isNotLogDefinitionClass(beanClass)){
			return false;
		}
		Class targetClass = beanClass;
		//注册log definition
		do {
			final Class finalTargetClass = targetClass;
			ReflectionUtils.doWithMethods(beanClass, method -> {
				if (method.isAnnotationPresent(AuditLog.class)) {
					LogDefinition logDefinition = buildAudiLogDefinition(finalTargetClass, method);
					logger.log(Level.INFO, String.format("注册日志定义 -> %s", logDefinition.toString()));
					this.register(logDefinition);
				}
			});
			//遍历父类方法
			targetClass = beanClass.getSuperclass();
		} while (targetClass != null  && targetClass != Object.class);
		return false;
	}






	/**
	 * 构建log 定义
	 *
	 * @param beanClass
	 * @param method
	 * @return
	 */
	private LogDefinition buildAudiLogDefinition(Class<?> beanClass, Method method) {
		LogDefinitionBuilder logDefinitionBuilder = new LogDefinitionBuilder(method, beanClass);
		return logDefinitionBuilder.getGenericLogDefinition();
	}


	/**
	 * 校验是否为log definition的class
	 * @param beanClass
	 * @return
	 */
	private boolean isNotLogDefinitionClass(Class<?> beanClass) {
		return !beanClass.isAnnotationPresent(AuditLogService.class);
	}

}
