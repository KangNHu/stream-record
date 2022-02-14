package org.codingeasy.streamrecord.springboot.configuration;


import org.codingeasy.streamrecord.core.ComponentFactory;
import org.codingeasy.streamrecord.core.RecordProducer;
import org.codingeasy.streamrecord.core.ParamParse;
import org.codingeasy.streamrecord.core.Pipeline;
import org.codingeasy.streamrecord.core.processor.RecordPostProcessor;
import org.codingeasy.streamrecord.core.processor.ProcessorStrategy;
import org.codingeasy.streamrecord.core.processor.TemplateResolve;
import org.codingeasy.streamrecord.core.support.RecordPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 自动装配类
 *
 * @author : KangNing Hu
 */
public class StreamRecordAutoConfiguration {

  //执行策略
  private List<ProcessorStrategy> processorStrategies;

  //后置处理器
  private List<RecordPostProcessor> recordPostProcessors;

  //模板解析器
  private TemplateResolve templateResolve;


  //参数解析器
  private ParamParse paramParse;


  //全局日志生成器
  private RecordProducer defaultRecordProducer;


  //全局生成默认管道
  private Pipeline defaultPipeline;


  @Bean
  @ConditionalOnMissingBean(ComponentFactory.class)
  public ComponentFactory componentFactory() {
    return new SpringComponentFactory();
  }


  @Autowired(required = false)
  public void setProcessorStrategies(List<ProcessorStrategy> processorStrategies) {
    this.processorStrategies = processorStrategies;
  }

  @Autowired(required = false)
  public void setRecordPostProcessors(List<RecordPostProcessor> recordPostProcessors) {
    this.recordPostProcessors = recordPostProcessors;
  }

  @Autowired(required = false)
  public void setTemplateResolve(TemplateResolve templateResolve) {
    this.templateResolve = templateResolve;
  }

  @Autowired(required = false)
  public void setParamParse(ParamParse paramParse) {
    this.paramParse = paramParse;
  }

  @Autowired(required = false)
  public void setDefaultRecordProducer(RecordProducer defaultRecordProducer) {
    this.defaultRecordProducer = defaultRecordProducer;
  }

  @Autowired(required = false)
  public void setDefaultPipeline(Pipeline defaultPipeline) {
    this.defaultPipeline = defaultPipeline;
  }


  @Bean
  @ConditionalOnMissingBean(RecordDefinitionAutoRegistry.class)
  public RecordDefinitionAutoRegistry recordPointcutAdvisor(ComponentFactory componentFactory) {
    RecordDefinitionAutoRegistry recordDefinitionAutoRegistry = new RecordDefinitionAutoRegistry();
    //执行策略 没有则不设置
    if (!CollectionUtils.isEmpty(processorStrategies)) {
      processorStrategies.forEach(recordDefinitionAutoRegistry::addProcessorStrategy);
    }
    //后置处理器设置 没有则不设置
    if (!CollectionUtils.isEmpty(recordPostProcessors)) {
      recordPostProcessors.forEach(recordDefinitionAutoRegistry::addRecordPostProcessor);
    }
    //模板解析器设置 没有则不设置
    if (templateResolve != null) {
      recordDefinitionAutoRegistry.setTemplateResolve(templateResolve);
    }
    //参数解析器设置 没有则不设置
    if (paramParse != null) {
      recordDefinitionAutoRegistry.setParamParse(paramParse);
    }
    //全局日志生成器设置 没有则不设置
    if (defaultRecordProducer != null) {
      recordDefinitionAutoRegistry.setDefaultRecordProducer(defaultRecordProducer);
    }
    //全局生成默认管道设置 没有则不设置
    if (defaultPipeline != null) {
      recordDefinitionAutoRegistry.setDefaultPipeline(defaultPipeline);
    }
    //设置组件工厂
    recordDefinitionAutoRegistry.setComponentFactory(componentFactory);
    //初始化组件
    recordDefinitionAutoRegistry.initComponent();
    //初始化
    return recordDefinitionAutoRegistry;
  }
}
