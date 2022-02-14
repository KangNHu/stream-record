package org.codingeasy.streamrecord.core;

import org.codingeasy.streamrecord.core.matedata.RecordDefinition;
import org.codingeasy.streamrecord.core.processor.RecordPostProcessor;
import org.codingeasy.streamrecord.core.processor.ProcessorStrategy;
import org.codingeasy.streamrecord.core.processor.TemplateResolve;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * 组件注册器
 *
 * @author : KangNing Hu
 */
public interface ComponentRegistry extends RecordDefinitionRegistry {

  /**
   * 设置处理测序
   */
  void addProcessorStrategy(ProcessorStrategy processorStrategy);

  /**
   * 添加上下文生命周期钩子
   */
  void addRecordPostProcessor(RecordPostProcessor recordPostProcessor);

  /**
   * 设置模板解析器
   */
  void setTemplateResolve(TemplateResolve templateResolve);

  /**
   * 设置参数解析器
   */
  void setParamParse(ParamParse paramParse);

  /**
   * 设置 异步处理的线程池
   */
  void setExecutor(Executor executor);

  /**
   * 设置组件工程
   */
  void setComponentFactory(ComponentFactory componentFactory);

  /**
   * 设置默认记录生成器
   */
  void setDefaultRecordProducer(RecordProducer defaultRecordProducer);

  /**
   * 生成默认管道
   */
  void setDefaultPipeline(Pipeline defaultPipeline);


  /**
   * 初始化组件
   */
  void initComponent();

  /**
   * 返回所有record定义
   *
   * @return 返回当前注册表中的所有record定义
   */
  default List<RecordDefinition> getRecordDefinitions() {
    throw new IllegalStateException("当前对象非记录定义注册器的实现者");
  }


  /**
   * 返回指定record定义
   *
   * @param method 被记录的方法
   * @return 被记录方法的record定义
   */
  default RecordDefinition getRecordDefinition(Class clazz, Method method) {
    throw new IllegalStateException("当前对象非记录定义注册器的实现者");
  }

}
