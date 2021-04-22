package org.easy.boot.log.log.core.processor;

import org.easy.boot.log.log.core.CurrentContext;

/**
 * 日志处理方式
 * @see ProcessorStrategy
 * @author hukangning
 */
public interface Processor {


	void process(CurrentContext currentContext);
}
