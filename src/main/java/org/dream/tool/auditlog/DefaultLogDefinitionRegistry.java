package org.dream.tool.auditlog;

import org.dream.tool.auditlog.processor.ProcessorStrategyProxy;

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
	 * 方法全名称:参数类型名称 -> log定义
	 * @see CacheKey
	 */
	private Map<CacheKey , LogDefinition> logDefinitionMap = new HashMap<CacheKey, LogDefinition>(16);



	/**
	 * 一个有顺序的log定义key列表
	 */
	private List<CacheKey> cacheKeys = new ArrayList<CacheKey>();


	public void register(LogDefinition logDefinition) {
		writeExecute(() -> {
			CacheKey cacheKey = new CacheKey(logDefinition.getMethod());
			logDefinitionMap.put( cacheKey, logDefinition);
			cacheKeys.add(cacheKey);
			return null;
		});
	}

	public List<LogDefinition> getLogDefinitions() {
		return readExecute(() -> {
			List<LogDefinition> logDefinitions = new ArrayList<>();
			for (CacheKey ck : cacheKeys){
				logDefinitions.add(logDefinitionMap.get(ck));
			}
			return logDefinitions;
		});
	}

	public LogDefinition getLogDefinition(Method method) {
		return readExecute(() -> logDefinitionMap.get(new CacheKey(method)));
	}

	/**
	 * 缓存key
	 */
	class CacheKey{
		private String methodName;

		private String paramTypes;


		public CacheKey(Method method){
			this.methodName = method.toGenericString();
			this.paramTypes = Arrays.asList(method.getParameterTypes()).stream().map(Class::getTypeName).collect(Collectors.joining());
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			CacheKey cacheKey = (CacheKey) o;
			return Objects.equals(methodName, cacheKey.methodName) &&
					Objects.equals(paramTypes, cacheKey.paramTypes);
		}

		@Override
		public int hashCode() {
			return Objects.hash(methodName, paramTypes);
		}
	}


}
