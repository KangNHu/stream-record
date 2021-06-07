package org.codingeasy.streamrecord.core.processor;

import org.codingeasy.streamrecord.core.CurrentContext;
import org.codingeasy.streamrecord.core.RecordProducer;
import org.codingeasy.streamrecord.core.Pipeline;
import org.codingeasy.streamrecord.core.annotation.Record;
import org.codingeasy.streamrecord.core.matedata.Advice;
import org.codingeasy.streamrecord.core.matedata.RecordInfoWrapper;
import org.codingeasy.streamrecord.core.matedata.RecordDefinition;

import java.util.HashMap;
import java.util.concurrent.Executor;

/**
* 执行策略代理  
* @author : KangNing Hu
*/
public class ProcessorStrategyProxy extends HashMap<String, Processor> {

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
		RecordDefinition recordDefinition = currentContext.getRecordDefinition();
		Advice advice = recordDefinition.getAdvice();
		//前置
		if (advice == Advice.BEFORE){
			routeProcess(currentContext , recordDefinition.isAsync());
			currentContext.invoke();
		}
		//后置
		else if (advice == Advice.AFTER){
			currentContext.invoke();
			routeProcess(currentContext , recordDefinition.isAsync());
		}
		//异常
		else {
			try {
				currentContext.invoke();
			}catch (Throwable throwable){
				routeProcess(currentContext , recordDefinition.isAsync());
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
