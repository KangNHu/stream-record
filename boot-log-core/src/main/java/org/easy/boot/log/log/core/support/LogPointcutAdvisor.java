package org.easy.boot.log.log.core.support;

import org.aopalliance.aop.Advice;
import org.easy.boot.log.log.core.matedata.LogDefinition;
import org.easy.boot.log.log.core.processor.LogPostProcessor;
import org.easy.boot.log.log.core.processor.ProcessorStrategy;
import org.easy.boot.log.log.core.processor.TemplateResolve;
import org.easy.boot.core.*;
import org.easy.boot.log.log.core.*;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;

import java.util.concurrent.Executor;

/**
* 审计日志条件通知  
* @author : KangNing Hu
*/
public class LogPointcutAdvisor implements ComponentRegistry,PointcutAdvisor {

	private LogContext logContext;

	private LogPointcut logPointcut;

	public LogPointcutAdvisor(){
		this.logContext = new LogContext();
		this.logPointcut = new LogPointcut();
	}

	@Override
	public Pointcut getPointcut() {
		return logPointcut;
	}

	@Override
	public Advice getAdvice() {
		return logContext;
	}

	@Override
	public boolean isPerInstance() {
		return true;
	}


	@Override
	public void addProcessorStrategy(ProcessorStrategy processorStrategy) {
		logContext.addProcessorStrategy(processorStrategy);
	}

	@Override
	public void addLogAuditPostProcessor(LogPostProcessor logPostProcessor) {
		logContext.addLogAuditPostProcessor(logPostProcessor);
	}

	@Override
	public void setTemplateResolve(TemplateResolve templateResolve) {
		logContext.setTemplateResolve(templateResolve);
	}

	@Override
	public void setParamParse(ParamParse paramParse) {
		logContext.setParamParse(paramParse);
	}

	@Override
	public void setExecutor(Executor executor) {
		logContext.setExecutor(executor);
	}

	@Override
	public void setComponentFactory(ComponentFactory componentFactory) {
		logContext.setComponentFactory(componentFactory);
	}

	@Override
	public void setDefaultLogProducer(LogProducer defaultLogProducer) {
		logContext.setDefaultLogProducer(defaultLogProducer);
	}

	@Override
	public void setDefaultPipeline(Pipeline defaultPipeline) {
		logContext.setDefaultPipeline(defaultPipeline);
	}

	@Override
	public void initComponent() {
		logContext.initComponent();
	}

	@Override
	public void register(LogDefinition logDefinition) {
		logContext.register(logDefinition);
	}
}
