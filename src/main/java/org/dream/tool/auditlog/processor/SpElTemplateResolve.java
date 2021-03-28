package org.dream.tool.auditlog.processor;

import org.dream.tool.auditlog.CurrentContext;
import org.dream.tool.auditlog.InterceptMethodWrapper;
import org.dream.tool.auditlog.utils.SpringELUtils;

/**
* Spel表达式实现的解析器
 *
* @author : KangNing Hu
*/
public class SpElTemplateResolve implements TemplateResolve{



	@Override
	public String doResolve(CurrentContext currentContext) {
		String temple = currentContext.getLogDefinition().getTemple();
		InterceptMethodWrapper interceptMethodWrapper = currentContext.getInterceptMethodWrapper();
		return SpringELUtils.parse(temple, interceptMethodWrapper.getMethod(), interceptMethodWrapper.toArray(new Object[]{}));
	}
}
