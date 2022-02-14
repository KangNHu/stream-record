package org.codingeasy.streamrecord.core;

import org.aopalliance.intercept.MethodInvocation;
import org.codingeasy.streamrecord.core.matedata.RecordDefinition;

/**
 * 当前上下文
 *
 * @author : KangNing Hu
 */
public class CurrentContext {

  /**
   * 记录定义注册器
   */
  private RecordDefinitionRegistry recordDefinitionRegistry;

  /**
   * 当前参数属性
   */
  private AttributeAccess attributeAccess;

  /**
   * 拦截方法包装
   */
  private InterceptMethodWrapper interceptMethodWrapper;


  /**
   * 当前上下文定义
   */
  private RecordDefinition recordDefinition;


  /**
   * 上下文
   */
  private Pipeline pipeline;


  /**
   * 记录生产器
   */
  private RecordProducer recordProducer;

  /**
   * 处理策略
   */
  private String processorStrategyName;


  /**
   * 执行异常
   */
  private Throwable throwable;

  /**
   * 业务执行的开始时间
   */
  private Long startTime;

  /**
   * 业务执行的结束时间
   */
  private Long endTime;


  public CurrentContext(RecordDefinitionRegistry recordDefinitionRegistry,
      AttributeAccess attributeAccess, InterceptMethodWrapper interceptMethodWrapper) {
    this.recordDefinitionRegistry = recordDefinitionRegistry;
    this.attributeAccess = attributeAccess;
    this.interceptMethodWrapper = interceptMethodWrapper;
  }

  /**
   * 解析record定义
   */
  void parseRecordDefinition(ComponentFactory componentFactory) {
    this.recordDefinition = recordDefinitionRegistry
        .getRecordDefinition(interceptMethodWrapper.getTarget().getClass(),
            this.interceptMethodWrapper.getMethod());
    if (this.recordDefinition == null) {
      throw new IllegalStateException("当前方法没有对应的RecordDefinition");
    }
    //创建记录生成器
    Class<? extends RecordProducer> recordProducerClass = this.recordDefinition
        .getRecordProducerClass();
    this.recordProducer = (RecordProducer) componentFactory.createComponent(recordProducerClass);
    //创建管道
    Class<? extends Pipeline> pipelineClass = this.recordDefinition.getPipelineClass();
    this.pipeline = (Pipeline) componentFactory.createComponent(pipelineClass);
    //处理策略
    this.processorStrategyName = this.recordDefinition.getProcessorStrategy();
  }

  /**
   * 方法调用
   */
  public Object invoke() throws Throwable {
    this.startTime = System.currentTimeMillis();
    try {
      MethodInvocation methodInvocation = this.interceptMethodWrapper.getMethodInvocation();
      return methodInvocation.proceed();
    } finally {
      this.endTime = System.currentTimeMillis();
    }
  }


  /**
   * 是否有异常
   *
   * @return 有异常返回true，否则返回false
   */
  public boolean hasException() {
    return this.throwable != null;
  }

  /**
   * 获取业务执行的持续时间
   *
   * @return 返回业务方法执行的持续时间， 如果执行通知类型为{@link org.codingeasy.streamrecord.core.matedata.Advice#BEFORE}
   * 则持续时间返回-1
   */
  public Long getDuration() {
    if (this.endTime == null || this.startTime == null) {
      return 0L;
    }
    return this.endTime - this.startTime;
  }

  public Long getStartTime() {
    return startTime;
  }


  public Long getEndTime() {
    return endTime;
  }


  public RecordDefinitionRegistry getRecordDefinitionRegistry() {
    return recordDefinitionRegistry;
  }

  public AttributeAccess getAttributeAccess() {
    return attributeAccess;
  }

  public InterceptMethodWrapper getInterceptMethodWrapper() {
    return interceptMethodWrapper;
  }

  public RecordDefinition getRecordDefinition() {
    return recordDefinition;
  }

  public Throwable getThrowable() {
    return throwable;
  }

  public void setThrowable(Throwable throwable) {
    this.throwable = throwable;
  }

  public Pipeline getPipeline() {
    return pipeline;
  }

  public RecordProducer getRecordProducer() {
    return recordProducer;
  }

  public String getProcessorStrategyName() {
    return processorStrategyName;
  }
}
