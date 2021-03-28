package org.dream.tool.auditlog;

import org.dream.tool.auditlog.matedata.LogDefinition;
import org.dream.tool.auditlog.processor.LogAuditPostProcessor;
import org.dream.tool.auditlog.processor.ProcessorStrategy;
import org.dream.tool.auditlog.processor.TemplateResolve;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Executor;

/**
* 组件注册器  
* @author : KangNing Hu
*/
public interface ComponentRegistry  extends LogDefinitionRegistry{

	/**
	 * 设置处理测序
	 */
	void addProcessorStrategy(ProcessorStrategy processorStrategy);

	/**
	 * 添加上下文生命周期钩子
	 * @param logAuditPostProcessor
	 */
	void addLogAuditPostProcessor(LogAuditPostProcessor logAuditPostProcessor);

	/**
	 * 设置模板解析器
	 * @param templateResolve
	 */
	void setTemplateResolve(TemplateResolve templateResolve);

	/**
	 * 设置参数解析器
	 * @param paramParse
	 */
	void setParamParse(ParamParse paramParse);

	/**
	 * 设置 异步处理的线程池
	 * @param executor
	 */
	void setExecutor(Executor executor);
	/**
	 * 设置组件工程
	 * @param componentFactory
	 */
	void setComponentFactory(ComponentFactory componentFactory);

	/**
	 * 设置默认日志生成器
	 * @param defaultLogProducer
	 */
	void setDefaultLogProducer(LogProducer defaultLogProducer);

	/**
	 * 生成默认管道
	 * @param defaultPipeline
	 */
	void setDefaultPipeline(Pipeline defaultPipeline);


	/**
	 * 初始化组件
	 */
	void initComponent();

	/**
	 * 返回所有log定义
	 * @return 返回当前注册表中的所有log定义
	 */
	default List<LogDefinition> getLogDefinitions(){
		throw new IllegalStateException("当前对象非日志定义注册器的实现者");
	}


	/**
	 * 返回指定log定义
	 * @param method 被记录的方法
	 * @return 被记录方法的log定义
	 */
	default LogDefinition getLogDefinition(Class clazz , Method method ){
		throw new IllegalStateException("当前对象非日志定义注册器的实现者");
	}

}
