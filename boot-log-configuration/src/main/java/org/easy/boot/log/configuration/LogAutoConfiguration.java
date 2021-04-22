package org.easy.boot.log.configuration;


import org.easy.boot.log.log.core.ComponentFactory;
import org.easy.boot.log.log.core.LogProducer;
import org.easy.boot.log.log.core.ParamParse;
import org.easy.boot.log.log.core.Pipeline;
import org.easy.boot.log.log.core.processor.LogPostProcessor;
import org.easy.boot.log.log.core.processor.ProcessorStrategy;
import org.easy.boot.log.log.core.processor.TemplateResolve;
import org.easy.boot.log.log.core.support.LogPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
* 自动装配类  
* @author : KangNing Hu
*/
public class LogAutoConfiguration {

	//执行策略
	private List<ProcessorStrategy> processorStrategies;

	//后置处理器
	private List<LogPostProcessor> logPostProcessors;

	//模板解析器
	private TemplateResolve templateResolve;


	//参数解析器
	private ParamParse paramParse;


	//全局日志生成器
	private LogProducer defaultLogProducer;


	//全局生成默认管道
	private Pipeline defaultPipeline;


	@Bean
	@ConditionalOnMissingBean(ComponentFactory.class)
	public ComponentFactory componentFactory(){
		return new SpringComponentFactory();
	}

	@Bean
	@ConditionalOnMissingBean(LogPointcutAdvisor.class)
	public LogDefinitionAutoRegistry auditLogPointcutAdvisor(ComponentFactory componentFactory){
		LogDefinitionAutoRegistry logDefinitionAutoRegistry = new LogDefinitionAutoRegistry();
		//执行策略 没有则不设置
		if (!CollectionUtils.isEmpty(processorStrategies)){
			processorStrategies.forEach(logDefinitionAutoRegistry::addProcessorStrategy);
		}
		//后置处理器设置 没有则不设置
		if (!CollectionUtils.isEmpty(logPostProcessors)){
			logPostProcessors.forEach(logDefinitionAutoRegistry::addLogAuditPostProcessor);
		}
		//模板解析器设置 没有则不设置
		if (this.templateResolve != null){
			logDefinitionAutoRegistry.setTemplateResolve(templateResolve);
		}
		//参数解析器设置 没有则不设置
		if (this.paramParse != null){
			logDefinitionAutoRegistry.setParamParse(paramParse);
		}
		//全局日志生成器设置 没有则不设置
		if (this.defaultLogProducer != null){
			logDefinitionAutoRegistry.setDefaultLogProducer(defaultLogProducer);
		}
		//全局生成默认管道设置 没有则不设置
		if (this.defaultPipeline != null){
			logDefinitionAutoRegistry.setDefaultPipeline(defaultPipeline);
		}
		//设置组件工厂
		logDefinitionAutoRegistry.setComponentFactory(componentFactory);
			//初始化组件
		logDefinitionAutoRegistry.initComponent();
		//初始化
		return logDefinitionAutoRegistry;
	}



	@Autowired(required = false)
	public void setProcessorStrategies(List<ProcessorStrategy> processorStrategies) {
		this.processorStrategies = processorStrategies;
	}

	@Autowired(required = false)
	public void setLogPostProcessors(List<LogPostProcessor> logPostProcessors) {
		this.logPostProcessors = logPostProcessors;
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
	public void setDefaultLogProducer(LogProducer defaultLogProducer) {
		this.defaultLogProducer = defaultLogProducer;
	}

	@Autowired(required = false)
	public void setDefaultPipeline(Pipeline defaultPipeline) {
		this.defaultPipeline = defaultPipeline;
	}
}
