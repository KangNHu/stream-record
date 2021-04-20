package org.dream.tool.auditlog.core.matedata;

import org.dream.tool.auditlog.core.AttributeAccess;
import org.dream.tool.auditlog.core.LogProducer;
import org.dream.tool.auditlog.core.Pipeline;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
* 默认的日志定义  
* @author : KangNing Hu
*/
public class DefaultLogDefinition extends AttributeAccess implements LogDefinition{

	//路由方法对象
	public final static String ROUTE_METHOD =  "routeMethod";
	//路由方法参数列表
	public final static String ROUTE_METHOD_PARAM_TYPES = "routeMethodParamTypes";

	private Class<?> targetClass;

	private Method method;


	private String temple;


	private boolean async;


	private String processorStrategy;


	private Class<? extends LogProducer> logProducerClass;


	private Class<? extends Pipeline> pipelineClass;


	private Advice advice;


	private List<ParamNode>[] params;


	private String name;


	public void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public void setTemple(String temple) {
		this.temple = temple;
	}

	public void setAsync(boolean async) {
		this.async = async;
	}

	public void setProcessorStrategy(String processorStrategy) {
		this.processorStrategy = processorStrategy;
	}

	public void setLogProducerClass(Class<? extends LogProducer> logProducerClass) {
		this.logProducerClass = logProducerClass;
	}

	public void setPipelineClass(Class<? extends Pipeline> pipelineClass) {
		this.pipelineClass = pipelineClass;
	}

	public void setAdvice(Advice advice) {
		this.advice = advice;
	}

	public void setParams(List<ParamNode>[] params) {
		this.params = params;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Class<?> getTargetClass() {
		return this.targetClass;
	}


	@Override
	public Method getMethod() {
		return this.method;
	}

	@Override
	public String getTemple() {
		return this.temple;
	}

	@Override
	public Boolean isAsync() {
		return this.async;
	}

	@Override
	public String getProcessorStrategy() {
		return this.processorStrategy;
	}

	@Override
	public Class<? extends LogProducer> getLogProducerClass() {
		return this.logProducerClass;
	}

	@Override
	public Class<? extends Pipeline> getPipelineClass() {
		return this.pipelineClass;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public List<ParamNode>[] getParams() {
		return this.params;
	}

	@Override
	public Advice getAdvice() {
		return this.advice;
	}


	@Override
	public String toString() {
		return "DefaultLogDefinition{" +
				"targetClass=" + targetClass +
				", method=" + method +
				", temple='" + temple + '\'' +
				", async=" + async +
				", processorStrategy='" + processorStrategy + '\'' +
				", logProducerClass=" + logProducerClass +
				", pipelineClass=" + pipelineClass +
				", advice=" + advice +
				", params=" + Arrays.toString(params) +
				", name='" + name + '\'' +
				"} " + super.toString();
	}
}
