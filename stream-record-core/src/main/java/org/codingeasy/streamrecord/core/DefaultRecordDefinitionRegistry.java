package org.codingeasy.streamrecord.core;

import org.codingeasy.streamrecord.core.matedata.RecordDefinition;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * record 定义的默认实现
 *
 * @author : KangNing Hu
 * @see java.util.concurrent.locks.ReentrantReadWriteLock
 * @see ReadWriteLock
 * @see RecordDefinitionRegistry
 * @see RecordDefinition
 */
public class DefaultRecordDefinitionRegistry extends ReadWriteLock implements
    RecordDefinitionRegistry {


  /**
   * key -> record定义
   */
  private Map<String, RecordDefinition> recordDefinitionMap = new HashMap<String, RecordDefinition>(
      16);

  /**
   * 一个有顺序的record定义key列表
   */
  private List<String> cacheKeys = new ArrayList<String>();


  public void register(RecordDefinition recordDefinition) {
    writeExecute(() -> {
      String cacheKey = buildCacheKey(recordDefinition.getMethod(),
          recordDefinition.getTargetClass());
      recordDefinitionMap.put(cacheKey, recordDefinition);
      cacheKeys.add(cacheKey);
      return null;
    });
  }

  public List<RecordDefinition> getRecordDefinitions() {
    return readExecute(() -> {
      List<RecordDefinition> recordDefinitions = new ArrayList<>();
      for (String ck : cacheKeys) {
        recordDefinitions.add(recordDefinitionMap.get(ck));
      }
      return recordDefinitions;
    });
  }

  public RecordDefinition getRecordDefinition(Class clazz, Method method) {
    return readExecute(() -> recordDefinitionMap.get(buildCacheKey(method, clazz)));
  }


  /**
   * 构建缓存key
   */
  private String buildCacheKey(Method method, Class clazz) {
    String methodName = method.toGenericString();
    String paramTypes = Arrays.asList(method.getParameterTypes()).stream().map(Class::getTypeName)
        .collect(Collectors.joining());
    String className = clazz.getTypeName();
    return methodName + ":" + paramTypes + ":" + className;
  }

}
