package org.dream.tool.auditlog.processor;

import org.dream.tool.auditlog.CurrentContext;

/**
* 处理器  
* @author : KangNing Hu
*/
public interface LogAuditPostProcessor {

	/**
	 * 前置处理
	 * @param currentContext
	 */
	void preProcessor(CurrentContext currentContext);

	/**
	 * 后置处理
	 * @param currentContext
	 */
	void postProcessor(CurrentContext currentContext);
}
