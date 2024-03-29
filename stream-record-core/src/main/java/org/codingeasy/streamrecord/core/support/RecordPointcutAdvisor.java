package org.codingeasy.streamrecord.core.support;

import org.aopalliance.aop.Advice;
import org.codingeasy.streamrecord.core.*;
import org.codingeasy.streamrecord.core.annotation.Record;
import org.codingeasy.streamrecord.core.matedata.RecordDefinition;
import org.codingeasy.streamrecord.core.processor.RecordPostProcessor;
import org.codingeasy.streamrecord.core.processor.ProcessorStrategy;
import org.codingeasy.streamrecord.core.processor.TemplateResolve;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

/**
 * 记录条件通知
 *
 * @author : KangNing Hu
 */
public class RecordPointcutAdvisor extends StaticMethodMatcherPointcutAdvisor implements
    ComponentRegistry {

  private RecordContext recordContext;


  public RecordPointcutAdvisor() {
    this.recordContext = new RecordContext();
  }


  @Override
  public Advice getAdvice() {
    return recordContext;
  }

  @Override
  public boolean isPerInstance() {
    return true;
  }


  @Override
  public void addProcessorStrategy(ProcessorStrategy processorStrategy) {
    recordContext.addProcessorStrategy(processorStrategy);
  }

  @Override
  public void addRecordPostProcessor(RecordPostProcessor recordPostProcessor) {
    recordContext.addRecordPostProcessor(recordPostProcessor);
  }

  @Override
  public void setTemplateResolve(TemplateResolve templateResolve) {
    recordContext.setTemplateResolve(templateResolve);
  }

  @Override
  public void setParamParse(ParamParse paramParse) {
    recordContext.setParamParse(paramParse);
  }

  @Override
  public void setExecutor(Executor executor) {
    recordContext.setExecutor(executor);
  }

  @Override
  public void setComponentFactory(ComponentFactory componentFactory) {
    recordContext.setComponentFactory(componentFactory);
  }

  @Override
  public void setDefaultRecordProducer(RecordProducer defaultRecordProducer) {
    recordContext.setDefaultRecordProducer(defaultRecordProducer);
  }

  @Override
  public void setDefaultPipeline(Pipeline defaultPipeline) {
    recordContext.setDefaultPipeline(defaultPipeline);
  }

  @Override
  public void initComponent() {
    recordContext.initComponent();
  }

  @Override
  public void register(RecordDefinition recordDefinition) {
    recordContext.register(recordDefinition);
  }


  @Override
  public boolean matches(Method method, Class<?> targetClass) {
    return method.isAnnotationPresent(Record.class);
  }
}
