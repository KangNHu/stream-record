package org.dream.tool.auditlog.configuration;


import org.dream.tool.auditlog.core.ComponentFactory;
import org.dream.tool.auditlog.core.LogProducer;
import org.dream.tool.auditlog.core.ParamParse;
import org.dream.tool.auditlog.core.Pipeline;
import org.dream.tool.auditlog.core.processor.LogAuditPostProcessor;
import org.dream.tool.auditlog.core.processor.ProcessorStrategy;
import org.dream.tool.auditlog.core.processor.TemplateResolve;
import org.dream.tool.auditlog.core.support.AuditLogPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
* 自动装配类  
* @author : KangNing Hu
*/
public class AuditLogAutoConfiguration {

	//执行策略
	private List<ProcessorStrategy> processorStrategies;

	//后置处理器
	private List<LogAuditPostProcessor> logAuditPostProcessors;

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
	@ConditionalOnMissingBean(AuditLogPointcutAdvisor.class)
	public LogDefinitionAutoRegistry auditLogPointcutAdvisor(ComponentFactory componentFactory){
		LogDefinitionAutoRegistry logDefinitionAutoRegistry = new LogDefinitionAutoRegistry();
		//执行策略 没有则不设置
		if (!CollectionUtils.isEmpty(processorStrategies)){
			processorStrategies.forEach(logDefinitionAutoRegistry::addProcessorStrategy);
		}
		//后置处理器设置 没有则不设置
		if (!CollectionUtils.isEmpty(logAuditPostProcessors)){
			logAuditPostProcessors.forEach(logDefinitionAutoRegistry::addLogAuditPostProcessor);
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
	public void setLogAuditPostProcessors(List<LogAuditPostProcessor> logAuditPostProcessors) {
		this.logAuditPostProcessors = logAuditPostProcessors;
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
