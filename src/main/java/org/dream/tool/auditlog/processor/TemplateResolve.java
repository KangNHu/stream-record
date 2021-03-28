package org.dream.tool.auditlog.processor;

import org.dream.tool.auditlog.CurrentContext;

/**
 * 模版解析器
 * @author hukangning
 */
@FunctionalInterface
public interface TemplateResolve {

	String doResolve(CurrentContext currentContext);
}
