package org.dream.tool.auditlog;

/**
* 日志生成器  
* @author : KangNing Hu
*/
@FunctionalInterface
public interface LogProducer {


	Object doProduce(CurrentContext currentContext);
}
