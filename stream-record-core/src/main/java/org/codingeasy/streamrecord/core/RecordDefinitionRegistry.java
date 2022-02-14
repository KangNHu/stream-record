package org.codingeasy.streamrecord.core;

import org.codingeasy.streamrecord.core.matedata.RecordDefinition;

import java.lang.reflect.Method;
import java.util.List;

/**
 * record 定义注册器
 *
 * @author hukangning
 * @see RecordDefinition
 * @see DefaultRecordDefinitionRegistry
 */
public interface RecordDefinitionRegistry {

  /**
   * 注册record定义
   *
   * @param recordDefinition record 定义
   */
  void register(RecordDefinition recordDefinition);


  /**
   * 返回所有record定义
   *
   * @return 返回当前注册表中的所有record定义
   */
  List<RecordDefinition> getRecordDefinitions();


  /**
   * 返回指定record定义
   *
   * @param method 被记录的方法
   * @return 被记录方法的record定义
   */
  RecordDefinition getRecordDefinition(Class clazz, Method method);


}
