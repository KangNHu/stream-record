package org.easy.boot.log.log.core.processor;

import org.easy.boot.log.log.core.CurrentContext;

/**
 * 模版解析器
 * @author hukangning
 */
@FunctionalInterface
public interface TemplateResolve {

	String doResolve(CurrentContext currentContext);
}
