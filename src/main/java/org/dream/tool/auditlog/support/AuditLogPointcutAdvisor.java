package org.dream.tool.auditlog.support;

import org.aopalliance.aop.Advice;
import org.dream.tool.auditlog.*;
import org.dream.tool.auditlog.matedata.LogDefinition;
import org.dream.tool.auditlog.processor.LogAuditPostProcessor;
import org.dream.tool.auditlog.processor.ProcessorStrategy;
import org.dream.tool.auditlog.processor.TemplateResolve;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;

import java.util.concurrent.Executor;

/**
* 审计日志条件通知  
* @author : KangNing Hu
*/
public class AuditLogPointcutAdvisor implements ComponentRegistry ,PointcutAdvisor {

	private AuditLogContext auditLogContext;

	private AuditLogPointcut auditLogPointcut;

	public AuditLogPointcutAdvisor(){
		this.auditLogContext = new AuditLogContext();
		this.auditLogPointcut = new AuditLogPointcut();
	}

	@Override
	public Pointcut getPointcut() {
		return auditLogPointcut;
	}

	@Override
	public Advice getAdvice() {
		return auditLogContext;
	}

	@Override
	public boolean isPerInstance() {
		return true;
	}


	@Override
	public void addProcessorStrategy(ProcessorStrategy processorStrategy) {
		auditLogContext.addProcessorStrategy(processorStrategy);
	}

	@Override
	public void addLogAuditPostProcessor(LogAuditPostProcessor logAuditPostProcessor) {
		auditLogContext.addLogAuditPostProcessor(logAuditPostProcessor);
	}

	@Override
	public void setTemplateResolve(TemplateResolve templateResolve) {
		auditLogContext.setTemplateResolve(templateResolve);
	}

	@Override
	public void setParamParse(ParamParse paramParse) {
		auditLogContext.setParamParse(paramParse);
	}

	@Override
	public void setExecutor(Executor executor) {
		auditLogContext.setExecutor(executor);
	}

	@Override
	public void setComponentFactory(ComponentFactory componentFactory) {
		auditLogContext.setComponentFactory(componentFactory);
	}

	@Override
	public void setDefaultLogProducer(LogProducer defaultLogProducer) {
		auditLogContext.setDefaultLogProducer(defaultLogProducer);
	}

	@Override
	public void setDefaultPipeline(Pipeline defaultPipeline) {
		auditLogContext.setDefaultPipeline(defaultPipeline);
	}

	@Override
	public void initComponent() {
		auditLogContext.initComponent();
	}

	@Override
	public void register(LogDefinition logDefinition) {
		auditLogContext.register(logDefinition);
	}
}
