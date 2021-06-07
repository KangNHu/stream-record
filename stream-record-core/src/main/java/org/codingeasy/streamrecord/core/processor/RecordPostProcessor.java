package org.codingeasy.streamrecord.core.processor;

import org.codingeasy.streamrecord.core.CurrentContext;

/**
* 处理器  
* @author : KangNing Hu
*/
public interface RecordPostProcessor {

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
