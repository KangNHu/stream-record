package org.dream.tool.auditlog.core.processor;

import org.dream.tool.auditlog.core.CurrentContext;
import org.dream.tool.auditlog.core.LogProducer;
import org.dream.tool.auditlog.core.Pipeline;
import org.dream.tool.auditlog.core.annotation.AuditLog;
import org.dream.tool.auditlog.core.matedata.Advice;
import org.dream.tool.auditlog.core.matedata.AuditLogInfoWrapper;
import org.dream.tool.auditlog.core.matedata.LogDefinition;

import java.util.HashMap;
import java.util.concurrent.Executor;

/**
* 执行策略代理  
* @author : KangNing Hu
*/
public class ProcessorStrategyProxy extends HashMap<String, Processor> {

	/**
	 * 默认的全局日志生成器
	 */
	private LogProducer defaultLogProducer;

	/**
	 * 默认的全局管道对象
	 */
	private Pipeline defaultPipeline;

	/**
	 * 异步处理的线程池
	 */
	private Executor executor;


	public ProcessorStrategyProxy(){

	}

	public ProcessorStrategyProxy(LogProducer defaultLogProducer ,Pipeline  defaultPipeline , Executor executor){
		this.defaultLogProducer = defaultLogProducer;
		this.defaultPipeline = defaultPipeline;
		this.executor = executor;
	}

	public Object process(CurrentContext currentContext) throws Throwable {
		LogDefinition logDefinition = currentContext.getLogDefinition();
		Advice advice = logDefinition.getAdvice();
		//前置
		if (advice == Advice.BEFORE){
			routeProcess(currentContext , logDefinition.isAsync());
			currentContext.invoke();
		}
		//后置
		else if (advice == Advice.AFTER){
			currentContext.invoke();
			routeProcess(currentContext , logDefinition.isAsync());
		}
		//异常
		else {
			try {
				currentContext.invoke();
			}catch (Throwable throwable){
				routeProcess(currentContext , logDefinition.isAsync());
			}
		}

		//通知
		return null;
	}

	/**
	 * 路由不同的执行方式
	 * @param currentContext
	 * @param isAsync
	 */
	private void routeProcess(CurrentContext currentContext , boolean isAsync){
		if (isAsync){
			executor.execute(() -> doProcess(currentContext));
		}else {
			doProcess(currentContext);
		}
	}


	private void doProcess(CurrentContext currentContext){
		//获取日志处理策略
		String processorStrategyName = currentContext.getProcessorStrategyName();
		Processor processor = get(processorStrategyName);
		if (processor == null){
			throw new IllegalArgumentException("未知的策略名称：" + processorStrategyName);
		}
		processor.process(currentContext);
		LogProducer logProducer = currentContext.getLogProducer();
		//如果指定日志生成器则用指定的生产器，否则使用全局的日志生成器
		if (logProducer.getClass() == AuditLog.Void.class){
			logProducer = defaultLogProducer;
		}
		AuditLogInfoWrapper auditLogInfoWrapper = logProducer.doProduce(currentContext);
		Pipeline pipeline = currentContext.getPipeline();
		//如果指定日志生成器则用指定的管道，否则使用全局的日志管道
		if (pipeline.getClass() == AuditLog.Void.class){
			pipeline = defaultPipeline;
		}
		if (pipeline != null){
			pipeline.doConsume(auditLogInfoWrapper);
		}
	}

	/**
	 * 添加处理策略
	 * @param processorStrategy 处理策略对象
	 */
	public void add(ProcessorStrategy processorStrategy) {
		this.put(processorStrategy.getName() , processorStrategy.getProcessor());
	}
}
