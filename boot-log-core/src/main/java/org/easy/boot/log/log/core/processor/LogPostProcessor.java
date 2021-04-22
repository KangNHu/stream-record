package org.easy.boot.log.log.core.processor;

import org.easy.boot.log.log.core.CurrentContext;

/**
* 处理器  
* @author : KangNing Hu
*/
public interface LogPostProcessor {

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
