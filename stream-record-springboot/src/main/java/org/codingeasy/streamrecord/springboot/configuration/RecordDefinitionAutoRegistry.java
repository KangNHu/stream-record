package org.codingeasy.streamrecord.springboot.configuration;

import org.codingeasy.streamrecord.core.annotation.Record;
import org.codingeasy.streamrecord.core.annotation.RecordService;
import org.codingeasy.streamrecord.core.matedata.RecordDefinition;
import org.codingeasy.streamrecord.core.matedata.RecordDefinitionBuilder;
import org.codingeasy.streamrecord.core.support.RecordPointcutAdvisor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * 日志定义自动注册器
 *
 * @author : KangNing Hu
 */
public class RecordDefinitionAutoRegistry extends RecordPointcutAdvisor implements InstantiationAwareBeanPostProcessor  {

	private Logger logger = LoggerFactory.getLogger(RecordDefinitionAutoRegistry.class.getName());


	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		Class<?> beanClass = bean.getClass();
		if (AopUtils.isAopProxy(bean)){
			 beanClass = AopUtils.getTargetClass(bean);
		}
		//判断是否为record definition class
		if (isNotRecordDefinitionClass(beanClass)){
			return true;
		}
		Class targetClass = beanClass;
		//注册record definition
		do {
			final Class finalTargetClass = targetClass;
			ReflectionUtils.doWithMethods(beanClass, method -> {
				if (method.isAnnotationPresent(Record.class)) {
					RecordDefinition recordDefinition = buildRecordDefinition(finalTargetClass, method);
					logger.info("注册记录定义 -> {}", recordDefinition.toString());
					this.register(recordDefinition);
				}
			});
			//遍历父类方法
			targetClass = beanClass.getSuperclass();
		} while (targetClass != null  && targetClass != Object.class);
		return true;
	}






	/**
	 * 构建record 定义
	 *
	 * @param beanClass
	 * @param method
	 * @return
	 */
	private RecordDefinition buildRecordDefinition(Class<?> beanClass, Method method) {
		RecordDefinitionBuilder recordDefinitionBuilder = new RecordDefinitionBuilder(method, beanClass);
		return recordDefinitionBuilder.getGenericRecordDefinition();
	}


	/**
	 * 校验是否为record definition的class
	 * @param beanClass
	 * @return
	 */
	private boolean isNotRecordDefinitionClass(Class<?> beanClass) {
		return !beanClass.isAnnotationPresent(RecordService.class);
	}

}
