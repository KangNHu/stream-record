package org.dream.tool.auditlog.core.processor;

import org.dream.tool.auditlog.core.CurrentContext;

/**
 * 模版解析器
 * @author hukangning
 */
@FunctionalInterface
public interface TemplateResolve {

	String doResolve(CurrentContext currentContext);
}
