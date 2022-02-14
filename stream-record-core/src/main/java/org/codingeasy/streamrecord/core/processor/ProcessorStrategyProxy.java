package org.codingeasy.streamrecord.core.processor;

import org.codingeasy.streamrecord.core.CurrentContext;
import org.codingeasy.streamrecord.core.InterceptMethodWrapper;
import org.codingeasy.streamrecord.core.RecordProducer;
import org.codingeasy.streamrecord.core.Pipeline;
import org.codingeasy.streamrecord.core.annotation.Record;
import org.codingeasy.streamrecord.core.matedata.Advice;
import org.codingeasy.streamrecord.core.matedata.RecordInfoWrapper;
import org.codingeasy.streamrecord.core.matedata.RecordDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.concurrent.Executor;
/**
* 执行策略代理  
* @author : KangNing Hu
*/
public class ProcessorStrategyProxy extends HashMap<String, Processor> {

	private Logger logger = LoggerFactory.getLogger(ProcessorStrategyProxy.class);

	/**
	 * 默认的全局记录生成器
	 */
	private RecordProducer defaultRecordProducer;

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

	public ProcessorStrategyProxy(RecordProducer defaultRecordProducer, Pipeline  defaultPipeline , Executor executor){
		this.defaultRecordProducer = defaultRecordProducer;
		this.defaultPipeline = defaultPipeline;
		this.executor = executor;
	}

	public Object process(CurrentContext currentContext) throws Throwable {
		Object obj = null;
		RecordDefinition recordDefinition = currentContext.getRecordDefinition();
		Advice advice = recordDefinition.getAdvice();
		//前置
		if (advice == Advice.BEFORE) {
			//不影响代码目标正常执行
			try {
				routeProcess(currentContext, recordDefinition.isAsync());
			}finally {
				obj = currentContext.invoke();
			}
		}
		//后置
		else if (advice == Advice.AFTER) {
			obj = currentContext.invoke();
			routeProcess(currentContext, recordDefinition.isAsync());
		}
		//后置异常进行处理
		else if (advice == Advice.AFTER_EXCEPTION){
			try {
				obj = currentContext.invoke();
			}catch (Throwable e){
				currentContext.setThrowable(e);
			}
			routeProcess(currentContext , recordDefinition.isAsync());
			if (currentContext.hasException()){
				throw currentContext.getThrowable();
			}
		}
		//异常
		else {
			try {
				obj = currentContext.invoke();
			} catch (Throwable throwable) {
				routeProcess(currentContext, recordDefinition.isAsync());
				throw throwable;
			}
		}
		return obj;
	}

	/**
	 * 路由不同的执行方式
	 * @param currentContext 当前上下文
	 * @param isAsync 是否异步
	 */
	private void routeProcess(CurrentContext currentContext , boolean isAsync){
		try {
			if (isAsync) {
				executor.execute(() -> doProcess(currentContext));
			} else {
				doProcess(currentContext);
			}
		}catch (Exception e){
			logger.warn("处理记录失败" ,e);
		}
	}


	private void doProcess(CurrentContext currentContext){
		//获取记录处理策略
		String processorStrategyName = currentContext.getProcessorStrategyName();
		Processor processor = get(processorStrategyName);
		if (processor == null){
			throw new IllegalArgumentException("未知的策略名称：" + processorStrategyName);
		}
		processor.process(currentContext);
		RecordProducer recordProducer = currentContext.getRecordProducer();
		//如果指定记录生成器则用指定的生产器，否则使用全局的记录生成器
		if (recordProducer.getClass() == Record.Void.class){
			recordProducer = defaultRecordProducer;
		}
		RecordInfoWrapper recordInfoWrapper = recordProducer.doProduce(currentContext);
		if (recordInfoWrapper == null){
			if (logger.isDebugEnabled()){
				logger.debug("当前生成记录为空忽略 , 记录定义：{} 运行参数 ：{}" ,
						currentContext.getRecordDefinition() ,
						currentContext.getInterceptMethodWrapper());
			}
			return;
		}
		Pipeline pipeline = currentContext.getPipeline();
		//如果指定记录生成器则用指定的管道，否则使用全局的记录管道
		if (pipeline.getClass() == Record.Void.class){
			pipeline = defaultPipeline;
		}
		if (pipeline != null){
			pipeline.doConsume(recordInfoWrapper);
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
