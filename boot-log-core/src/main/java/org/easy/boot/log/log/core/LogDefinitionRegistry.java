package org.easy.boot.log.log.core;

import org.easy.boot.log.log.core.matedata.LogDefinition;

import java.lang.reflect.Method;
import java.util.List;

/**
 * log 定义注册器
 * @see  LogDefinition
 * @see DefaultLogDefinitionRegistry
 * @author hukangning
 */
public interface LogDefinitionRegistry {

	/**
	 * 注册log 定于
	 * @param logDefinition log 定于
	 */
	void register(LogDefinition logDefinition);


	/**
	 * 返回所有log定义
	 * @return 返回当前注册表中的所有log定义
	 */
	List<LogDefinition> getLogDefinitions();


	/**
	 * 返回指定log定义
	 * @param method 被记录的方法
	 * @return 被记录方法的log定义
	 */
	LogDefinition getLogDefinition(Class clazz , Method method );



}
