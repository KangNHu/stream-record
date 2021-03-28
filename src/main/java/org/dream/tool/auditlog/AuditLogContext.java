package org.dream.tool.auditlog;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.dream.tool.auditlog.matedata.LogDefinition;
import org.dream.tool.auditlog.processor.*;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
* 审计日志上下文
 * @see DefaultLogDefinitionRegistry
* @author : KangNing Hu
*/
public class AuditLogContext implements ComponentRegistry, MethodInterceptor, ComponentFactory {

	private LogDefinitionRegistry logDefinitionRegistry = new DefaultLogDefinitionRegistry();

	//执行策略代理
	private ProcessorStrategyProxy processorStrategyProxy;

	//上下文生命周期钩子处理器
	private List<LogAuditPostProcessor> logAuditPostProcessors;

	//参数解析器
	private ParamParse paramParse;

	//组件创建工厂
	private ComponentFactory componentFactory;

	//模版解析器
	private TemplateResolve templateResolve;

	// 异步处理的线程池
	private Executor executor;

	// 默认的日志生成器
	private LogProducer defaultLogProducer;

	//默认管道
	private Pipeline defaultPipeline;

	//初始化状态
	private volatile boolean state = false;

	/**
	 * 初始化组件
	 */
	@Override
	public void initComponent(){
		if (this.paramParse == null){
			this.paramParse = new DefaultParamParse();
		}
		if (this.componentFactory == null){
			this.componentFactory = this;
		}
		if (this.templateResolve == null){
			this.templateResolve = new SpElTemplateResolve();
		}
		if (this.defaultLogProducer == null){
			this.defaultLogProducer = new DefaultLogProducer();
		}
		if (this.executor == null){
			this.executor = new ThreadPoolExecutor(4 , 18 ,
					30 , TimeUnit.MINUTES  , new SynchronousQueue<Runnable>() ,
					new BasicThreadFactory.Builder().namingPattern("audit-log-").build());
		}
		this.logAuditPostProcessors = new ArrayList<>();
		this.processorStrategyProxy = new ProcessorStrategyProxy(this.defaultLogProducer , this.defaultPipeline , this.executor);
		//添加内置处理策略
		// 基于el表达式的策略
		ProcessorStrategy expression = ProcessorStrategy.EXPRESSION;
		ExpressionProcessor expressionProcessor = (ExpressionProcessor) expression.getProcessor();
		expressionProcessor.setTemplateResolve(this.templateResolve);
		this.processorStrategyProxy.add(expression);
		//基于方法路由的策略
		this.processorStrategyProxy.add(ProcessorStrategy.ROUTE);
		this.state = true;
	}

	/**
	 * 设置处理测序
	 */
	@Override
	public void addProcessorStrategy(ProcessorStrategy processorStrategy){
		this.processorStrategyProxy.add(processorStrategy);
	}
	/**
	 * 添加上下文生命周期钩子
	 * @param logAuditPostProcessor
	 */
	@Override
	public void  addLogAuditPostProcessor(LogAuditPostProcessor logAuditPostProcessor){
		this.logAuditPostProcessors.add(logAuditPostProcessor);
	}
	/**
	 * 设置模板解析器
	 * @param templateResolve
	 */
	@Override
	public void setTemplateResolve(TemplateResolve templateResolve) {
		this.templateResolve = templateResolve;
	}

	/**
	 * 设置参数解析器
	 * @param paramParse
	 */
	@Override
	public void setParamParse(ParamParse paramParse){
		this.paramParse = paramParse;
	}

	/**
	 * 设置 异步处理的线程池
	 * @param executor
	 */
	@Override
	public void setExecutor(Executor executor){
		this.executor = executor;
	}

	/**
	 * 设置组件工程
	 * @param componentFactory
	 */
	@Override
	public void setComponentFactory(ComponentFactory componentFactory) {
		this.componentFactory = componentFactory;
	}

	/**
	 * 设置默认日志生成器
	 * @param defaultLogProducer
	 */
	@Override
	public void setDefaultLogProducer(LogProducer defaultLogProducer) {
		this.defaultLogProducer = defaultLogProducer;
	}

	/**
	 * 生成默认管道
	 * @param defaultPipeline
	 */
	@Override
	public void setDefaultPipeline(Pipeline defaultPipeline) {
		this.defaultPipeline = defaultPipeline;
	}

	@Override
	public void register(LogDefinition logDefinition) {
		logDefinitionRegistry.register(logDefinition);
	}

	@Override
	public List<LogDefinition> getLogDefinitions() {
		return logDefinitionRegistry.getLogDefinitions();
	}

	@Override
	public LogDefinition getLogDefinition(Class clazz, Method method) {
		return logDefinitionRegistry.getLogDefinition(clazz , method);
	}

	@Override
	public Object createComponent(Class clazz) {
		return BeanUtils.instantiateClass(clazz);
	}


	/**
	 * 应用前置处理器
	 * @param currentContext 当前上下文
	 */
	private void applyPreProcessors(CurrentContext currentContext) {
		for (LogAuditPostProcessor postProcessor : logAuditPostProcessors){
			 postProcessor.preProcessor(currentContext);
		}
	}

	/**
	 * 应用后置处理器
	 * @param currentContext 当前上下文
	 */
	private void applyPostProcessors(CurrentContext currentContext) {
		for (LogAuditPostProcessor postProcessor : logAuditPostProcessors){
			postProcessor.postProcessor(currentContext);
		}
	}

	/**
	 * 创建当前上下文
	 * @return
	 */
	protected CurrentContext createCurrentContext(InterceptMethodWrapper interceptMethodWrapper , AttributeAccess attributeAccess){
		return new CurrentContext(this , attributeAccess, interceptMethodWrapper);
	}

	/**
	 * 校验上下文状态
	 */
	private void checkState() {
		if (!this.state){
			throw new IllegalStateException("log 上下文未初始化");
		}
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		//校验上下文状态
		checkState();
		InterceptMethodWrapper interceptMethodWrapper = new InterceptMethodWrapper(invocation);
		//创建当前上下文
		CurrentContext currentContext = createCurrentContext(interceptMethodWrapper, new AttributeAccess());
		//解析参数
		paramParse.parse(currentContext);
		//解析log定义
		currentContext.parseLogDefinition(this);
		//前置处理
		applyPreProcessors(currentContext);
		//执行结果
		Object object = processorStrategyProxy.process(currentContext);
		//后置处理
		applyPostProcessors(currentContext);
		return object;
	}
}
