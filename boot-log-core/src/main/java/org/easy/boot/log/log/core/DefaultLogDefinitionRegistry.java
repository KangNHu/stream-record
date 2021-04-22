package org.easy.boot.log.log.core;

import org.easy.boot.log.log.core.matedata.LogDefinition;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
* log定义的默认实现
 * @see java.util.concurrent.locks.ReentrantReadWriteLock
 * @see ReadWriteLock
 * @see LogDefinitionRegistry
 * @see LogDefinition
* @author : KangNing Hu
*/
public class DefaultLogDefinitionRegistry extends ReadWriteLock implements LogDefinitionRegistry {


	/**
	 * key -> log定义
	 */
	private Map<String , LogDefinition> logDefinitionMap = new HashMap<String, LogDefinition>(16);

	/**
	 * 一个有顺序的log定义key列表
	 */
	private List<String> cacheKeys = new ArrayList<String>();


	public void register(LogDefinition logDefinition) {
		writeExecute(() -> {
			String cacheKey = buildCacheKey(logDefinition.getMethod() , logDefinition.getTargetClass());
			logDefinitionMap.put( cacheKey, logDefinition);
			cacheKeys.add(cacheKey);
			return null;
		});
	}

	public List<LogDefinition> getLogDefinitions() {
		return readExecute(() -> {
			List<LogDefinition> logDefinitions = new ArrayList<>();
			for (String ck : cacheKeys){
				logDefinitions.add(logDefinitionMap.get(ck));
			}
			return logDefinitions;
		});
	}

	public LogDefinition getLogDefinition(Class clazz , Method method ) {
		return readExecute(() -> logDefinitionMap.get(buildCacheKey(method, clazz)));
	}


	/**
	 * 构建缓存key
	 * @return
	 */
	private String buildCacheKey(Method method , Class clazz){
		String methodName = method.toGenericString();
		String paramTypes = Arrays.asList(method.getParameterTypes()).stream().map(Class::getTypeName).collect(Collectors.joining());
		String className = clazz.getTypeName();
		return methodName+":"+paramTypes+":"+className;
	}

}
