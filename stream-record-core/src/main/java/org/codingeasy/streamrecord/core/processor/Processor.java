package org.codingeasy.streamrecord.core.processor;

import org.codingeasy.streamrecord.core.CurrentContext;

/**
 * 记录处理方式
 *
 * @author hukangning
 * @see ProcessorStrategy
 */
public interface Processor {


  void process(CurrentContext currentContext);
}
