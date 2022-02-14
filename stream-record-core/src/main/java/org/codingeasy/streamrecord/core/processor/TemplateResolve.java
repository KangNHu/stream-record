package org.codingeasy.streamrecord.core.processor;

import org.codingeasy.streamrecord.core.CurrentContext;

/**
 * 模版解析器
 *
 * @author hukangning
 */
@FunctionalInterface
public interface TemplateResolve {

  String doResolve(CurrentContext currentContext);
}
