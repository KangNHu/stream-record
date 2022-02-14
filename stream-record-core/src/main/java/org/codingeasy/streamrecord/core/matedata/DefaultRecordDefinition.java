package org.codingeasy.streamrecord.core.matedata;

import org.codingeasy.streamrecord.core.AttributeAccess;
import org.codingeasy.streamrecord.core.RecordProducer;
import org.codingeasy.streamrecord.core.Pipeline;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * 默认的记录定义
 *
 * @author : KangNing Hu
 */
public class DefaultRecordDefinition extends AttributeAccess implements RecordDefinition {

  private Class<?> targetClass;

  private Method method;


  private String temple;


  private boolean async;


  private String processorStrategy;


  private Class<? extends RecordProducer> recordProducerClass;


  private Class<? extends Pipeline> pipelineClass;


  private Advice advice;


  private List<ParamNode>[] params;


  private String name;


  public void setTargetClass(Class<?> targetClass) {
    this.targetClass = targetClass;
  }

  public void setMethod(Method method) {
    this.method = method;
  }

  public void setTemple(String temple) {
    this.temple = temple;
  }

  public void setAsync(boolean async) {
    this.async = async;
  }

  public void setProcessorStrategy(String processorStrategy) {
    this.processorStrategy = processorStrategy;
  }

  public void setRecordProducerClass(Class<? extends RecordProducer> recordProducerClass) {
    this.recordProducerClass = recordProducerClass;
  }

  public void setPipelineClass(Class<? extends Pipeline> pipelineClass) {
    this.pipelineClass = pipelineClass;
  }

  public void setAdvice(Advice advice) {
    this.advice = advice;
  }

  public void setParams(List<ParamNode>[] params) {
    this.params = params;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public Class<?> getTargetClass() {
    return this.targetClass;
  }


  @Override
  public Method getMethod() {
    return this.method;
  }

  @Override
  public String getTemple() {
    return this.temple;
  }

  @Override
  public Boolean isAsync() {
    return this.async;
  }

  @Override
  public String getProcessorStrategy() {
    return this.processorStrategy;
  }

  @Override
  public Class<? extends RecordProducer> getRecordProducerClass() {
    return this.recordProducerClass;
  }

  @Override
  public Class<? extends Pipeline> getPipelineClass() {
    return this.pipelineClass;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public List<ParamNode>[] getParams() {
    return this.params;
  }

  @Override
  public Advice getAdvice() {
    return this.advice;
  }


  @Override
  public String toString() {
    return "DefaultRecordDefinition{" +
        "targetClass=" + targetClass +
        ", method=" + method +
        ", temple='" + temple + '\'' +
        ", async=" + async +
        ", processorStrategy='" + processorStrategy + '\'' +
        ", recordProducerClass=" + recordProducerClass +
        ", pipelineClass=" + pipelineClass +
        ", advice=" + advice +
        ", params=" + Arrays.toString(params) +
        ", name='" + name + '\'' +
        "} " + super.toString();
  }
}
