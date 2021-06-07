package org.codingeasy.streamrecord.core;

import org.aopalliance.intercept.MethodInvocation;
import org.codingeasy.streamrecord.core.matedata.RecordDefinition;

/**
* 当前上下文  
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
	private String  processorStrategyName;


	public CurrentContext(RecordDefinitionRegistry recordDefinitionRegistry, AttributeAccess attributeAccess, InterceptMethodWrapper interceptMethodWrapper){
		this.recordDefinitionRegistry = recordDefinitionRegistry;
		this.attributeAccess = attributeAccess;
		this.interceptMethodWrapper = interceptMethodWrapper;
	}

	/**
	 * 解析record定义
	 */
	void parseRecordDefinition(ComponentFactory componentFactory) {
		this.recordDefinition = recordDefinitionRegistry.getRecordDefinition(interceptMethodWrapper.getTarget().getClass() ,this.interceptMethodWrapper.getMethod());
		if (this.recordDefinition == null){
			throw new IllegalStateException("当前方法没有对应的RecordDefinition");
		}
		//创建记录生成器
		Class<? extends RecordProducer> recordProducerClass = this.recordDefinition.getRecordProducerClass();
		this.recordProducer = (RecordProducer) componentFactory.createComponent(recordProducerClass);
		//创建管道
		Class<? extends Pipeline> pipelineClass = this.recordDefinition.getPipelineClass();
		this.pipeline = (Pipeline) componentFactory.createComponent(pipelineClass);
		//处理策略
		this.processorStrategyName = this.recordDefinition.getProcessorStrategy();
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
