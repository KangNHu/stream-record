package org.dream.tool.auditlog.processor;

import org.dream.tool.auditlog.CurrentContext;

/**
 * 日志处理方式
 * @see ProcessorStrategy
 * @author hukangning
 */
public interface Processor {


	void process(CurrentContext currentContext);
}
