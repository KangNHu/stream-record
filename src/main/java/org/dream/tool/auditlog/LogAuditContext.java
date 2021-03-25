package org.dream.tool.auditlog;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.dream.tool.auditlog.processor.LogAuditPostProcessor;
import org.dream.tool.auditlog.processor.ProcessorStrategy;
import org.dream.tool.auditlog.processor.ProcessorStrategyProxy;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
* 审计日志上下文
 * @see DefaultLogDefinitionRegistry
* @author : KangNing Hu
*/
public class LogAuditContext implements LogDefinitionRegistry , MethodInterceptor, ComponentFactory {

	private LogDefinitionRegistry logDefinitionRegistry = new DefaultLogDefinitionRegistry();

	//执行策略代理
	private ProcessorStrategyProxy processorStrategyProxy;

	//上下文生命周期钩子处理器
	private List<LogAuditPostProcessor> logAuditPostProcessors;

	//参数解析器
	private ParamParse paramParse;

	//组件创建工厂
	private ComponentFactory componentFactory;


	//初始化状态
	private volatile boolean state = false;


	/**
	 * 初始化组件
	 */
	public void initComponent(){
		if (this.paramParse == null){
			this.paramParse = new DefaultParamParse();
		}
		if (this.componentFactory == null){
			this.componentFactory = this;
		}
		this.logAuditPostProcessors = new ArrayList<>();
		this.processorStrategyProxy = new ProcessorStrategyProxy();
		this.state = true;
	}

	/**
	 * 设置处理测序
	 */
	public void addProcessorStrategy(ProcessorStrategy processorStrategy){
		this.processorStrategyProxy.add(processorStrategy);
	}
	/**
	 * 添加上下文生命周期钩子
	 * @param logAuditPostProcessor
	 */
	public void  addLogAuditPostProcessor(LogAuditPostProcessor logAuditPostProcessor){
		this.logAuditPostProcessors.add(logAuditPostProcessor);
	}

	/**
	 * 添加参数解析器
	 * @param paramParse
	 */
	public void setParamParse(ParamParse paramParse){
		this.paramParse = paramParse;
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
	public LogDefinition getLogDefinition(Method method) {
		return logDefinitionRegistry.getLogDefinition(method);
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
	protected CurrentContext createCurrentContext(InterceptMethodWrapper interceptMethodWrapper , ParamAttribute paramAttribute){
		return new CurrentContext(this , paramAttribute , interceptMethodWrapper);
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
		//解析参数
		ParamAttribute paramAttribute = paramParse.parse(interceptMethodWrapper);
		//创建当前上下文
		CurrentContext currentContext = createCurrentContext(interceptMethodWrapper, paramAttribute);
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
