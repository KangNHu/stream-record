package org.dream.tool.auditlog;

import org.dream.tool.auditlog.matedata.CacheKey;
import org.dream.tool.auditlog.matedata.LogDefinition;

import java.lang.reflect.Method;
import java.util.*;

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
	 * @see CacheKey
	 */
	private Map<CacheKey , LogDefinition> logDefinitionMap = new HashMap<CacheKey, LogDefinition>(16);

	/**
	 * 一个有顺序的log定义key列表
	 */
	private List<CacheKey> cacheKeys = new ArrayList<CacheKey>();


	public void register(LogDefinition logDefinition) {
		writeExecute(() -> {
			CacheKey cacheKey = new CacheKey(logDefinition.getMethod() , logDefinition.getLogProducerClass());
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

	public LogDefinition getLogDefinition(Class clazz , Method method ) {
		return readExecute(() -> logDefinitionMap.get(new CacheKey(method , clazz)));
	}



}
