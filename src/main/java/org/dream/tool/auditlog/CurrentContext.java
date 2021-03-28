package org.dream.tool.auditlog;

import org.aopalliance.intercept.MethodInvocation;
import org.dream.tool.auditlog.matedata.LogDefinition;

/**
* 当前上下文  
* @author : KangNing Hu
*/
public class CurrentContext {

	/**
	 * 日志定义注册器
	 */
	private LogDefinitionRegistry logDefinitionRegistry;

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
	private LogDefinition logDefinition;


	/**
	 * 上下文
	 */
	private Pipeline pipeline;


	/**
	 * 日志生产器
	 */
	private LogProducer logProducer;

	/**
	 * 处理策略
	 */
	private String  processorStrategyName;


	public CurrentContext(LogDefinitionRegistry logDefinitionRegistry , AttributeAccess attributeAccess, InterceptMethodWrapper interceptMethodWrapper){
		this.logDefinitionRegistry = logDefinitionRegistry;
		this.attributeAccess = attributeAccess;
		this.interceptMethodWrapper = interceptMethodWrapper;
	}

	/**
	 * 解析log定义
	 */
	void parseLogDefinition(ComponentFactory componentFactory) {
		this.logDefinition = logDefinitionRegistry.getLogDefinition(interceptMethodWrapper.getTarget().getClass() ,this.interceptMethodWrapper.getMethod());
		//创建日志生成器
		Class<? extends LogProducer> logProducerClass = this.logDefinition.getLogProducerClass();
		this.logProducer = (LogProducer) componentFactory.createComponent(logProducerClass);
		//创建管道
		Class<? extends Pipeline> pipelineClass = this.logDefinition.getPipelineClass();
		this.pipeline = (Pipeline) componentFactory.createComponent(pipelineClass);
		//处理策略
		this.processorStrategyName = this.logDefinition.getProcessorStrategy();
	}

	/**
	 * 方法调用
	 * @return
	 * @throws Throwable
	 */
	public Object invoke() throws Throwable {
		MethodInvocation methodInvocation = this.interceptMethodWrapper.getMethodInvocation();
		return methodInvocation.proceed();
	}

	public LogDefinitionRegistry getLogDefinitionRegistry() {
		return logDefinitionRegistry;
	}

	public AttributeAccess getAttributeAccess() {
		return attributeAccess;
	}

	public InterceptMethodWrapper getInterceptMethodWrapper() {
		return interceptMethodWrapper;
	}

	public LogDefinition getLogDefinition() {
		return logDefinition;
	}

	public Pipeline getPipeline() {
		return pipeline;
	}

	public LogProducer getLogProducer() {
		return logProducer;
	}

	public String getProcessorStrategyName() {
		return processorStrategyName;
	}
}
